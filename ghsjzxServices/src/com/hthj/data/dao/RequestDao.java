package com.hthj.data.dao;

import com.hthj.data.domain.DockWorkInformation;
import com.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class RequestDao {
    public void saveData(DockWorkInformation dwi) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());

        String sql="insert into v_wh_whzysbd (APPLIID,LINKMANNM,TELEPHONENUMNB,CONSIGNER,CONSIGNERTELNB,PLANSTARTDATEDT," +
                "PLANWORKTIME,CARRIERDEPARTNM,CARRIERSHIPNM,CARRIERVOYAGENM,SHIPCHECKLOADNB,ARRIVEDATEDT,SPECIALDEMAND," +
                "WORKDOCKID,WORKDOCKNAME,SAFETYWORK,ANNEX,APPLIDEPARTID,APPLIDEPARTNAME,WORKDOCKBERTHID,WORKDOCKBERTHNAME," +
                "WORKPORTNM,SHIPWORKTYPEID,AUDITSTATENM,AUDITMANID,AUDITDATEDT,AUDITNOTE,SENDMANID,SENDMANNAME,SENDDATEDT," +
                "ISIN,ISAPPEND,AUDITDEPARTID,AUDITDEPARTNAME,WORKMANNM,DEAPPLYNOTE,BACKAPPLYNOTE,ORG_NAME,OU,CREATE_TIME," +
                "USER_ID,USER_NAME,UPDATE_TIME,OPERATOR,OPERATORNAME,RESERVE1,RESERVE2,HSE_MANAGER,HSE_MOBILE,MARINE_AUDIT," +
                "IS_ADDITIVE,DISPATCHER,AUDITMANNAME,RECHECKNOTE,CNGOODSNAME,WORKPLACE,WORKPLACEID,MMSI,APPLIID_CERT_NO," +
                "CONSIGNER_ORG_NAME,EMERGENCY_SUPPLIES,EMERGENCY_RESPONSE,EMERGENCY_TEAM,MARINE_STATUS,DRAUGHT,SHIP_LENGTH," +
                "SHIP_CERT_NO,CERT_VALID_DATE,MARINE_APP_NO,MARINE_APP_TIME,MARINE_PAPER_FLAG,MESSAGE_FLAG,MESSAGE_REFNO," +
                "NEW_MESSAGE_REFNO,REMARK,XML_MODIFY_NUM,IN_PORT,IS_DELETE,IS_READ,SJC,TSZT_Q,TSZT_H,REPORT_TYPE) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//        String sql="insert into v_wh_whzysbd ('APPLIID','LINKMANNM','TELEPHONENUMNB','CONSIGNER') values (?,?,?,?)";
        Object[] params ={dwi.getAPPLIID(),dwi.getLINKMANNM(),dwi.getTELEPHONENUMNB(),dwi.getCONSIGNER(),dwi.getCONSIGNERTELNB(),
                dwi.getPLANSTARTDATEDT(),dwi.getPLANWORKTIME(),dwi.getCARRIERDEPARTNM(),dwi.getCARRIERSHIPNM(),dwi.getCARRIERVOYAGENM(),
                dwi.getSHIPCHECKLOADNB(),dwi.getARRIVEDATEDT(),dwi.getSPECIALDEMAND(),dwi.getWORKDOCKID(),dwi.getWORKDOCKNAME(),
                dwi.getSAFETYWORK(),dwi.getANNEX(),
                dwi.getAPPLIDEPARTID(),dwi.getAPPLIDEPARTNAME(),dwi.getWORKDOCKBERTHID(),dwi.getWORKDOCKBERTHNAME(),dwi.getWORKPORTNM(),
                dwi.getSHIPWORKTYPEID(),dwi.getAUDITSTATENM(),dwi.getAUDITMANID(),dwi.getAUDITDATEDT(),dwi.getAUDITNOTE(),dwi.getSENDMANID(),
                dwi.getSENDDATEDT(),dwi.getISIN(),dwi.getISAPPEND(),dwi.getAUDITDEPARTID(),dwi.getAUDITDEPARTNAME(),dwi.getWORKMANNM(),
                dwi.getDEAPPLYNOTE(),dwi.getBACKAPPLYNOTE(),
                dwi.getORG_NAME(),dwi.getOU(),dwi.getCREATE_TIME(),dwi.getUSER_ID(),dwi.getUSER_NAME(),
                dwi.getUPDATE_TIME(),dwi.getOPERATOR(),dwi.getOPERATORNAME(),dwi.getRESERVE1(),dwi.getRESERVE2(),
                dwi.getHSE_MANAGER(),dwi.getHSE_MOBILE(),dwi.getMARINE_AUDIT(),dwi.getIS_ADDITIVE(),dwi.getDISPATCHER(),
                dwi.getAUDITMANNAME(),dwi.getRECHECKNOTE(),dwi.getCNGOODSNAME(),dwi.getWORKPLACE(),dwi.getWORKPLACEID(),
                dwi.getMMSI(),dwi.getAPPLIID_CERT_NO(),dwi.getCONSIGNER_ORG_NAME(),dwi.getEMERGENCY_SUPPLIES(),dwi.getEMERGENCY_RESPONSE(),
                dwi.getEMERGENCY_TEAM(),dwi.getMARINE_STATUS(),dwi.getDRAUGHT(),dwi.getSHIP_LENGTH(),dwi.getSHIP_CERT_NO(),dwi.getCERT_VALID_DATE(),
                dwi.getMARINE_APP_NO(),dwi.getMARINE_APP_TIME(),dwi.getMARINE_PAPER_FLAG(),dwi.getMESSAGE_FLAG(),dwi.getMESSAGE_REFNO(),
                dwi.getNEW_MESSAGE_REFNO(),dwi.getREMARK(),dwi.getXML_MODIFY_NUM(),dwi.getIN_PORT(),dwi.getIS_DELETE(),dwi.getIS_READ(),
                dwi.getSJC(),dwi.getTSZT_Q(),dwi.getTSZT_H(),dwi.getREPORT_TYPE()};
        queryRunner.update(sql,params);
    }
}

















