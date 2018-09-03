package com.hthj.data.dao;

import com.hthj.data.domain.PortEnterpriseData;
import com.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class RequestDao {

    public void saveData(PortEnterpriseData ped) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());

        String sql="insert into v_wh_sbqyxx (MAPID,PBDEPARTID,PBDEPARTNAME,DCDEPARTID,DCDEPARTNAME,DCAPPLYTYPE," +
                "DCGOODSSCALE,CREATE_TIME,USER_ID,UPDATE_TIME,OPERATOR,RESERVE1,RESERVE2,DISTRICT,IS_DELETE,IS_READ," +
                "DCAPPLYTYPE_NAME,SJC) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
// String sql="insert into v_wh_whzysbd ('APPLIID','LINKMANNM','TELEPHONENUMNB','CONSIGNER') values (?,?,?,?)";values
        Object[] params ={ped.getMAPID(),ped.getPBDEPARTID(),ped.getPBDEPARTNAME(),ped.getDCDEPARTID(),ped.getDCDEPARTNAME(),
                ped.getDCAPPLYTYPE(),ped.getDCGOODSSCALE(),ped.getCREATE_TIME(),ped.getUSER_ID(),ped.getUPDATE_TIME(),
                ped.getOPERATOR(),ped.getRESERVE1(),ped.getRESERVE2(),ped.getDISTRICT(),ped.getIS_DELETE(),
                ped.getIS_READ(),ped.getDCAPPLYTYPE_NAME(), ped.getSJC()};
        queryRunner.update(sql,params);
    }
}

















