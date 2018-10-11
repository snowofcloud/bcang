package com.c503.hthj.data.dao.first;

import com.c503.hthj.data.domain.DangerousGoodsDeclarationDangerousGood;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class DangerousGoodsDeclarationDangerousGoodRequestDao {

    /**查询数据*/
    public DangerousGoodsDeclarationDangerousGood queryData(String goods_id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_wh_whzysbd_wxhw  where GOODS_ID = ?";
        DangerousGoodsDeclarationDangerousGood dgddg = queryRunner.query(sql, new BeanHandler<DangerousGoodsDeclarationDangerousGood>
                (DangerousGoodsDeclarationDangerousGood.class), goods_id);
        return dgddg;
    }

    /**插入数据*/
    public void saveData(DangerousGoodsDeclarationDangerousGood dgddg) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());

        String sql="insert into edi_v_wh_whzysbd_wxhw (GOODS_ID,DANGERGOODSCNNM,UNCODE,TALLYKIND,AMOUNTNB,CASINGNM," +
                "APPLIID,DANGERGOODSENNM,DANGERGOODSID,DANGERGOODSMAINTYPE,IS_DELETE,IS_READ,SJC,CONSIGNER_NAME,CONSIGNER_ID) "+
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
// String sql="insert into v_wh_whzysbd ('APPLIID','LINKMANNM','TELEPHONENUMNB','CONSIGNER') values (?,?,?,?)";values
        Object[] params ={dgddg.getGOODS_ID(),dgddg.getDANGERGOODSCNNM(),dgddg.getUNCODE(),dgddg.getTALLYKIND(),
                dgddg.getAMOUNTNB(), dgddg.getCASINGNM(),dgddg.getAPPLIID(),dgddg.getDANGERGOODSENNM(),
                dgddg.getDANGERGOODSID(),dgddg.getDANGERGOODSMAINTYPE(),
                dgddg.getIS_DELETE(),dgddg.getIS_READ(),
                dgddg.getSJC(),dgddg.getCONSIGNER_NAME(),dgddg.getCONSIGNER_ID()};
        queryRunner.update(sql,params);
    }
}

















