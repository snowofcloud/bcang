/**
 * 文件名：IFileService.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-6-30
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.c503.sc.base.service.IBaseService;
import com.c503.sc.filemanage.bean.FileInfoEntity;

/**
 * 文件业务接口
 * 提供文件的访问、上传功能
 * 
 * @author huangtw
 * @version [版本号, 2016-6-30]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface IFastdfsFileService extends IBaseService<FileInfoEntity> {
    
    /**
     * 保存文件
     * 文件保存包括文件物理保存和数据库文件映射保存
     * 
     * @param fileInfoEntity 当前保存对象
     * @return 当前保存对象
     * @throws Exception 运行时异常
     */
    FileInfoEntity save(FileInfoEntity fileInfoEntity)
        throws Exception;
    
    /**
     * 
     * 按照ID逻辑删除文件
     * 文件删除只删除数据库映射，不删除物理文件
     * 
     * @param updateBy 操作人
     * @param ids 要删除的文件ID数组
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int remove(String updateBy, String... ids)
        throws Exception;
    
    /**
     * 修改文件
     * 如果要修改的文件中包括文件流（物理文件），
     * 那么物理文件是保存，数据库文件映射是修改，文件映射同时指向新保存的纹理文件
     * 如果没有文件流，则只修改数据库文件映射。
     * 
     * @param fileInfoEntity 当前修改对象
     * @return 当前修改对象
     * @throws Exception 运行时异常
     */
    FileInfoEntity update(FileInfoEntity fileInfoEntity)
        throws Exception;
    
    /**
     * 按照ID查询文件对象
     * 物理文件通过 FileInfoEntity对象的方法getDownloadFile获取
     * 
     * @param id 主键ID
     * @return 查询实体
     * @throws Exception 运行时异常
     */
    FileInfoEntity findById(Serializable id)
        throws Exception;
    
    /**
     * 按照ID查询数据库文件映射对象
     * 注意此方法不会返回物理文件，需要物理文件参见 接口 findById
     * 
     * @param id 主键ID
     * @return 查询实体
     * @throws Exception 运行时异常
     */
    FileInfoEntity findByIdOutFile(Serializable id)
        throws Exception;
    
    /**
     * 查询所有文件对象
     * 此方法返回的文件对象不包括物理文件（的文件流）
     * 
     * @return 查询实体集合
     * @throws Exception 运行时异常
     */
    List<FileInfoEntity> findAll()
        throws Exception;
    
    /**
     * 
     * 分页查询文件对象
     * 此方法返回的文件对象不包括物理文件（的文件流）
     * 
     * @param params 查询条件
     * @return 文件集合
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    List<FileInfoEntity> findByParams(Map<String, Object> params)
        throws Exception;
    
    /**
     * 按条件查询文件总数
     * 
     * @param params 查询条件
     * @return 文件总条数
     * @throws Exception 运行时异常
     * @see [类、类#方法、类#成员]
     */
    int getCount(Map<String, Object> params)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉返回文件流
     * 〈功能详细描述〉
     * 
     * @param id id
     * @return Map<String, Object>
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    Map<String, Object> returnOutputStream(String id)
        throws Exception;
    
}
