package com.c503.hthj.data.dao.first;

import com.c503.hthj.data.domain.PortEnterpriseData;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class PortEnterpriseDataRequestDao {

    /**
     * 查询数据
     */
    public PortEnterpriseData queryData(String mapid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_wh_sbqyxx  where MAPID = ?";
        PortEnterpriseData ped = queryRunner.query(sql, new BeanHandler<PortEnterpriseData>
                (PortEnterpriseData.class), mapid);
        return ped;
    }

    /**
     * 插入数据
     */
    public void saveData(PortEnterpriseData ped) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        String sql = "insert into edi_v_wh_sbqyxx (MAPID,PBDEPARTID,PBDEPARTNAME,DCDEPARTID,DCDEPARTNAME,DCAPPLYTYPE," +
                "DCGOODSSCALE,CREATE_TIME,USER_ID,UPDATE_TIME,OPERATOR,RESERVE1,RESERVE2,DISTRICT,IS_DELETE,IS_READ," +
                "DCAPPLYTYPE_NAME,SJC) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {ped.getMAPID(), ped.getPBDEPARTID(), ped.getPBDEPARTNAME(), ped.getDCDEPARTID(), ped.getDCDEPARTNAME(),
                ped.getDCAPPLYTYPE(), ped.getDCGOODSSCALE(), ped.getCREATE_TIME(), ped.getUSER_ID(), ped.getUPDATE_TIME(),
                ped.getOPERATOR(), ped.getRESERVE1(), ped.getRESERVE2(), ped.getDISTRICT(), ped.getIS_DELETE(),
                ped.getIS_READ(), ped.getDCAPPLYTYPE_NAME(), ped.getSJC()};
        queryRunner.update(sql, params);
    }
}

















