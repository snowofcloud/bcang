package com.hthj.data.utils;

import com.hthj.data.domain.PortEnterpriseData;
import net.sf.json.JSONObject;

public class DataTransformUtils {

    public static PortEnterpriseData getData(JSONObject jsonObject) {
        /**获取每个属性的value*/
        Object mapid = jsonObject.get("MAPID");
        Object pbdepartid = jsonObject.get("PBDEPARTID");
        Object pbdepartname = jsonObject.get("PBDEPARTNAME");
        Object dcdepartid = jsonObject.get("DCDEPARTID");
        Object dcdepartname = jsonObject.get("DCDEPARTNAME");
        Object dcapplytype = jsonObject.get("DCAPPLYTYPE");
        Object dcgoodsscale = jsonObject.get("DCGOODSSCALE");


        Object create_time = jsonObject.get("CREATE_TIME");
        Object user_id = jsonObject.get("USER_ID");
        Object update_time = jsonObject.get("UPDATE_TIME");
        Object operator = jsonObject.get("OPERATOR");
        Object reserve1 = jsonObject.get("RESERVE1");
        Object reserve2 = jsonObject.get("RESERVE2");

        Object district = jsonObject.get("DISTRICT");
        Object is_delete = jsonObject.get("IS_DELETE");
        Object is_read = jsonObject.get("IS_READ");
        Object dcapplytype_name = jsonObject.get("DCAPPLYTYPE_NAME");
        Object sjc = jsonObject.get("SJC");

        /**港口企业数据进行封装*/
        PortEnterpriseData ped = new PortEnterpriseData();

        ped.setMAPID((String)mapid);
        ped.setPBDEPARTID((String)pbdepartid);
        ped.setPBDEPARTNAME((String)pbdepartname);
        ped.setDCDEPARTID((String)dcdepartid);
        ped.setDCDEPARTNAME((String)dcdepartname);
        ped.setDCAPPLYTYPE((String)dcapplytype);
        ped.setDCGOODSSCALE((String)dcgoodsscale);

        ped.setCREATE_TIME((String)create_time);
        ped.setUSER_ID((String)user_id);
        ped.setUPDATE_TIME((String)update_time);
        ped.setOPERATOR((String)operator);
        ped.setRESERVE1((String)reserve1);
        ped.setRESERVE2((String)reserve2);
        ped.setDISTRICT((String)district);
        ped.setIS_DELETE((String)is_delete);
        ped.setIS_READ((String)is_read);
        ped.setDCAPPLYTYPE_NAME((String)dcapplytype_name);
        ped.setSJC((String)sjc);
        /**返回ped*/
        return ped;
    }
}
