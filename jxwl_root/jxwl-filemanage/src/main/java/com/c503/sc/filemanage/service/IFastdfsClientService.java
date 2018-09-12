/**
 * 文件名：IFastdfsClientService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年9月15日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.service;

import java.io.InputStream;

import org.csource.fastdfs.StorageClient;

/**
 * 〈一句话功能简述〉操作fastdfs文件服务器 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年10月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IFastdfsClientService {
    /**
     * 〈一句话功能简述〉连接fastdfs文件服务器 〈功能详细描述〉
     * 
     * @return 客户端连接
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    StorageClient storageClientPerpo()
        throws Exception;
    
    /**
     * 〈一句话功能简述〉上传文件到fastdfs 〈功能详细描述〉
     * 
     * @param in 输入流
     * @param fileType 文件类型
     * @return 返回上传成功文件存放的组和路径
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    String[] upLoadFileToFastdfs(InputStream in, String fileType)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉从fastdfs删除文件 〈功能详细描述〉
     * 
     * @param groupName 文件存放的组
     * @param remoteFilename 文件路径
     * @return 返回删除成功的值（0代表删除成功）
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    int deleteFileFromFastdfs(String groupName, String remoteFilename)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉从fastdfs下载文件 〈功能详细描述〉
     * 
     * @param groupName 文件存放的组
     * @param remoteFilename 文件路径
     * @return 返回文件流
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    byte[] downLoadFile(String groupName, String remoteFilename)
        throws Exception;
}
