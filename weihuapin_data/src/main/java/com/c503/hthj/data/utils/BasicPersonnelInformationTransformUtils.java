package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.BasicPersonnelInformation;
import net.sf.json.JSONObject;

public class BasicPersonnelInformationTransformUtils {

    private static BasicPersonnelInformation bpi = new BasicPersonnelInformation();

    /**
     * 查询数据
     */
    public static String queryData(JSONObject jsonObject) {
        Object id1 = jsonObject.get("ID");
        String id = (String) id1;
        return id;
    }

    /**
     * 封装数据
     */
    public static BasicPersonnelInformation getData(JSONObject jsonObject) {
        /**获取每个属性的value*/
        Object id = jsonObject.get("ID");
        Object ou = jsonObject.get("OU");
        Object man_name = jsonObject.get("MAN_NAME");
        Object man_num = jsonObject.get("MAN_NUM");
        Object man_phone_no = jsonObject.get("MAN_PHONE_NO");
        Object man_mobile_no = jsonObject.get("MAN_MOBILE_NO");
        Object type = jsonObject.get("TYPE");

        Object status = jsonObject.get("STATUS");
        Object creator = jsonObject.get("CREATOR");
        Object create_time = jsonObject.get("CREATE_TIME");
        Object operator = jsonObject.get("OPERATOR");
        Object update_time = jsonObject.get("UPDATE_TIME");
        Object reserve1 = jsonObject.get("RESERVE1");

        Object reserve2 = jsonObject.get("RESERVE2");
        Object org_name = jsonObject.get("ORG_NAME");
        Object is_delete = jsonObject.get("IS_DELETE");
        Object is_read = jsonObject.get("IS_READ");
        Object sjc = jsonObject.get("SJC");

        /**港口企业数据进行封装*/
        //BasicPersonnelInformation bpi = new BasicPersonnelInformation();

        bpi.setID((String) id);
        bpi.setOU((String) ou);
        bpi.setMAN_NAME((String) man_name);
        bpi.setMAN_NUM((String) man_num);
        bpi.setMAN_PHONE_NO((String) man_phone_no);
        bpi.setMAN_MOBILE_NO((String) man_mobile_no);
        bpi.setTYPE((String) type);

        bpi.setSTATUS((String) status);
        bpi.setCREATOR((String) creator);
        bpi.setCREATE_TIME((String) create_time);
        bpi.setOPERATOR((String) operator);
        bpi.setUPDATE_TIME((String) update_time);
        bpi.setRESERVE1((String) reserve1);
        bpi.setRESERVE2((String) reserve2);
        bpi.setORG_NAME((String) org_name);
        bpi.setIS_DELETE((String) is_delete);
        bpi.setIS_READ((String) is_read);
        bpi.setSJC((String) sjc);
        /**返回ped*/
        return bpi;
    }
}
