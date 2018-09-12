/*
 * 文件名：IDictionaryServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2015年7月22日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.dict.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.c503.sc.dict.bean.DictionaryValueEntity;
import com.c503.sc.dict.constant.DicConstants;
import com.c503.sc.dict.dao.IDictBizComRelaDao;
import com.c503.sc.dict.dao.IDictionaryTypeDao;
import com.c503.sc.dict.dao.IDictionaryValueDao;
import com.c503.sc.dict.service.IDictionaryService;
import com.c503.sc.dict.vo.DictDataVo;
import com.c503.sc.dict.vo.DictVo;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503BeanUtils;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.NumberContant;
import com.c503.sc.utils.common.SystemContants;

/**
 * 〈一句话功能简述〉数据字典业务实现类 〈功能详细描述〉
 * 
 * @author duanhy
 * @version [版本号, 2015年8月24日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService {
    /** 日志记录器 */
    private static final LoggingManager LOGGER =
        LoggingManager.getLogger(DictionaryServiceImpl.class);
    
    /** 类型对象数据接口 */
    @Resource(name = "dictionaryTypeDao")
    private IDictionaryTypeDao dictionaryTypeDao;
    
    /** 值对象数据接口 */
    @Resource(name = "dictionaryValueDao")
    private IDictionaryValueDao dictionaryValueDao;
    
    /** 业务与常用字典类型关系数据接口 */
    @Resource(name = "dictBizComRelaDao")
    private IDictBizComRelaDao dictBizComRelaDao;
    
    // /** redis管理器 */
    // @Resource(name = "redisManager")
    // private RedisManager redisManager;
    
    /** {@inheritDoc} */
    @Override
    public Object findDictByCode(String code)
        throws Exception {
        // 记录程序进入方法调试日志
        LOGGER.debug(SystemContants.DEBUG_START, code);
        Object dictData = null;
        String source = ResourceManager.getMessage("source");
        if ("1".equals(source)) {
            dictData = this.findDictFromCache(code);
        }
        if ("2".equals(source)) {
            dictData = this.findDictFromDB(code);
        }
        // 记录程序结束方法调试日志
        LOGGER.debug(SystemContants.DEBUG_END, code);
        
        return dictData;
    }
    
    /** {@inheritDoc} */
    public Object findDictFromCache(String code)
        throws Exception {
        Object obj = null;
        // TODO 从缓存获取数据
        // if (!C503StringUtils.isEmpty(code)) {
        // Map<String, List<DictDataVo>> dict =
        // (Map<String, List<DictDataVo>>)
        // SerializeUtils.deserialize(redisManager.get(SystemContants.DICT.getBytes("UTF8")));
        // if (null != dict && dict.containsKey(code)) {
        // obj = dict.get(code);
        // }
        // if (null != dict && !dict.containsKey(code)) {
        // for (Map.Entry<String, List<DictDataVo>> entry : dict.entrySet()) {
        // List<DictDataVo> list = entry.getValue();
        // obj = findDictDataVoByCode(list, code);
        // if (null != obj) {
        // break;
        // }
        // }
        // }
        // }
        
        return obj;
    }
    
    /** {@inheritDoc} */
    public Object findDictFromDB(String code)
        throws Exception {
        Object result = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("remove", SystemContants.ON);
        // 根据长度判断查询哪张表
        if (C503StringUtils.isNotEmpty(code)
            && code.length() == NumberContant.THREE) {
            // 获得某个模块下所有的字典类型
            Map<String, List<DictVo>> typeMap =
                new HashMap<String, List<DictVo>>();
            params.put("code", code);
            
            List<DictDataVo> dictDatas =
                this.dictionaryTypeDao.findAllDictData(params);
            // 获得父节点
            List<DictDataVo> pdictDatas = findParentNode(dictDatas, code);
            findChildNode(dictDatas, pdictDatas);
            int size = pdictDatas.size();
            for (int i = 0; i < size; i++) {
                DictDataVo vo = pdictDatas.get(i);
                List<DictVo> items = new ArrayList<DictVo>();
                int itemsSize = vo.getDictData().size();
                for (int j = 0; j < itemsSize; j++) {
                    DictVo dict = new DictVo();
                    C503BeanUtils.copyProperties(dict, vo.getDictData().get(j));
                    items.add(dict);
                }
                DictVo dict = new DictVo();
                C503BeanUtils.copyProperties(dict, vo);
                typeMap.put(vo.getCode(), items);
            }
            // 查询业务与常用字典类型关联表
            Map<String, List<DictVo>> bizComMap = combineBizCommon(params);
            if (!CollectionUtils.isEmpty(bizComMap)) {
                typeMap.putAll(bizComMap);
            }
            result = typeMap;
            // 记录操作成功信息
            LOGGER.info(DicConstants.SUC_OPTION, result);
        }
        if (C503StringUtils.isNotEmpty(code)
            && code.length() == NumberContant.SIX) {
            // 获得某个模块下某个分类的明细
            params.put("pCode", code);
            result = (Object) this.dictionaryValueDao.findByCond(params);
            // 记录操作成功信息
            LOGGER.info(DicConstants.SUC_OPTION, result);
        }
        if (C503StringUtils.isNotEmpty(code)
            && code.length() == NumberContant.NINE) {
            // 获得明细
            params.put("code", code);
            result = (Object) this.dictionaryValueDao.findByCond(params);
            // 记录操作成功信息
            LOGGER.info(DicConstants.SUC_OPTION, result);
        }
        
        return result;
    }
    
    /**
     * 〈一句话功能简述〉组装业务和常用字典类型关系
     * 〈功能详细描述〉
     * 
     * @param params 查询条件，其中（remove:逻辑删除标识，code：字典编码）
     * @return 数据字典集合对象
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    private Map<String, List<DictVo>> combineBizCommon(
        Map<String, Object> params)
        throws Exception {
        Map<String, List<DictVo>> resultMap = null;
        List<DictDataVo> commonList = this.dictBizComRelaDao.findByCond(params);
        if (!CollectionUtils.isEmpty(commonList)) {
            resultMap = new HashMap<String, List<DictVo>>();
            int commonSize = commonList.size();
            for (int i = 0; i < commonSize; i++) {
                DictDataVo commonVo = commonList.get(i);
                resultMap.put(commonVo.getCode(), null);
                String temp = commonVo.getCode();
                List<DictVo> items = new ArrayList<DictVo>();
                params.put("typeCode", temp);
                List<DictionaryValueEntity> values =
                    this.dictionaryValueDao.findByParams(params);
                int valuesSize = values.size();
                for (int j = 0; j < valuesSize; j++) {
                    String tempPcode = values.get(j).getTypeCode();
                    if (temp.equals(tempPcode)) {
                        DictVo valueVo =
                            new DictVo(values.get(j).getValue(), values.get(j)
                                .getName(), values.get(j).getEnName());
                        items.add(valueVo);
                    }
                }
                resultMap.put(temp, items);
            }
        }
        return resultMap;
    }
    
    /**
     * 〈一句话功能简述〉某个模块下的分类以及明细
     * 〈功能详细描述〉
     * 
     * @param dictDatas 所有数据
     * @param code 编码
     * @return 模块列表
     * @see [类、类#方法、类#成员]
     */
    private List<DictDataVo> findParentNode(List<DictDataVo> dictDatas,
        String code) {
        List<DictDataVo> parentList = new ArrayList<DictDataVo>();
        int size = dictDatas.size();
        for (int i = 0; i < size; i++) {
            DictDataVo vo = dictDatas.get(i);
            if (code.equals(vo.getPcode())) {
                parentList.add(vo);
            }
        }
        return parentList;
    }
    
    /**
     * 〈一句话功能简述〉通过编码递归找具体数据 〈功能详细描述〉
     * 
     * @param list 数据字典对象
     * @param code 编码
     * @return DictDataVo 编码对应的字典对象
     * @see [类、类#方法、类#成员]
     */
    // private Object findDictDataVoByCode(List<DictDataVo> list, String code) {
    // int size = list.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo vo = list.get(i);
    // if (vo.getCode().equals(code)) {
    // return vo;
    // }
    // // 当当前节点为叶子节点的时候，不进行下一次递归查询
    // if (vo.getDictData().size() > 0) {
    // Object tem = findDictDataVoByCode(vo.getDictData(), code);
    // if (tem != null) {
    // return tem;
    // }
    // }
    // }
    // return null;
    // }
    
    /**
     * 〈一句话功能简述〉将新增对象放入缓存 〈功能详细描述〉通过vo添加新增的分类对象；通过list添加新增的明细对象
     * 
     * @param vo 分类对象
     * @param orderList 明细对象集合
     * @throws Exception InvocationTargetException 反射异常 IllegalAccessException
     *             非法入口异常
     * @see [类、类#方法、类#成员]
     */
    // @SuppressWarnings("unchecked")
    // private void putCache(DictDataVo vo, List<DictDataVo> orderList)
    // throws Exception {
    // Map<String, List<DictDataVo>> dict =
    // (Map<String, List<DictDataVo>>)
    // SerializeUtils.deserialize(redisManager.get(SystemContants.DICT.getBytes("UTF8")));
    // if (null != vo) {
    // String code = vo.getCode();
    // if (!dict.containsKey(code)) {
    // dict.put(vo.getCode(), new ArrayList<DictDataVo>());
    // }
    // else {
    // String pcode = vo.getPcode();
    // List<DictDataVo> list = dict.get(pcode);
    // list.add(vo);
    // }
    // }
    //
    // if (!CollectionUtils.isEmpty(orderList)) {
    // // 获取父编号
    // String pcode = orderList.get(0).getPcode();
    // // TODO 存在风险:如果改变code的定义，可能发生代码错误
    // String key =
    // pcode.substring(NumberContant.ZERO, NumberContant.THREE);
    // List<DictDataVo> list = dict.get(key);
    // int size = list.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo dictVo = list.get(i);
    // if (dictVo.getCode().equals(pcode)) {
    // // 与传入list数据进行对比添加
    // compareToAdd(dictVo, orderList, false);
    // }
    // }
    // }
    //
    // redisManager.set(SystemContants.DICT.getBytes("UTF8"),
    // SerializeUtils.serialize(dict));
    // }
    
    /**
     * 〈一句话功能简述〉更新缓存 〈功能详细描述〉根据类型或者值对象进行缓存修改
     * 
     * @param vo 类型对象
     * @param orderList 值对象集合
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    // @SuppressWarnings("unchecked")
    // private void updateCache(DictDataVo vo, List<DictDataVo> orderList)
    // throws Exception {
    // Map<String, List<DictDataVo>> dict =
    // (Map<String, List<DictDataVo>>)
    // SerializeUtils.deserialize(redisManager.get(SystemContants.DICT.getBytes("UTF8")));
    // if (null != vo) {
    // String pcode = vo.getPcode();
    // if (!pcode.equals("0")) {
    // List<DictDataVo> list = dict.get(pcode);
    // int size = list.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo temp = list.get(i);
    // if (temp.getCode().equals(vo.getCode())) {
    // // 将vo复制至temp
    // vo.setDictData(temp.getDictData());
    // C503BeanUtils.copyProperties(temp, vo);
    // }
    // }
    // }
    // }
    // if (!CollectionUtils.isEmpty(orderList)) {
    // // 获取父编号
    // String pcode = orderList.get(0).getPcode();
    // // TODO 存在风险:如果改变code的定义，可能发生代码错误
    // String key =
    // pcode.substring(NumberContant.ZERO, NumberContant.THREE);
    // List<DictDataVo> list = dict.get(key);
    // int size = list.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo dictVo = list.get(i);
    // if (dictVo.getCode().equals(pcode)) {
    // // 与传入list数据进行对比添加
    // compareToAdd(dictVo, orderList, true);
    // }
    // }
    // }
    // redisManager.set(SystemContants.DICT.getBytes("UTF8"),
    // SerializeUtils.serialize(dict));
    // }
    
    /**
     * 〈一句话功能简述〉删除缓存 〈功能详细描述〉根据分类以及明细进行缓存删除
     * 
     * @param ids 字典对象ID
     * @param isType 是否类型对象
     * @throws Exception 系统异常
     * @see [类、类#方法、类#成员]
     */
    // @SuppressWarnings("unchecked")
    // private void deleteCache(String[] ids, boolean isType)
    // throws Exception {
    // Map<String, Object> params = new HashMap<String, Object>();
    // params.put("remove", SystemContants.ON);
    // params.put("ids", ids);
    // List<DictDataVo> datas = new ArrayList<DictDataVo>();
    // if (isType) {
    // datas = this.dictionaryTypeDao.findByCond(params);
    // }
    // else {
    // datas =
    // (List<DictDataVo>) this.dictionaryValueDao.findByCond(params);
    // }
    // Map<String, List<DictDataVo>> dict =
    // (Map<String, List<DictDataVo>>)
    // SerializeUtils.deserialize(redisManager.get(SystemContants.DICT.getBytes("UTF8")));
    // int size = datas.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo vo = datas.get(i);
    // String pcode = vo.getPcode();
    // if (isType) {
    // if (pcode.equals("0")) {
    // dict.remove(vo.getCode());
    // }
    // else {
    // List<DictDataVo> list = dict.get(pcode);
    // // 对比删除
    // compareDel(list, vo);
    // }
    // }
    // else {
    // String key =
    // pcode.substring(NumberContant.ZERO, NumberContant.THREE);
    // // 删除分类下的明细
    // List<DictDataVo> list = dict.get(key);
    // deleteValues(list, datas);
    // }
    // }
    // redisManager.set(SystemContants.DICT.getBytes("UTF8"),
    // SerializeUtils.serialize(dict));
    // }
    /**
     * 〈一句话功能简述〉根据缓存中的集合对象对比当前要进行修改的集合，进行缓存清理 〈功能详细描述〉
     * 
     * @param list 缓存中的字典对象
     * @param datas 要清除的字典对象
     * @see [类、类#方法、类#成员]
     */
    // private void deleteValues(List<DictDataVo> list, List<DictDataVo> datas)
    // {
    // int size = list.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo vo = list.get(i);
    // List<DictDataVo> itemList = vo.getDictData();
    // int dataSize = datas.size();
    // for (int j = 0; j < dataSize; j++) {
    // DictDataVo data = datas.get(j);
    // compareDel(itemList, data);
    // }
    // }
    // }
    /**
     * 〈一句话功能简述〉对比删除 〈功能详细描述〉
     * 
     * @param list 数据字典对象集合
     * @param vo 数据字典对象
     * @see [类、类#方法、类#成员]
     */
    // private void compareDel(List<DictDataVo> list, DictDataVo vo) {
    // List<DictDataVo> tempList = new ArrayList<DictDataVo>();
    // tempList.addAll(list);
    // int size = tempList.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo temp = tempList.get(i);
    // if (temp.getCode().equals(vo.getCode())) {
    // list.remove(temp);
    // }
    // }
    // }
    /**
     * 〈一句话功能简述〉对比添加数据字典对象 〈功能详细描述〉如果在缓存的list中没有出现过的数据字典对象直接加入typeList；
     * 否则将编号相等的数据字典对象的排序号用排序list中对应的排序号替换。
     * 
     * @param dictVo 某个分类对象
     * @param orderList 经过调整排序的明细对象
     * @param isUpdate 是否更新操作标识（true:是；false:否）
     * @return List<DictDataVo> 数据对象集合
     * @throws InvocationTargetException 反射异常
     * @throws IllegalAccessException 非法入口异常
     * @see [类、类#方法、类#成员]
     */
    // private List<DictDataVo> compareToAdd(DictDataVo dictVo,
    // List<DictDataVo> orderList, boolean isUpdate)
    // throws IllegalAccessException, InvocationTargetException {
    // List<DictDataVo> typeList = dictVo.getDictData();
    // // 存储新加入的DictDataVo
    // List<DictDataVo> tempList = new ArrayList<DictDataVo>();
    // tempList.addAll(orderList);
    // int size = typeList.size();
    // int orderSize = orderList.size();
    // for (int i = 0; i < size; i++) {
    // DictDataVo vo = typeList.get(i);
    // for (int j = 0; j < orderSize; j++) {
    // DictDataVo temp = orderList.get(j);
    // if (vo.getCode().equals(temp.getCode())) {
    // if (vo.getOrders().compareTo(temp.getOrders()) != 0) {
    // if (isUpdate) {
    // // 将vo复制至temp
    // C503BeanUtils.copyProperties(vo, temp);
    // }
    // else {
    // vo.setOrders(temp.getOrders());
    // tempList.remove(temp);
    // }
    // }
    // }
    // }
    // }
    // if (!isUpdate) {
    // typeList.addAll(tempList);
    // }
    // return typeList;
    // }
    
    /**
     * 〈一句话功能简述〉递归找子节点 〈功能详细描述〉通过父节点的list找到子节点的集合
     * 
     * @param all 所有数据字典元素
     * @param parentNode 父节点集合
     * @see [类、类#方法、类#成员]
     */
    private void findChildNode(List<DictDataVo> all, List<DictDataVo> parentNode) {
        for (DictDataVo temdictData : parentNode) {
            List<DictDataVo> childNode = new ArrayList<DictDataVo>();
            for (DictDataVo vo : all) {
                if (vo.getPcode().equals(temdictData.getCode())) {
                    childNode.add(vo);
                    findChildNode(all, childNode);
                }
            }
            temdictData.setDictData(childNode);
        }
        
    }
    
    /**
     * {@inheritDoc}
     * 
     */
    protected LoggingManager logger() {
        return LOGGER;
    }
}
