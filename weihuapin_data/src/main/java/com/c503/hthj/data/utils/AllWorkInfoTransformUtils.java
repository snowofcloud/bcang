package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.AllWorkInfo;
import net.sf.json.JSONObject;

/**
 * @auther xuxq
 * @date 2018/12/1 0:21
 */
public class AllWorkInfoTransformUtils {

    private static AllWorkInfo allWorkInfo  = new AllWorkInfo();

    /**
     * 查询主键
     */
    public static String queryData(JSONObject jsonObject) {
        Object fid1 = jsonObject.get("FID");
        String fid = (String) fid1;
        return fid;
    }

    public static AllWorkInfo getData(JSONObject jsonObject){
        Object fid = jsonObject.get("FID");allWorkInfo.setFID((String) fid);
        Object zycs = jsonObject.get("ZYCS");allWorkInfo.setZYCS((String) zycs);
        Object bg_id = jsonObject.get("BG_ID");allWorkInfo.setBG_ID((String) bg_id);
        Object whpm = jsonObject.get("WHPM"); allWorkInfo.setWHPM((String) whpm);
        Object modify_at = jsonObject.get("MODIFY_AT"); allWorkInfo.setMODIFY_AT((String) modify_at);

        Object id = jsonObject.get("ID"); allWorkInfo.setID((String) id);
        Object xx_id = jsonObject.get("XX_ID"); allWorkInfo.setXX_ID((String) xx_id);
        Object zylb_id = jsonObject.get("ZYLB_ID"); allWorkInfo.setZYLB_ID((String) zylb_id);
        Object zycslb_id = jsonObject.get("ZYCSLB_ID"); allWorkInfo.setZYCSLB_ID((String) zycslb_id);
        Object zycs_id = jsonObject.get("ZYCS_ID"); allWorkInfo.setZYCS_ID((String) zycs_id);

        Object zyl = jsonObject.get("ZYL"); allWorkInfo.setZYL((Integer) zyl);
        Object row_id = jsonObject.get("ROW_ID"); allWorkInfo.setROW_ID((Integer) row_id);
        return allWorkInfo;
    }




}
