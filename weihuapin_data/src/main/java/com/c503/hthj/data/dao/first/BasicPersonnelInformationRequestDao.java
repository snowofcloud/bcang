package com.c503.hthj.data.dao.first;

import com.c503.hthj.data.domain.BasicPersonnelInformation;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class BasicPersonnelInformationRequestDao {

    /**
     * 查询数据
     */
    public BasicPersonnelInformation queryData(String id) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_wh_ryjcxx  where ID = ?";
        BasicPersonnelInformation bpi = queryRunner.query(sql, new BeanHandler<BasicPersonnelInformation>
                (BasicPersonnelInformation.class), id);
        return bpi;
    }

    /**
     * 插入数据
     */
    public void saveData(BasicPersonnelInformation bpi) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        String sql = "insert into edi_v_wh_ryjcxx (ID,OU,MAN_NAME,MAN_NUM,MAN_PHONE_NO,MAN_MOBILE_NO," +
                "TYPE,STATUS,CREATOR,CREATE_TIME,OPERATOR,UPDATE_TIME,RESERVE1,RESERVE2,ORG_NAME,IS_DELETE," +
                "IS_READ,SJC) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {bpi.getID(), bpi.getOU(), bpi.getMAN_NAME(), bpi.getMAN_NUM(), bpi.getMAN_PHONE_NO(),
                bpi.getMAN_MOBILE_NO(), bpi.getTYPE(), bpi.getSTATUS(), bpi.getCREATOR(), bpi.getCREATE_TIME(),
                bpi.getOPERATOR(), bpi.getUPDATE_TIME(), bpi.getRESERVE1(), bpi.getRESERVE2(), bpi.getORG_NAME(),
                bpi.getIS_DELETE(), bpi.getIS_READ(), bpi.getSJC()};
        queryRunner.update(sql, params);
    }
}

















