package com.c503.hthj.data.dao.first;

import com.c503.hthj.data.domain.DangerousGoodsDeclarationMarineDeclarationRecord;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class DangerousGoodsDeclarationMarineDeclarationRecordRequestDao {

    /**查询数据*/
    public DangerousGoodsDeclarationMarineDeclarationRecord queryData(String record_id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_wh_whzysbd_hssbjl  where RECORD_ID = ?";
        DangerousGoodsDeclarationMarineDeclarationRecord dgdmdr = queryRunner.query(sql, new BeanHandler<DangerousGoodsDeclarationMarineDeclarationRecord>
                (DangerousGoodsDeclarationMarineDeclarationRecord.class), record_id);
        return dgdmdr;
    }

    /**插入数据*/
    public void saveData(DangerousGoodsDeclarationMarineDeclarationRecord dgdmdr) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());

        String sql="insert into edi_v_wh_whzysbd_hssbjl (RECORD_ID,APPLIID,MARINE_APP_NO,MARINE_AUDIT,CARRIERDEPARTNM,CARRIERSHIPNM," +
                "CARRIERVOYAGENM,SHIPCHECKLOADNB,ARRIVEDATEDT,DRAUGHT,SHIP_LENGTH,SHIP_CERT_NO,CERT_VALID_DATE," +
                "WORKPORTNM,MARINE_APP_TIME,MARINE_STATUS,CREATOR,CREATE_TIME,OPERATOR,UPDATE_TIME,RESERVE1,RESERVE2," +
                "FROM_FLAG,IS_DELETE,SJC) "+
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
// String sql="insert into v_wh_whzysbd ('APPLIID','LINKMANNM','TELEPHONENUMNB','CONSIGNER') values (?,?,?,?)";values
        Object[] params ={dgdmdr.getRECORD_ID(),dgdmdr.getAPPLIID(),dgdmdr.getMARINE_APP_NO(),dgdmdr.getMARINE_AUDIT(),
                dgdmdr.getCARRIERDEPARTNM(), dgdmdr.getCARRIERSHIPNM(),dgdmdr.getCARRIERVOYAGENM(),dgdmdr.getSHIPCHECKLOADNB(),
                dgdmdr.getARRIVEDATEDT(),dgdmdr.getDRAUGHT(),dgdmdr.getSHIP_LENGTH(),dgdmdr.getSHIP_CERT_NO(),
                dgdmdr.getCERT_VALID_DATE(),dgdmdr.getWORKPORTNM(),dgdmdr.getMARINE_APP_TIME(),
                dgdmdr.getMARINE_STATUS(),dgdmdr.getCREATOR(),dgdmdr.getCREATE_TIME(),dgdmdr.getOPERATOR(),
                dgdmdr.getUPDATE_TIME(),dgdmdr.getRESERVE1(),dgdmdr.getRESERVE2(),dgdmdr.getFROM_FLAG(),
                dgdmdr.getIS_DELETE(),dgdmdr.getSJC()};
        queryRunner.update(sql,params);
    }
}

















