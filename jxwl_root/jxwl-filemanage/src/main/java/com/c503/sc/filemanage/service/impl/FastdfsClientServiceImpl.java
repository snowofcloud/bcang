/**
 * 文件名：IFastdfsClientServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：操作fastdfs文件服务器
 * 修改时间：2015年9月15日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.service.impl;

import java.io.InputStream;
import java.net.InetSocketAddress;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerGroup;
import org.csource.fastdfs.TrackerServer;
import org.springframework.stereotype.Service;

import com.c503.sc.filemanage.constants.FileManageConstant;
import com.c503.sc.filemanage.service.IFastdfsClientService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.common.SystemContants;

/**
 * 
 * 〈一句话功能简述〉操作fastdfs文件服务器 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年10月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "fastdfsClientService")
public class FastdfsClientServiceImpl implements IFastdfsClientService {
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(FastdfsClientServiceImpl.class);
    
    @Override
    public StorageClient storageClientPerpo()
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START);
        ClientGlobal.setG_anti_steal_token(false);
        ClientGlobal.setG_charset(ResourceManager.getMessage(FileManageConstant.CHARSET));
        ClientGlobal.setG_connect_timeout(Integer.parseInt(ResourceManager.getMessage(FileManageConstant.CONNECT_TIMEOUT)));
        ClientGlobal.setG_network_timeout(Integer.parseInt(ResourceManager.getMessage(FileManageConstant.NETWORK_TIMEOUT)));
        ClientGlobal.setG_secret_key(ResourceManager.getMessage(FileManageConstant.SECRET_KEY));
        ClientGlobal.setG_tracker_http_port(Integer.parseInt(ResourceManager.getMessage(FileManageConstant.TRACKER_HTTP_PORT)));
        InetSocketAddress[] trackerServers = new InetSocketAddress[1];
        trackerServers[0] =
            new InetSocketAddress(
                ResourceManager.getMessage(FileManageConstant.HOST_NAME_ADDR),
                Integer.parseInt(ResourceManager.getMessage(FileManageConstant.HOST_NAME_PORT)));
        
        ClientGlobal.setG_tracker_group(new TrackerGroup(trackerServers));
        
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        
        StorageServer storageServer = null;
        StorageClient storageClient =
            new StorageClient(trackerServer, storageServer);
        LOGGER.debug(SystemContants.DEBUG_END);
        
        return storageClient;
    }
    
    @Override
    public String[] upLoadFileToFastdfs(InputStream in, String fileType)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, in);
        StorageClient storageClient = this.storageClientPerpo();
        
        NameValuePair[] metaList = new NameValuePair[FileManageConstant.THREE];
        metaList[0] = new NameValuePair("width", "120");
        metaList[1] = new NameValuePair("heigth", "120");
        metaList[2] = new NameValuePair("author", "gary");
        
        byte[] fileBuff = null;
        if (in != null) {
            int len = in.available();
            fileBuff = new byte[len];
            in.read(fileBuff);
        }
        
        // 客户端 的 上传 文件 results 是 结果 返回 （存储位置 ： 组名 、文件索引 ）
        String[] results =
            storageClient.upload_file(fileBuff, fileType, metaList);
        
        LOGGER.info(0, FileManageConstant.SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, in);
        
        return results;
    }
    
    @Override
    public int deleteFileFromFastdfs(String groupName, String remoteFilename)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, remoteFilename);
        
        StorageClient storageClient = this.storageClientPerpo();
        int delId = storageClient.delete_file(groupName, remoteFilename);
        
        LOGGER.info(0, FileManageConstant.SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, remoteFilename);
        
        return delId;
    }
    
    @Override
    public byte[] downLoadFile(String groupName, String remoteFilename)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, remoteFilename);
        
        StorageClient storageClient = this.storageClientPerpo();
        
        LOGGER.info(0, FileManageConstant.SUC_OPTION);
        LOGGER.debug(SystemContants.DEBUG_END, remoteFilename);
        
        return storageClient.download_file(groupName, remoteFilename);
    }
}
