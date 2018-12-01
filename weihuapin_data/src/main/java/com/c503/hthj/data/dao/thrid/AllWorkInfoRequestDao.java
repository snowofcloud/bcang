package com.c503.hthj.data.dao.thrid;

import com.c503.hthj.data.domain.AllWorkInfo;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @auther xuxq
 * @date 2018/12/1 0:32
 */
public class AllWorkInfoRequestDao {

    public AllWorkInfo queryData(String fid) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_wfzyfb  where FID = ?";
        AllWorkInfo bis = queryRunner.query(sql, new BeanHandler<AllWorkInfo>(AllWorkInfo.class), fid);
        return bis;
    }

    public void saveData(AllWorkInfo bis) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        String sql = "insert into edi_v_wfzyfb (FID,ZYCS,BG_ID,WHPM,MODIFY_AT, ID,XX_ID,ZYLB_ID,ZYCSLB_ID,ZYCS_ID,ZYL,ROW_ID)" +
                " values (?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {bis.getFID(), bis.getZYCS(), bis.getBG_ID(), bis.getWHPM(), bis.getMODIFY_AT(), bis.getID(),
                bis.getXX_ID(), bis.getZYLB_ID(), bis.getZYCSLB_ID(), bis.getZYCS_ID(),bis.getZYL(), bis.getROW_ID()};
        queryRunner.update(sql, params);
    }

}
