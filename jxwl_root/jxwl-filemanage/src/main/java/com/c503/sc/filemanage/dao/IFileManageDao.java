/**
 * 文件名：IFileManageDao.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年8月13日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.filemanage.dao;

import java.util.List;
import java.util.Map;

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
@Repository(value = "fileManageDao")
public interface IFileManageDao extends IBaseDao<FileInfoEntity> {
    /**
     * 〈一句话功能简述〉插入多条记录 文件对象的集合
     * 〈功能详细描述〉
     * 
     * @param records 文件对象的集合
     * @return 返回插入结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int saveBatch(@Param("records") List<FileInfoEntity> records)
        throws Exception;
    /**
     * 〈一句话功能简述〉批量逻辑删除 〈功能详细描述〉
     * 
     * @param map 删除条件信息
     * @return 删除条数结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int deleteBatch(Map<String, Object> map)
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
    /**
     * 〈一句话功能简述〉根据fileCode查询数据
     * 〈功能详细描述〉
     * 
     * @param fileCode 文件类型
     * @return FileInfoEntity
     * @throws Exception Exception
     * @see [类、类#方法、类#成员]
     */
    FileInfoEntity findByFileCode(String fileCode)
        throws Exception;
    /**
     * 〈一句话功能简述〉查询多条记录， 通过传入id的数组 〈功能详细描述〉
     * 
     * @param map 查询条件
     * @return 查询的记录
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    List<FileInfoEntity> findByIds(Map<String, Object> map)
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
     * 〈一句话功能简述〉 批量修改〈功能详细描述〉
     * 
     * @param files 文件list集合
     * @return 返回更新条数结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int updateByIdList(List<FileInfoEntity> files)
        throws Exception;
    /**
     * 〈一句话功能简述〉插入单条记录 〈功能详细描述〉
     * 
     * @param record 单个文件信息
     * @return 返回保存条数结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int saveOne(FileInfoEntity record)
        throws Exception;
    
    /************************************************ 以下未用 *****************************************************/
    
    /**
     * 
     * 〈一句话功能简述〉根据文件id数组进行批量删除 〈功能详细描述〉
     * 
     * @param fileIds 文件id数组
     * @return 返回删除条数结果
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    int deleteByIds(String[] fileIds)
        throws Exception;
}
