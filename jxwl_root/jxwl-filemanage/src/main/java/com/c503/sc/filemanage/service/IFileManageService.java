/**
 * 文件名：ISaveFileToDisk.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月13日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.c503.sc.base.service.IBaseService;
import com.c503.sc.filemanage.bean.FileInfoEntity;

/**
 * 〈一句话功能简述〉文件上传下载相关操作 〈功能详细描述〉
 * 参考接口 {@link IFastdfsFileService}
 * 
 * @author zz
 * @version [版本号, 2015年8月20日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IFileManageService extends IBaseService<FileInfoEntity> {
    /**
     * 〈一句话功能简述〉保存上传的文件到fastdfs文件服务器，通过传入文件对象list 〈功能详细描述〉
     * 
     * @param files 文件对象list集合
     * @return 返回上传成功之后的集合：集合里面的数组包含
     * @throws Exception 运行时异常
     * @see [类#方法]
     */
    List<String[]> saveFileToFastdfs(List<FileInfoEntity> files)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉保存上传的文件到fastdfs文件服务器和文件信息到数据库
     * 〈功能详细描述〉多个
     * 
     * @param files 文件对象list集合
     * @return 文件对象list集合
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    List<FileInfoEntity> saveFileAndInfo(List<FileInfoEntity> files)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉保存上传的文件到fastdfs文件服务器和文件信息到数据库
     * 〈功能详细描述〉单个
     * 
     * @param file 文件对象
     * @return 新增文件对象
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    FileInfoEntity saveFileAndInfo(FileInfoEntity file)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉删除fastdfs上的文件和数据库中的文件信息 〈功能详细描述〉
     * 
     * @param map map集合，规则如下：map.put("ids", new String[]{"..."});
     *            map.put("updateBy", "...");
     * @return 成功返回true，否则返回false
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员 ]
     */
    boolean deleteFileAndInfo(Map<String, Object> map)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉查询单个文件信息
     * 〈功能详细描述〉
     * 
     * @param id 文件id
     * @return 返回单个文件信息
     * @throws Exception 数据库异常
     * @see [类、类#方法、类#成员]
     */
    FileInfoEntity findFileInfoFromDB(String id)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉查询多个文件信息 〈功能详细描述〉
     * 
     * @param fileIds 文件is数组
     * @return 返回一条或多条文件信息
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    List<FileInfoEntity> findFileInfoFromDB(String[] fileIds)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉从fastdfs上查找文件 〈功能详细描述〉
     * 
     * @param fileIds 文件信息id数组
     * @return 返回文件
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    File findFileFromFastdfs(String[] fileIds)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉返回文件流 〈功能详细描述〉
     * 
     * @param id 文件id
     * @return 返回byte[]数组
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> returnOutputStream(String id)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉根据fileCode查询数据
     * 〈功能详细描述〉
     * 
     * @param fileCode 文件类型
     * @return FileInfoEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> returnOutputStreamByFileCode(String fileCode)
        throws Exception;
    
    /**
     * 
     * 〈一句话功能简述〉更新文件和信息 〈功能详细描述〉
     * 
     * @param files 文件对象list集合
     * @return 返回一个或多个文件对象
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    List<FileInfoEntity> updateFileAndFastdfs(List<FileInfoEntity> files)
        throws Exception;
    
}
