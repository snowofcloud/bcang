package com.hthj.data.dao;

import com.hthj.data.domain.DockWorkInformation;
import com.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

public class RequestDao {
    public void saveData(DockWorkInformation dwi) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());

        String sql="insert into v_wh_whzysbd (APPLIID,LINKMANNM,TELEPHONENUMNB,CONSIGNER) values (?,?,?,?)";
//        String sql="insert into v_wh_whzysbd ('APPLIID','LINKMANNM','TELEPHONENUMNB','CONSIGNER') values (?,?,?,?)";
        Object[] params ={dwi.getAPPLIID(),dwi.getLINKMANNM(),dwi.getTELEPHONENUMNB(),dwi.getCONSIGNER()};
        String linkmannm = dwi.getLINKMANNM();
        queryRunner.update(sql,params);
    }
}

















