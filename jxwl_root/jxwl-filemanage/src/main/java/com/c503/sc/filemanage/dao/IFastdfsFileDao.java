/**
 * 文件名：IFileManageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月13日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.c503.sc.base.dao.IBaseDao;
import com.c503.sc.filemanage.bean.FileInfoEntity;

/**
 * 
 * 〈一句话功能简述〉对存放在数据库上的信息的增删查操作 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2015年8月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Repository(value = "fastdfsFileDao")
public interface IFastdfsFileDao extends IBaseDao<FileInfoEntity> {
    /**
     * 〈一句话功能简述〉请使用save方法
     * 〈功能详细描述〉
     * 
     * @param record 单个文件信息
     * @return 返回保存条数结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int saveOne(FileInfoEntity record)
        throws Exception;
    
    /**
     * 批量逻辑删除
     * 
     * @param updateBy 操作人
     * @param ids 要删除的文件ID数组
     * @return 影响条数
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    int remove(@Param("updateBy")
    String updateBy, @Param("ids")
    String... ids)
        throws Exception;
    
    /**
     * 〈一句话功能简述〉修改 〈功能详细描述〉
     * 
     * @param record 文件对象
     * @return 返回更新条数结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int updateById(FileInfoEntity record)
        throws Exception;
    
    /**
     * 〈一句话功能简述> 根据id查询单条记录 〈功能详细描述〉
     * 
     * @param id 文件id
     * @return 文件对象
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    FileInfoEntity findById(String id)
        throws Exception;
}
