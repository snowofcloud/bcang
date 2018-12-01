package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.AllWork;
import net.sf.json.JSONObject;

/**
 * @auther xuxq
 * @date 2018/11/30 9:04
 */
public class AllWorkTransformUtils {

    private static AllWork allWork  = new AllWork();

    /**
     * 查询主键
     */
    public static String queryData(JSONObject jsonObject) {
        Object qyid1 = jsonObject.get("QYID");
        String qyid = (String) qyid1;
        return qyid;
    }


    public static AllWork getData(JSONObject jsonObject){
        Object ssfj = jsonObject.get("SSFJ"); allWork.setSSFJ((String) ssfj);
        Object yjzyjssj = jsonObject.get("YJZYJSSJ");allWork.setYJZYJSSJ((String) yjzyjssj);
        Object whpm_all = jsonObject.get("WHPM_ALL");allWork.setWHPM_ALL((String) whpm_all);
        Object sjzzjgdm = jsonObject.get("SJZZJGDM");allWork.setSJZZJGDM((String) sjzzjgdm);
        Object qyid = jsonObject.get("QYID");allWork.setQYID((String) qyid);
        Object modify_at = jsonObject.get("MODIFY_AT");allWork.setMODIFY_AT((String) modify_at);
        Object cbmc = jsonObject.get("CBMC");allWork.setCBMC((String) cbmc);
        Object imo = jsonObject.get("IMO");allWork.setIMO((String) imo);
        Object tyshxydm = jsonObject.get("TYSHXYDM");allWork.setTYSHXYDM((String) tyshxydm);
        Object zyl = jsonObject.get("ZYL");allWork.setZYL((Integer) zyl);
        Object row_id = jsonObject.get("ROW_ID");allWork.setROW_ID((Integer) row_id);
        Object sssj = jsonObject.get("SSSJ");allWork.setSSSJ((String) sssj);
        Object ssfj_id = jsonObject.get("SSFJ_ID");allWork.setSSFJ_ID((String) ssfj_id);
        Object zydd_id = jsonObject.get("ZYDD_ID");allWork.setZYDD_ID((String) zydd_id);
        Object zydd = jsonObject.get("ZYDD");allWork.setZYDD((String) zydd);
        Object qymc = jsonObject.get("QYMC");allWork.setQYMC((String) qymc);
        Object gb_id = jsonObject.get("GB_ID");allWork.setGB_ID((String) gb_id);
        Object sjzykssj = jsonObject.get("SJZYKSSJ");allWork.setSJZYKSSJ((String) sjzykssj);
        Object zt = jsonObject.get("ZT");allWork.setZT((String) zt);
        Object id = jsonObject.get("ID");allWork.setID((String) id);
        Object zylb_id = jsonObject.get("ZYLB_ID");allWork.setZYLB_ID((String) zylb_id);
        Object yjzykssj = jsonObject.get("YJZYKSSJ");allWork.setYJZYKSSJ((String) yjzykssj);
        Object sjzyjssj = jsonObject.get("SJZYJSSJ");allWork.setSJZYJSSJ((String) sjzyjssj);
        Object sssj_id = jsonObject.get("SSSJ_ID");allWork.setSSSJ_ID((String) sssj_id);
        return allWork;
    }


}
