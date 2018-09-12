/**
 * 文件名：FastdfsFileServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-6-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.impl.BaseServiceImpl;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.constants.FileManageConstant;
import com.c503.sc.filemanage.dao.IFastdfsFileDao;
import com.c503.sc.filemanage.service.IFastdfsClientService;
import com.c503.sc.filemanage.service.IFastdfsFileService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 文件业务接口的Fastdfs实现类
 * 
 * @author huangtw
 * @version [版本号, 2016年7月1日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "fastdfsFileService")
public class FastdfsFileServiceImpl extends BaseServiceImpl<FileInfoEntity>
    implements IFastdfsFileService {
    
    /** 操作文件数据库接口 */
    @Resource(name = "fastdfsFileDao")
    private IFastdfsFileDao fileManageDao;
    
    /** 操作fastdfs文件服务器接口 */
    @Resource(name = "fastdfsClientService")
    private IFastdfsClientService fastdfsClientService;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(FastdfsFileServiceImpl.class);
    
    @Override
    public int remove(String updateBy, String... ids)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, "updateBy:" + updateBy
            + ",ids:" + Arrays.toString(ids));
        int i = this.fileManageDao.remove(updateBy, ids);
        LOGGER.debug(SystemContants.DEBUG_END, "effect " + i);
        
        return i;
    }
    
    @Override
    public List<FileInfoEntity> findByParams(Map<String, Object> params)
        throws Exception {
        
        return this.fileManageDao.findByParams(params);
    }
    
    @Override
    public int getCount(Map<String, Object> params)
        throws Exception {
        
        return this.fileManageDao.getCount(params);
    }
    
    @Override
    public List<FileInfoEntity> findAll()
        throws Exception {
        return this.fileManageDao.findAll();
    }
    
    @Override
    public FileInfoEntity findById(Serializable id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        FileInfoEntity fileInfoEntity = findByIdOutFile(id);
        // 获取物理文件。此处需要优化，需要考虑大文件、缓存等因素
        byte[] bytes =
            this.fastdfsClientService.downLoadFile(fileInfoEntity.getFileGroup(),
                fileInfoEntity.getFilePath());
        String fileName = fileInfoEntity.getFileName();
        File tempFile = saveTempFile(bytes, fileName);
        fileInfoEntity.setDownloadFile(tempFile);
        LOGGER.debug(SystemContants.DEBUG_END, fileInfoEntity);
        
        return fileInfoEntity;
    }
    
    @Override
    public FileInfoEntity findByIdOutFile(Serializable id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        if (null == id) {
            throw new NullPointerException("Parameter (id) is null");
        }
        // 获取文件数据库映射数据
        FileInfoEntity fileInfoEntity = fileManageDao.findById(id);
        if (null == fileInfoEntity) {
            throw new NullPointerException("File(id=" + id + ") is not exist");
        }
        LOGGER.debug(SystemContants.DEBUG_END, fileInfoEntity);
        
        return fileInfoEntity;
    }
    
    @Override
    public FileInfoEntity save(FileInfoEntity fileInfoEntity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, fileInfoEntity);
        if (null == fileInfoEntity) {
            throw new NullPointerException("Parameter (FileInfoEntity) is null");
        }
        // 首先保存文件
        this.saveFileToFastdfs(fileInfoEntity);
        // 再保存数据库
        this.fileManageDao.save(fileInfoEntity);
        
        return fileInfoEntity;
    }
    
    @Override
    public FileInfoEntity update(FileInfoEntity fileInfoEntity)
        throws Exception {
        if (null == fileInfoEntity) {
            throw new NullPointerException("Parameter (FileInfoEntity) is null");
        }
        // 首先保存文件
        saveFileToFastdfs(fileInfoEntity);
        // 再保存数据库
        this.fileManageDao.update(fileInfoEntity);
        
        return fileInfoEntity;
    }
    
    // 返回单个文件流
    @Override
    public Map<String, Object> returnOutputStream(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        String fileGroup = "";
        String remoteFilePath = "";
        String orgFileName = "";
        byte[] byt = null;
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != id) {
            FileInfoEntity fileInfoEntity = this.fileManageDao.findById(id);
            fileGroup = fileInfoEntity.getFileGroup();
            remoteFilePath = fileInfoEntity.getFilePath();
            orgFileName = fileInfoEntity.getOrgFileName();
            byt =
                this.fastdfsClientService.downLoadFile(fileGroup,
                    remoteFilePath);
            if (null == byt) {
                throw new CustomException(FileManageConstant.FILE_NOT_EXSITS,
                    byt);
            }
        }
        map.put("orgFileName", orgFileName);
        map.put("byte", byt);
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return map;
    }
    
    /**
     * 
     * 〈一句话功能简述〉保存文件到Fastdfs
     * 〈功能详细描述〉
     * 
     * @param fileInfoEntity fileInfoEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private void saveFileToFastdfs(FileInfoEntity fileInfoEntity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, fileInfoEntity);
        if (null != fileInfoEntity) {
            MultipartFile multipartFile = fileInfoEntity.getFile();
            // 文件原名
            String orgFileName = fileInfoEntity.getOrgFileName();
            // 文件后缀
            String suffix =
                orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
            
            String[] result =
                this.fastdfsClientService.upLoadFileToFastdfs(multipartFile.getInputStream(),
                    suffix);
            LOGGER.info(FileManageConstant.SUC_OPTION, result);
            if (null != result && result.length >= 2) {
                fileInfoEntity.setFileGroup(result[0]);
                fileInfoEntity.setFilePath(result[1]);
            }
            LOGGER.debug(SystemContants.DEBUG_END, fileInfoEntity);
        }
    }
    
    /**
     * 〈一句话功能简述〉保存模板文件
     * 〈功能详细描述〉
     * 
     * @param bytes byte[]
     * @param fileName fileName
     * @return File
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    private File saveTempFile(byte[] bytes, String fileName)
        throws Exception {
        OutputStream outs = null;
        File tempFile = null;
        try {
            int lio = fileName.lastIndexOf(".");
            String prefix = fileName.substring(0, lio);
            String suffix = fileName.substring(lio + 1);
            tempFile = File.createTempFile(prefix, suffix);
            outs = new FileOutputStream(tempFile);
            outs.write(bytes);
            outs.flush();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            if (outs != null) {
                outs.close();
            }
        }
        return tempFile;
    }
    
    @Override
    public IBaseDao<FileInfoEntity> getBaseDao() {
        return this.fileManageDao;
    }
    
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
}
