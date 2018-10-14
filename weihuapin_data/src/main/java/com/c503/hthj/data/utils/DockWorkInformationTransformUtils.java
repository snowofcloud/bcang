package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.DockWorkInformation;
import net.sf.json.JSONObject;

public class DockWorkInformationTransformUtils {

    private static DockWorkInformation dwi = new DockWorkInformation();

    /**查询数据*/
    public static String queryData(JSONObject jsonObject){
        Object appliid1 = jsonObject.get("APPLIID");
        String appliid = (String)appliid1;
        return appliid;
    }

    /**封装数据*/
    public static DockWorkInformation getData(JSONObject jsonObject) {
        /**获取每个属性的value*/

        Object appliid = jsonObject.get("APPLIID");
        Object linkmannm = jsonObject.get("LINKMANNM");
        Object telephonenumnb = jsonObject.get("TELEPHONENUMNB");
        Object consigner = jsonObject.get("CONSIGNER");
        Object consignertelnb = jsonObject.get("CONSIGNERTELNB");
        Object planstartdatedt = jsonObject.get("PLANSTARTDATEDT");

        Object planworktime = jsonObject.get("PLANWORKTIME");
        Object carrierdepartnm = jsonObject.get("CARRIERDEPARTNM");
        Object carriershipnm = jsonObject.get("CARRIERSHIPNM");
        Object carriervoyagenm = jsonObject.get("CARRIERVOYAGENM");
        Object shipcheckloadnb = jsonObject.get("SHIPCHECKLOADNB");
        Object arrivedatedt = jsonObject.get("ARRIVEDATEDT");
        Object specialdemand = jsonObject.get("SPECIALDEMAND");
        Object workdockid = jsonObject.get("WORKDOCKID");
        Object workdockname = jsonObject.get("WORKDOCKNAME");
        Object safetywork = jsonObject.get("SAFETYWORK");
        Object annex = jsonObject.get("ANNEX");
        Object applidepartid = jsonObject.get("APPLIDEPARTID");
        Object applidepartname = jsonObject.get("APPLIDEPARTNAME");
        Object workdockberthid = jsonObject.get("WORKDOCKBERTHID");
        Object workdockberthname = jsonObject.get("WORKDOCKBERTHNAME");
        Object workportnm = jsonObject.get("WORKPORTNM");
        Object shipworktypeid = jsonObject.get("SHIPWORKTYPEID");
        Object auditstatenm = jsonObject.get("AUDITSTATENM");
        Object auditmanid = jsonObject.get("AUDITMANID");
        Object auditdatedt = jsonObject.get("AUDITDATEDT");
        Object auditnote = jsonObject.get("AUDITNOTE");
        Object sendmanid = jsonObject.get("SENDMANID");
        Object sendmanname = jsonObject.get("SENDMANNAME");
        Object senddatedt = jsonObject.get("SENDDATEDT");
        Object isin = jsonObject.get("ISIN");
        Object isappend = jsonObject.get("ISAPPEND");
        Object auditdepartid = jsonObject.get("AUDITDEPARTID");//以上33个
        Object auditdepartname = jsonObject.get("AUDITDEPARTNAME");
        Object workmannm=jsonObject.get("WORKMANNM");
        Object deapplynote = jsonObject.get("DEAPPLYNOTE");
        Object backapplynote = jsonObject.get("BACKAPPLYNOTE");
        Object org_name = jsonObject.get("ORG_NAME");
        Object ou = jsonObject.get("OU");
        Object create_time = jsonObject.get("CREATE_TIME");
        Object user_id = jsonObject.get("USER_ID");
        Object user_name = jsonObject.get("USER_NAME");
        Object update_time = jsonObject.get("UPDATE_TIME");
        Object operator = jsonObject.get("OPERATOR");
        Object operatorname = jsonObject.get("OPERATORNAME");
        Object reserve1 = jsonObject.get("RESERVE1");
        Object reserve2 = jsonObject.get("RESERVE2");
        Object hse_manager = jsonObject.get("HSE_MANAGER");
        Object hse_mobile = jsonObject.get("HSE_MOBILE");
        Object marine_audit = jsonObject.get("MARINE_AUDIT");
        Object is_additive = jsonObject.get("IS_ADDITIVE");
        Object dispatcher = jsonObject.get("DISPATCHER");
        Object auditmanname = jsonObject.get("AUDITMANNAME");
        Object rechecknote = jsonObject.get("RECHECKNOTE");
        Object cngoodsname = jsonObject.get("CNGOODSNAME");
        Object workplace = jsonObject.get("WORKPLACE");
        Object workplaceid = jsonObject.get("WORKPLACEID");
        Object mmsi = jsonObject.get("MMSI");
        Object appliid_cert_no = jsonObject.get("APPLIID_CERT_NO");
        Object consigner_org_name = jsonObject.get("CONSIGNER_ORG_NAME");
        Object emergency_supplies = jsonObject.get("EMERGENCY_SUPPLIES");
        Object emergency_response = jsonObject.get("EMERGENCY_RESPONSE");
        Object emergency_team = jsonObject.get("EMERGENCY_TEAM");
        Object marine_status = jsonObject.get("MARINE_STATUS");
        Object draught = jsonObject.get("DRAUGHT");

        Object ship_length = jsonObject.get("SHIP_LENGTH");
        Object ship_cert_no = jsonObject.get("SHIP_CERT_NO");
        Object cert_valid_date = jsonObject.get("CERT_VALID_DATE");
        Object marine_app_no = jsonObject.get("MARINE_APP_NO");
        Object marine_app_time = jsonObject.get("MARINE_APP_TIME");
        Object marine_paper_flag = jsonObject.get("MARINE_PAPER_FLAG");
        Object message_flag = jsonObject.get("MESSAGE_FLAG");
        Object message_refno = jsonObject.get("MESSAGE_REFNO");
        Object new_message_refno = jsonObject.get("NEW_MESSAGE_REFNO");
        Object remark = jsonObject.get("REMARK");
        Object xml_modify_num = jsonObject.get("XML_MODIFY_NUM");
        Object in_port = jsonObject.get("IN_PORT");
        Object is_delete = jsonObject.get("IS_DELETE");
        Object is_read = jsonObject.get("IS_READ");
        Object sjc = jsonObject.get("SJC");
        Object tszt_q = jsonObject.get("TSZT_Q");
        Object tszt_h = jsonObject.get("TSZT_H");
        Object report_type = jsonObject.get("REPORT_TYPE");

        /**码头作业信息进行封装*/
        //DockWorkInformation dwi = new DockWorkInformation();
        dwi.setAPPLIID((String) appliid);
        dwi.setLINKMANNM((String)linkmannm);
        dwi.setTELEPHONENUMNB((String)telephonenumnb);
        dwi.setCONSIGNER((String)consigner);
        dwi.setCONSIGNERTELNB((String)consignertelnb);
        dwi.setPLANSTARTDATEDT((String)planstartdatedt);
        dwi.setPLANWORKTIME((String)planworktime);
        dwi.setCARRIERDEPARTNM((String)carrierdepartnm);
        dwi.setCARRIERSHIPNM((String)carriershipnm);
        dwi.setCARRIERVOYAGENM((String)carriervoyagenm);
        dwi.setSHIPCHECKLOADNB((String)shipcheckloadnb);
        dwi.setARRIVEDATEDT((String)arrivedatedt);
        dwi.setSPECIALDEMAND((String)specialdemand);
        dwi.setWORKDOCKID((String)workdockid);
        dwi.setWORKDOCKNAME((String)workdockname);
        dwi.setSAFETYWORK((String)safetywork);
        dwi.setANNEX((String)annex);
        dwi.setAPPLIDEPARTID((String)applidepartid);
        dwi.setAPPLIDEPARTNAME((String)applidepartname);
        dwi.setWORKDOCKBERTHID((String)workdockberthid);
        dwi.setWORKDOCKBERTHNAME((String)workdockberthname);
        dwi.setWORKPORTNM((String)workportnm);
        dwi.setSHIPWORKTYPEID((String)shipworktypeid);
        dwi.setAUDITSTATENM((String)auditstatenm);
        dwi.setAUDITMANID((String)auditmanid);
        dwi.setAUDITDATEDT((String)auditdatedt);
        dwi.setAUDITNOTE((String)auditnote);
        dwi.setSENDMANID((String)sendmanid);
        dwi.setSENDDATEDT((String)senddatedt);
        dwi.setSENDMANNAME((String)sendmanname);
        dwi.setISIN((String)isin);
        dwi.setISAPPEND((String)isappend);
        dwi.setAUDITDEPARTID((String)auditdepartid);
        //以上33个
        dwi.setAUDITDEPARTNAME((String)auditdepartname);
        dwi.setWORKMANNM((String)workmannm);
        dwi.setDEAPPLYNOTE((String)deapplynote);
        dwi.setBACKAPPLYNOTE((String)backapplynote);
        dwi.setORG_NAME((String)org_name);
        dwi.setOU((String)ou);
        dwi.setCREATE_TIME((String)create_time);
        dwi.setUSER_ID((String)user_id);
        dwi.setUSER_NAME((String)user_name);
        dwi.setUPDATE_TIME((String)update_time);
        dwi.setOPERATOR((String)operator);
        dwi.setOPERATORNAME((String)operatorname);
        dwi.setRESERVE1((String)reserve1);
        dwi.setRESERVE2((String)reserve2);
        dwi.setHSE_MANAGER((String)hse_manager);
        dwi.setHSE_MOBILE((String)hse_mobile);
        dwi.setMARINE_AUDIT((String)marine_audit);
        dwi.setIS_ADDITIVE((String)is_additive);
        dwi.setDISPATCHER((String)dispatcher);
        dwi.setAUDITMANNAME((String)auditmanname);
        dwi.setRECHECKNOTE((String)rechecknote);
        dwi.setCNGOODSNAME((String)cngoodsname);
        dwi.setWORKPLACE((String)workplace);
        dwi.setWORKPLACEID((String)workplaceid);
        dwi.setMMSI((String)mmsi);
        dwi.setAPPLIID_CERT_NO((String)appliid_cert_no);
        dwi.setCONSIGNER_ORG_NAME((String)consigner_org_name);
        dwi.setEMERGENCY_SUPPLIES((String)emergency_supplies);
        dwi.setEMERGENCY_RESPONSE((String)emergency_response);
        dwi.setEMERGENCY_TEAM((String)emergency_team);
        dwi.setMARINE_STATUS((String)marine_status);
        dwi.setDRAUGHT((String)draught);
        dwi.setSHIP_LENGTH((String)ship_length);
        dwi.setSHIP_CERT_NO((String)ship_cert_no);
        dwi.setCERT_VALID_DATE((String)cert_valid_date);
        dwi.setMARINE_APP_NO((String)marine_app_no);
        dwi.setMARINE_APP_TIME((String)marine_app_time);
        dwi.setMARINE_PAPER_FLAG((String)marine_paper_flag);
        dwi.setMESSAGE_FLAG((String)message_flag);
        dwi.setMESSAGE_REFNO((String)message_refno);
        dwi.setNEW_MESSAGE_REFNO((String)new_message_refno);
        dwi.setREMARK((String)remark);
        dwi.setXML_MODIFY_NUM((String)xml_modify_num);
        dwi.setIN_PORT((String)in_port);
        dwi.setIS_DELETE((String)is_delete);
        dwi.setIS_READ((String)is_read);
        dwi.setSJC((String)sjc);
        dwi.setTSZT_Q((String)tszt_q);
        dwi.setTSZT_H((String)tszt_h);
        dwi.setREPORT_TYPE((String)report_type);

        /**返回dwi*/
        return dwi;
    }
}