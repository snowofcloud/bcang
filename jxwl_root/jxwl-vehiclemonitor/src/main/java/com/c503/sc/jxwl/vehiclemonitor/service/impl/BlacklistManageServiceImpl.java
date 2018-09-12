/**
 * 文件名：BlacklistManageServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-26
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c503.sc.base.exception.CustomException;
import com.c503.sc.dict.bean.DictionaryValueEntity;
import com.c503.sc.dict.dao.IDictionaryValueDao;
import com.c503.sc.jxwl.common.constant.BizConstants;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.vehiclemonitor.bean.BlacklistManageEntity;
import com.c503.sc.jxwl.vehiclemonitor.dao.IBlacklistManageDao;
import com.c503.sc.jxwl.vehiclemonitor.service.IBlacklistManageService;
import com.c503.sc.jxwl.vehiclemonitor.vo.AlarmNumVo;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.c503.sc.utils.exceltools.Content;
import com.c503.sc.utils.exceltools.ExportSheet;
import com.c503.sc.utils.exceltools.Header;
import com.c503.sc.utils.exceltools.Title;

/**
 * 
 * 〈一句话功能简述〉黑名单管理业务层
 * 〈功能详细描述〉
 * 
 * @author guqh
 * @version [版本号, 2016-7-26]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "blacklistManageService")
public class BlacklistManageServiceImpl implements IBlacklistManageService {
    
    /** 日志器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(BlacklistManageServiceImpl.class);
    
    /** 黑名单信息Dao */
    @Resource(name = "blacklistManageDao")
    private IBlacklistManageDao blacklistManageDao;
    
    /** 数据字典接口 */
    @Resource(name = "dictionaryValueDao")
    private IDictionaryValueDao dictionaryValueDao;
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public List<BlacklistManageEntity> findByParams(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        List<BlacklistManageEntity> list =
            new ArrayList<BlacklistManageEntity>();
        try {
            map.put("remove", SystemContants.ON);
            list = this.blacklistManageDao.findByParams(map);
            int size = list.size();
            if (0 < size) {
                for (BlacklistManageEntity entity : list) {
                    String name = findNameByCode(entity.getObjectType());
                    String type = findNameByCode(entity.getBlacklistType());
                    entity.setObjectType(name);
                    entity.setBlacklistType(type);
                }
            }
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return list;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Object save(BlacklistManageEntity entity)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, entity);
        
        entity.setId(C503StringUtils.createUUID());
        entity.setRemove(SystemContants.ON);
        Date curDate = new Date();
        entity.setCreateTime(curDate);
        entity.setUpdateTime(curDate);
        
        try {
            this.blacklistManageDao.save(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, entity);
        }
        LOGGER.debug(SystemContants.DEBUG_END, entity);
        
        return entity.getId();
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object update(BlacklistManageEntity entity)
        throws Exception {
        try {
            this.blacklistManageDao.update(entity);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return entity;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public BlacklistManageEntity findById(String id)
        throws Exception {
        BlacklistManageEntity entity = null;
        try {
            entity = this.blacklistManageDao.findById(id);
            String name = findNameByCode(entity.getObjectType());
            String type = findNameByCode(entity.getBlacklistType());
            entity.setObjectType(name);
            entity.setBlacklistType(type);
        }
        catch (Exception e) {
            throw new CustomException(BizConstants.ADD_BLACK_NOT_EXIST, id);
        }
        return entity;
    }
    
    @Override
    public int delete(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        int delLine = 0;
        map.put("remove", SystemContants.OFF);
        
        try {
            delLine = this.blacklistManageDao.delete(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        LOGGER.debug(SystemContants.DEBUG_END, map);
        
        return delLine;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    @Transactional(rollbackFor = java.lang.Exception.class)
    public Object updateAlarmNum(AlarmNumVo vo)
        throws Exception {
        try {
            this.blacklistManageDao.updateAlarmNum(vo);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return vo;
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public AlarmNumVo findAlarmNumById(Map<String, Object> map)
        throws Exception {
        AlarmNumVo vo = null;
        try {
            vo = this.blacklistManageDao.findAlarmNumById(map);
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e);
        }
        return vo;
    }
    
    /**
     * {@inheritDoc}
     */
    public Map<String, Object> exportExcel(Map<String, Object> map)
        throws Exception {
        LOGGER.debug(SystemContants.DEBUG_START, map);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 查询满足条件的渔港信息集合
        map.put("remove", SystemContants.ON);
        List<BlacklistManageEntity> list = null;
        try {
            list = this.blacklistManageDao.findByParams(map);
            
        }
        catch (Exception e) {
            throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
        }
        // 生成sheet对象
        ExportSheet sheet = createSheet(list);
        resultMap.put("sheet", sheet);
        resultMap.put("excelName", "报警信息表");
        LOGGER.debug(SystemContants.DEBUG_END, map);
        return resultMap;
    }
    
    /**
     * 
     * 〈一句话功能简述〉将数据组装为excel表单
     * 〈功能详细描述〉
     * 
     * @param dataList 数据结合
     * @return excel表单
     * @see [类、类#方法、类#成员]
     */
    private ExportSheet createSheet(List<BlacklistManageEntity> dataList) {
        ExportSheet sheet = new ExportSheet();
        Header header = new Header();
        Object[] headNames =
            new Object[] {"序号", "拉黑对象", "拉黑类型", "拉黑原因", "拉黑时间"};
        header.setHeadNames(headNames);
        // 设置表的标题
        Title title = new Title();
        title.setTitleName("黑名单信息表");
        // 设置表头对应的属性
        String[] fields =
            new String[] {"rowNum", "blacklistName", "blacklistType",
                "blacklistReason", "blacklistDate"};
        
        Content content = new Content();
        content.setFieldNames(fields);
        content.setDataList(dataList);
        sheet.setHeader(header);
        sheet.setTitle(title);
        sheet.setContent(content);
        return sheet;
    }
    
    /**
     * 
     * 〈一句话功能简述〉根据字典的value值获取字典的名称 〈功能详细描述〉
     * 
     * @param value
     *            字典的value
     * @return 获取字典的名称
     * @throws Exception
     *             数据库异常
     * @see [类、类#方法、类#成员]
     */
    private String findNameByCode(String value)
        throws Exception {
        String name = "";
        if (C503StringUtils.isNotEmpty(value)) {
            DictionaryValueEntity dic =
                this.dictionaryValueDao.findDicByValue(value);
            name = null == dic ? "" : dic.getName();
        }
        return name;
    }
    
    /**
     * 
     * 〈一句话功能简述〉根据法人代码获取其在黑名单里的条数 〈功能详细描述〉
     * 
     * @param corporateNo
     *            企业的法人代码
     * @return int 获取条数
     * @throws Exception
     *             数据库异常
     * @see [类、类#方法、类#成员]
     */
	@Override
	public int findNumById(String corporateNo) 
			throws Exception {
		int count = this.blacklistManageDao.findNumById(corporateNo);
		return count;
	}
}
