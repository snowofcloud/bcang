/**
 * 文件名：FileManageImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月14日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.base.service.impl.BaseServiceImpl;
import com.c503.sc.filemanage.bean.FileInfoEntity;
import com.c503.sc.filemanage.constants.FileManageConstant;
import com.c503.sc.filemanage.dao.IFileManageDao;
import com.c503.sc.filemanage.service.IFastdfsClientService;
import com.c503.sc.filemanage.service.IFileManageService;
import com.c503.sc.filemanage.utils.C503FileUtils;
import com.c503.sc.filemanage.utils.CompressFileUtil;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉文件上传下载以及信息保存操作 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年8月20日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "fileManageService")
public class FileManageServiceImpl extends BaseServiceImpl<FileInfoEntity>
    implements IFileManageService {
    /** 操作文件数据库接口 */
    @Resource(name = "fileManageDao")
    private IFileManageDao fileManageDao;
    
    /** 操作fastdfs文件服务器接口 */
    @Resource(name = "fastdfsClientService")
    private IFastdfsClientService fastdfsClientService;
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(FileManageServiceImpl.class);
    
    @Override
    public List<FileInfoEntity> findByParams(Map<String, Object> map)
        throws Exception {
        return this.fileManageDao.findByParams(map);
    }
    
    @Override
    public List<String[]> saveFileToFastdfs(List<FileInfoEntity> files)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, files);
        MultipartFile multipartFile;
        String[] result = null;
        List<String[]> list = new ArrayList<String[]>();
        if (0 < files.size()) {
            for (FileInfoEntity fileInfoEntity : files) {
                multipartFile = fileInfoEntity.getFile();
                String orgFileName = fileInfoEntity.getOrgFileName();
                String lastName =
                    orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
                
                try {
                    result =
                        this.fastdfsClientService.upLoadFileToFastdfs(multipartFile.getInputStream(),
                            lastName);
                } catch (Exception e) {
                    // 上传失败
                    list = null;
                    throw new CustomException(
                        FileManageConstant.FILE_UPLOAD_FAIL_E);
                }
                LOGGER.info(FileManageConstant.SUC_OPTION, result);
                if (null != result) {
                    list.add(result);
                }
            }
        } else {
            // 无文件可上传
            throw new CustomException(FileManageConstant.FILE_NOT_FILE_UPLOAD_E);
        }
        LOGGER.debug(SystemContants.DEBUG_END, files);
        
        return list;
    }
    
    // 批量保存
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public List<FileInfoEntity> saveFileAndInfo(List<FileInfoEntity> files)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, files);
        String groupName = "";
        String remoteFilename = "";
        
        if (null != files) {
            List<String[]> result = this.saveFileToFastdfs(files);
            for (int i = 0; i < result.size(); i++) {
                FileInfoEntity fileInfoEntity = files.get(i);
                fileInfoEntity.setId(C503StringUtils.createUUID());
                String[] res = result.get(i);
                groupName = res[0];
                remoteFilename = res[1];
                fileInfoEntity.setFileGroup(groupName);
                fileInfoEntity.setFilePath(remoteFilename);
            }
            boolean re = this.fileManageDao.saveBatch(files) > 0 ? true : false;
            LOGGER.info(FileManageConstant.SUC_OPTION, re);
            if (!re) {
                this.deleteFileFromFastdfs(files);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, files);
        
        return files;
    }
    
    // 保存单个
    /** {@inheritDoc} */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public FileInfoEntity saveFileAndInfo(FileInfoEntity file)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, file);
        String[] result = null;
        
        file.setId(C503StringUtils.createUUID());
        file.setCreateTime(new Date());
        file.setUpdateTime(new Date());
        MultipartFile multipartFile = file.getFile();
        String orgFileName = file.getOrgFileName();
        String lastName =
            orgFileName.substring(orgFileName.lastIndexOf(".") + 1);
        try {
            result =
                this.fastdfsClientService.upLoadFileToFastdfs(multipartFile.getInputStream(),
                    lastName);
        } catch (Exception e) {
            // 上传失败
            throw new CustomException(FileManageConstant.FILE_UPLOAD_FAIL_E, e);
        }
        file.setFileGroup(result[0]);
        file.setFilePath(result[1]);
        this.fileManageDao.saveOne(file);
        // 记录操作成功信息
        LOGGER.info(FileManageConstant.SUC_OPTION, file);
        LOGGER.debug(SystemContants.DEBUG_END, file);
        
        return file;
    }
    
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public boolean deleteFileAndInfo(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        map.put("updateTime", new Date());
        int delFileBack = -1;
        boolean result = false;
        List<FileInfoEntity> lists = null;
        if (null != map) {
            String[] fileIds = (String[]) map.get("ids");
            if (null != fileIds && fileIds.length > 0) {
                lists = this.findFileInfoFromDB(fileIds);
                result = this.deleteFileInfoFromDB(map);
                if (result) {
                    delFileBack = this.deleteFileFromFastdfs(lists);
                    if (0 == delFileBack) {
                        result = true;
                    } else if (2 == delFileBack) {
                        throw new CustomException(
                            FileManageConstant.FILE_NOT_EXSITS, delFileBack);
                    }
                }
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return result;
    }
    
    @Override
    public FileInfoEntity findFileInfoFromDB(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        FileInfoEntity fileInfo = null;
        if (C503StringUtils.isNotEmpty(id)) {
            fileInfo = this.fileManageDao.findById(id);
        }
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return fileInfo;
    }
    
    @Override
    public List<FileInfoEntity> findFileInfoFromDB(String[] fileIds)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, fileIds);
        Map<String, Object> map = new HashMap<>();
        List<String> ids = new ArrayList<>();
        if (fileIds != null) {
            for (String string : fileIds) {
                if (string != null) {// 剔除null
                    ids.add(string);
                }
            }
            String[] ids0 = new String[ids.size()];
            fileIds = ids.toArray(ids0);
        }
        map.put("ids", fileIds);
        map.put("remove", "0");
        List<FileInfoEntity> list = this.fileManageDao.findByIds(map);
        LOGGER.debug(SystemContants.DEBUG_END, fileIds);
        
        return list;
    }
    
    @Override
    public File findFileFromFastdfs(String[] fileIds)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, fileIds);
        // 服务中存放文件的现有文件名
        String fileName = "";
        // 服务器上临时创建的目录用来存放下载的文件
        String tempPath = "";
        // 存放在临时目录中的全路径，即目录路径+文件名
        String destFilePath = "";
        // 压缩文件夹的全路径，含文件名
        String zipPath = "";
        // 用于向controller返回文件对象
        File file = null;
        
        // 获取服务器临时目录的路径
        tempPath = this.querySystemName();
        
        zipPath = tempPath + ".zip";
        // 1.在服务器上创建临时目录
        File reDir = C503FileUtils.createDir(tempPath);
        if (null != reDir) {
            List<FileInfoEntity> list = this.findFileInfoFromDB(fileIds);
            if (null != list) {
                for (int i = 0; i < list.size(); i++) {
                    FileInfoEntity fileInfo = list.get(i);
                    fileName = fileInfo.getOrgFileName();
                    String group = fileInfo.getFileGroup();
                    String remotePath = fileInfo.getFilePath();
                    
                    destFilePath =
                        C503FileUtils.addSeparator(tempPath) + fileName;
                    
                    byte[] result =
                        this.fastdfsClientService.downLoadFile(group,
                            remotePath);
                    if (null != result) {
                        // 2.向临时目录中写文件
                        C503FileUtils.copyFile(result, destFilePath);
                    }
                }
                // 3.压缩临时文件夹
                CompressFileUtil.zipFile(tempPath, zipPath);
                // 4.向controller返回压缩文件
                file = new File(zipPath);
                // 5.删除服务器上的临时文件夹
                C503FileUtils.delete(tempPath);
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, fileIds);
        
        return file;
    }
    
    // 批量编辑图片：直接将上传图片，然后将上传的图片的结果返回并更新数据库，如果数据库更新失败回滚
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public List<FileInfoEntity> updateFileAndFastdfs(List<FileInfoEntity> files)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, files);
        List<FileInfoEntity> list = null;
        String[] bakIds = new String[files.size()];
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (null != files) {
            for (int i = 0; i < files.size(); i++) {
                bakIds[i] = files.get(i).getId();
                map.put("updateBy", files.get(i).getUpdateBy());
            }
            map.put("updateDate", new Date());
            map.put("remove", SystemContants.OFF);
            map.put("ids", bakIds);
            
            list = this.saveFileAndInfo(files);
            if (null != list) {
                boolean result = this.deleteFileAndInfo(map);
                if (!result) {
                    list = null;
                }
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, files);
        
        return list;
    }
    
    // 返回单个文件流
    @Override
    @Transactional(readOnly = false)
    public Map<String, Object> returnOutputStream(String id)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, id);
        String fileGroup = "";
        String remoteFilePath = "";
        String orgFileName = "";
        byte[] byt = null;
        Map<String, Object> map = new HashMap<>();
        if (null != id) {
            try {
                FileInfoEntity fileInfoEntity = this.fileManageDao.findById(id);
                fileGroup = fileInfoEntity.getFileGroup();
                remoteFilePath = fileInfoEntity.getFilePath();
                orgFileName = fileInfoEntity.getFileName();
                byt =
                    this.fastdfsClientService.downLoadFile(fileGroup,
                        remoteFilePath);
                if (null == byt) {
                    map = null;
                    throw new CustomException(
                        FileManageConstant.FILE_NOT_EXSITS, byt);
                }
            } catch (Exception e) {
                throw new CustomException(FileManageConstant.FILE_NOT_EXSITS,
                    byt);
            }
        }
        map.put("orgFileName", orgFileName);
        map.put("byte", byt);
        LOGGER.debug(SystemContants.DEBUG_END, id);
        
        return map;
    }
    
    // 返回单个文件流(文件类型)
    @Override
    public Map<String, Object> returnOutputStreamByFileCode(String fileCode)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, fileCode);
        String fileGroup = "";
        String remoteFilePath = "";
        String orgFileName = "";
        byte[] byt = null;
        Map<String, Object> map = new HashMap<String, Object>();
        if (null != fileCode) {
            try {
                FileInfoEntity fileInfoEntity =
                    this.fileManageDao.findByFileCode(fileCode);
                fileGroup = fileInfoEntity.getFileGroup();
                remoteFilePath = fileInfoEntity.getFilePath();
                orgFileName = fileInfoEntity.getFileName();
                byt =
                    this.fastdfsClientService.downLoadFile(fileGroup,
                        remoteFilePath);
                if (null == byt) {
                    map = null;
                    throw new CustomException(
                        FileManageConstant.FILE_NOT_EXSITS, byt);
                }
            } catch (Exception e) {
                throw new CustomException(FileManageConstant.FILE_NOT_EXSITS,
                    byt);
            }
        }
        map.put("orgFileName", orgFileName);
        map.put("byte", byt);
        LOGGER.debug(SystemContants.DEBUG_END, fileCode);
        
        return map;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.c503.sc.framework.core.service.impl.BaseServiceImpl#getBaseDao()
     */
    @Override
    public IBaseDao<FileInfoEntity> getBaseDao() {
        return null;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see com.c503.sc.framework.core.service.impl.BaseServiceImpl#logger()
     */
    @Override
    protected LoggingManager logger() {
        return LOGGER;
    }
    
    /**
     * 〈一句话功能简述〉查询当前应用服务器系统，并返回文件下载存放的临时目录路径 〈功能详细描述〉
     * 
     * @return 文件下载存放的临时目录
     * @see [类#方法]
     */
    private String querySystemName() {
        // 文件下载存放的临时目录
        String tempPath = "";
        
        // 获取当前应用服务器操作系统
        String systemName = System.getProperties().getProperty("os.name");
        if (systemName.contains("Windows")) {
            System.out.println(systemName);
            tempPath =
                ResourceManager.getMessage(FileManageConstant.W_ZIP_TEMP,
                    C503StringUtils.createUUID());
        } else {
            tempPath =
                ResourceManager.getMessage(FileManageConstant.L_ZIP_TEMP,
                    C503StringUtils.createUUID());
        }
        
        return tempPath;
    }
    
    /**
     * 〈一句话功能简述〉逻辑删除文件数据表信息 〈功能详细描述〉
     * 
     * @param map 删除条件
     * @return 成功true，反之false
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    @Transactional(readOnly = false)
    private boolean deleteFileInfoFromDB(Map<String, Object> map)
        throws Exception {
        logger().debug(SystemContants.DEBUG_START, map);
        map.put("remove", SystemContants.OFF);
        // result = this.fileManageDao.deleteBatch(map) > 0 ? true : false;
        try {
            this.fileManageDao.deleteBatch(map);
        } catch (Exception e) {
            logger().info(FileManageConstant.FAIL_OPTION, false);
            logger().debug(SystemContants.DEBUG_END, map);
            return false;
        }
        logger().info(FileManageConstant.SUC_OPTION, true);
        logger().debug(SystemContants.DEBUG_END, map);
        // return result;
        return true;
    }
    
    /**
     * 〈一句话功能简述〉删除fastdfs文件服务器上的文件 〈功能详细描述〉
     * 
     * @param files 文件对象集合
     * @return 删除文件结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private int deleteFileFromFastdfs(List<FileInfoEntity> files)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, files);
        String groupName = "";
        String remoteFilename = "";
        int delFileBack = -1;
        
        for (FileInfoEntity fileInfoEntity : files) {
            groupName = fileInfoEntity.getFileGroup();
            remoteFilename = fileInfoEntity.getFilePath();
            delFileBack =
                this.fastdfsClientService.deleteFileFromFastdfs(groupName,
                    remoteFilename);
            LOGGER.info(FileManageConstant.SUC_OPTION, delFileBack);
            if (2 == delFileBack) {
                break;
            }
        }
        LOGGER.debug(SystemContants.DEBUG_END, files);
        
        return delFileBack;
    }
}
