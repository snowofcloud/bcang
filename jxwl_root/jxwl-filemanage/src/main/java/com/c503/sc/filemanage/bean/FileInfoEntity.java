/**
 * 文件名：FileManageEntity.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月13日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.bean;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.c503.sc.base.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class FileInfoEntity extends BaseEntity {
    /** 序列号 */
    private static final long serialVersionUID = 1L;
    
    /** 上传的文件对象 */
    private MultipartFile file;
    
    /** 下载时文件对象 */
    private File downloadFile;
    
    /** 文件存放的组 */
    private String fileGroup;
    
    /** 原始文件名 */
    private String orgFileName;
    
    /** 文件名称 */
    private String fileName;
    
    /** 文件物理路径 */
    private String filePath;
    
    /** 文件大小 */
    private Long fileSize;
    
    /** 备注 */
    private String remark;
    
    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date uploadTime;
    
    /** 文件信息集合 */
    private List<FileInfoEntity> lists;
    
    /**
     * 〈一句话功能简述〉获取文件对象 〈功能详细描述〉
     * 
     * @return 文件对象
     * @see [类、类#方法、类#成员]
     */
    public MultipartFile getFile() {
        return file;
    }
    
    /**
     * 〈一句话功能简述〉设置文件对象 〈功能详细描述〉
     * 
     * @param mulFile 多文件文件对象
     * @see [类、类#方法、类#成员]
     */
    public void setFile(MultipartFile mulFile) {
        this.file = mulFile;
    }
    
    /**
     * 〈一句话功能简述〉获取文件存放的组 〈功能详细描述〉
     * 
     * @return 文件存放的组
     * @see [类、类#方法、类#成员]
     */
    public String getFileGroup() {
        return fileGroup;
    }
    
    /**
     * 〈一句话功能简述〉获取文件存放的组 〈功能详细描述〉
     * 
     * @param fileGroup 文件存放的组
     * @see [类、类#方法、类#成员]
     */
    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }
    
    /**
     * 〈一句话功能简述〉获取文件原始名 〈功能详细描述〉
     * 
     * @return 文件原始名
     * @see [类、类#方法、类#成员]
     */
    public String getOrgFileName() {
        return orgFileName;
    }
    
    /**
     * 〈一句话功能简述〉设置文件原始名 〈功能详细描述〉
     * 
     * @param orgFileName 文件原始名
     * @see [类、类#方法、类#成员]
     */
    public void setOrgFileName(String orgFileName) {
        this.orgFileName = orgFileName == null ? null : orgFileName.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取保存后文件名 〈功能详细描述〉
     * 
     * @return 保存后文件名
     * @see [类、类#方法、类#成员]
     */
    public String getFileName() {
        return fileName;
    }
    
    /**
     * 〈一句话功能简述〉设置保存后文件名 〈功能详细描述〉
     * 
     * @param fileName 保存后文件名
     * @see [类、类#方法、类#成员]
     */
    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取文件保存路径 〈功能详细描述〉
     * 
     * @return 文件保存路径
     * @see [类、类#方法、类#成员]
     */
    public String getFilePath() {
        return filePath;
    }
    
    /**
     * 〈一句话功能简述〉设置文件保存路径 〈功能详细描述〉
     * 
     * @param filePath 文件保存路径
     * @see [类、类#方法、类#成员]
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }
    
    /**
     * 〈一句话功能简述〉获取文件大小 〈功能详细描述〉
     * 
     * @return 文件大小
     * @see [类、类#方法、类#成员]
     */
    public Long getFileSize() {
        return fileSize;
    }
    
    /**
     * 〈一句话功能简述〉设置文件大小 〈功能详细描述〉
     * 
     * @param fileSize 文件大小
     * @see [类、类#方法、类#成员]
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    
    /**
     * 〈一句话功能简述〉获取文件说明 〈功能详细描述〉
     * 
     * @return 文件说明
     * @see [类、类#方法、类#成员]
     */
    public String getRemark() {
        return remark;
    }
    
    /**
     * 〈一句话功能简述〉设置文件说明 〈功能详细描述〉
     * 
     * @param remark 文件说明
     * @see [类、类#方法、类#成员]
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    
    /**
     * 〈一句话功能简述〉上传时间
     * 〈功能详细描述〉
     * 
     * @return 上传时间
     * @see [类、类#方法、类#成员]
     */
    public Date getUploadTime() {
        return uploadTime;
    }
    
    /**
     * 〈一句话功能简述〉上传时间
     * 〈功能详细描述〉
     * 
     * @param uploadTime 上传时间
     * @see [类、类#方法、类#成员]
     */
    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }
    
    /**
     * 〈一句话功能简述〉获取文件list集合 〈功能详细描述〉
     * 
     * @return 文件list集合
     * @see [类、类#方法、类#成员]
     */
    public List<FileInfoEntity> getLists() {
        return lists;
    }
    
    /**
     * 〈一句话功能简述〉设置文件list集合 〈功能详细描述〉
     * 
     * @param lists 文件list集合
     * @see [类、类#方法、类#成员]
     */
    public void setLists(List<FileInfoEntity> lists) {
        this.lists = lists;
    }
    
    /**
     * 获取下载文件
     * 
     * @return 要下载的文件
     * @see [类、类#方法、类#成员]
     */
    public File getDownloadFile() {
        return downloadFile;
    }
    
    /**
     * 设置下载文件
     * 
     * @param downloadFile 要下载的文体
     * @see [类、类#方法、类#成员]
     */
    public void setDownloadFile(File downloadFile) {
        this.downloadFile = downloadFile;
    }
    
    @Override
    protected Object getBaseEntity() {
        return this;
    }
}
