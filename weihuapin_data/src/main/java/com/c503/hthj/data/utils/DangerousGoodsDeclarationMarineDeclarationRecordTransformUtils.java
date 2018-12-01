package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.DangerousGoodsDeclarationMarineDeclarationRecord;
import net.sf.json.JSONObject;

public class DangerousGoodsDeclarationMarineDeclarationRecordTransformUtils {

    private static DangerousGoodsDeclarationMarineDeclarationRecord dgdmdr =
            new DangerousGoodsDeclarationMarineDeclarationRecord();

    /**
     * 查询数据
     */
    public static String queryData(JSONObject jsonObject) {
        Object record_id1 = jsonObject.get("RECORD_ID");
        String record_id = (String) record_id1;
        return record_id;
    }

    /**
     * 封装数据
     */
    public static DangerousGoodsDeclarationMarineDeclarationRecord getData(JSONObject jsonObject) {
        /**获取每个属性的value*/

        //System.out.println(jsonObject.toString());
        Object record_id = jsonObject.get("RECORD_ID");
        Object appliid = jsonObject.get("APPLIID");
        Object marine_app_no = jsonObject.get("MARINE_APP_NO");
        Object marine_audit = jsonObject.get("MARINE_AUDIT");
        Object carrierdepartnm = jsonObject.get("CARRIERDEPARTNM");
        //carrierdepartnm.
        Object carriershipnm = jsonObject.get("CARRIERSHIPNM");
        Object carriervoyagenm = jsonObject.get("CARRIERVOYAGENM");
        Object shipcheckloadnb = jsonObject.get("SHIPCHECKLOADNB");
        Object arrivedatedt = jsonObject.get("ARRIVEDATEDT");
        Object draught = jsonObject.get("DRAUGHT");
        Object ship_length = jsonObject.get("SHIP_LENGTH");
        Object ship_cert_no = jsonObject.get("SHIP_CERT_NO");
        Object cert_valid_date = jsonObject.get("CERT_VALID_DATE");
        Object workportnm = jsonObject.get("WORKPORTNM");
        Object marine_app_time = jsonObject.get("MARINE_APP_TIME");
        Object marine_status = jsonObject.get("MARINE_STATUS");
        Object creator = jsonObject.get("CREATOR");
        Object create_time = jsonObject.get("CREATE_TIME");
        Object operator = jsonObject.get("OPERATOR");
        Object update_time = jsonObject.get("UPDATE_TIME");
        Object reserve1 = jsonObject.get("RESERVE1");
        Object reserve2 = jsonObject.get("RESERVE2");
        Object from_flag = jsonObject.get("FROM_FLAG");
        Object is_delete = jsonObject.get("IS_DELETE");
        Object sjc = jsonObject.get("SJC");

        /*2.4	危货作业申报单_海事申报记录*/

        //DangerousGoodsDeclarationMarineDeclarationRecord dgdmdr =
        // new DangerousGoodsDeclarationMarineDeclarationRecord();
        dgdmdr.setRECORD_ID((String) record_id);
        dgdmdr.setAPPLIID((String) appliid);
        dgdmdr.setMARINE_APP_NO((String) marine_app_no);
        dgdmdr.setMARINE_AUDIT((String) marine_audit);
        dgdmdr.setCARRIERDEPARTNM(carrierdepartnm.toString());
        dgdmdr.setCARRIERSHIPNM((String) carriershipnm);
        dgdmdr.setCARRIERVOYAGENM(carriervoyagenm.toString());
        dgdmdr.setSHIPCHECKLOADNB(shipcheckloadnb.toString());
        dgdmdr.setARRIVEDATEDT((String) arrivedatedt);
        dgdmdr.setDRAUGHT((String) draught);
        dgdmdr.setSHIP_LENGTH((String) ship_length);
        dgdmdr.setSHIP_CERT_NO((String) ship_cert_no);
        dgdmdr.setCERT_VALID_DATE((String) cert_valid_date);
        dgdmdr.setWORKPORTNM((String) workportnm);
        dgdmdr.setMARINE_APP_TIME((String) marine_app_time);
        dgdmdr.setMARINE_STATUS((String) marine_status);
        dgdmdr.setCREATOR((String) creator);
        dgdmdr.setCREATE_TIME((String) create_time);
        dgdmdr.setOPERATOR((String) operator);
        dgdmdr.setUPDATE_TIME((String) update_time);
        dgdmdr.setRESERVE1((String) reserve1);
        dgdmdr.setRESERVE2((String) reserve2);
        dgdmdr.setFROM_FLAG((String) from_flag);
        dgdmdr.setIS_DELETE((String) is_delete);
        dgdmdr.setSJC((String) sjc);


        /**返回dwi*/

        return dgdmdr;
    }

}
