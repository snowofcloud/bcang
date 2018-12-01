package com.c503.hthj.data.dao.thrid;

import com.c503.hthj.data.domain.AllShipInfo;
import com.c503.hthj.data.domain.AllWork;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @auther xuxq
 * @date 2018/11/30 9:37
 */
public class AllWorkRequestDao {

    public AllWork queryData(String qyid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_wfzyzb  where QYID = ?";
        AllWork bis = queryRunner.query(sql, new BeanHandler<AllWork>(AllWork.class), qyid);
        return bis;
    }

    public void saveData(AllWork bis) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        String sql = "insert into edi_v_wfzyzb (SSFJ,YJZYJSSJ,WHPM_ALL,SJZZJGDM,QYID,MODIFY_AT, CBMC,IMO,TYSHXYDM,ZYL,ROW_ID,SSSJ,SSFJ_ID," +
                "ZYDD_ID,ZYDD,QYMC,GB_ID,SJZYKSSJ,ZT,ID, ZYLB_ID, YJZYKSSJ,SJZYJSSJ,SSSJ_ID) values (?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {bis.getSSFJ(), bis.getYJZYJSSJ(), bis.getWHPM_ALL(), bis.getSJZZJGDM(), bis.getQYID(), bis.getMODIFY_AT(),
                bis.getCBMC(), bis.getIMO(), bis.getTYSHXYDM(), bis.getZYL(), bis.getROW_ID(), bis.getSSSJ(), bis.getSSFJ_ID(),

                bis.getZYDD_ID(), bis.getZYDD(), bis.getQYMC(), bis.getGB_ID(), bis.getSJZYKSSJ(), bis.getZT(), bis.getID(),bis.getZYLB_ID(),
                bis.getYJZYKSSJ(), bis.getSJZYJSSJ(), bis.getSSSJ_ID()};
        queryRunner.update(sql, params);
    }

}
