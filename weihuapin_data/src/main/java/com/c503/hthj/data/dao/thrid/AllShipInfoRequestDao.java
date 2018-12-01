package com.c503.hthj.data.dao.thrid;

import com.c503.hthj.data.domain.AllShipInfo;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @auther xuxq
 * @date 2018/11/29 21:51
 */
public class AllShipInfoRequestDao {


    public AllShipInfo queryData(String cbdjh) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_cbjbxx_qg  where CBDJH = ?";
        AllShipInfo bis = queryRunner.query(sql, new BeanHandler<AllShipInfo>(AllShipInfo.class), cbdjh);
        return bis;
    }

    public void saveData(AllShipInfo bis) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        String sql = "insert into edi_v_cbjbxx_qg (HSJGDM,CBXS,JYRDZ,ZHXGSJ,JBCS,CBDJH, CBXK,KZPSL,LGYSZDGD,ZJZGL,CJGDM,ZDHS,ZJSM," +
                "YWCM,HDKFDJ,TJQZLDM,HDHXDM,CYRS,JZRQ,SYR, XJMZCS, ROW_ID,CQGDM,HCSL,CTCLDM,CYZWCM,CBZLDM,ZJZLDM,MZPSL,KZCS,JSDY," +
                "ZCDD,ZCBSL,ZCCMC,HHCBZDM,CKDEZJ,ZWCM,CKZZD,CBZJ,CBSBH,CBZC,JDW,CJSJ,JYR,YZCZRJ,SMHCBS," +
                "ZDW,CCDJH,CBJZ,GX,CXJC,HDHQDM,EDCW,TJQSL,SFZX,YZCS,MZCS,CJBMDM,EDXW) values (?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {bis.getHSJGDM(), bis.getCBXS(), bis.getJYRDZ(), bis.getZHXGSJ(), bis.getJBCS(), bis.getCBDJH(),
                bis.getCBXK(), bis.getKZPSL(), bis.getLGYSZDGD(), bis.getZJZGL(), bis.getCJGDM(), bis.getZDHS(), bis.getZJSM(),

                bis.getYWCM(), bis.getHDKFDJ(), bis.getTJQZLDM(), bis.getHDHXDM(), bis.getCYRS(), bis.getJZRQ(), bis.getSYR(),bis.getXJMZCS(),
                bis.getROW_ID(), bis.getCQGDM(), bis.getHCSL(), bis.getCTCLDM(),    bis.getCYZWCM(),
                bis.getCBZLDM(), bis.getZJZLDM(), bis.getMZPSL(), bis.getKZCS(), bis.getJSDY(), bis.getZCDD(),
                bis.getZCBSL(), bis.getZCCMC(), bis.getHHCBZDM(), bis.getCKDEZJ(), bis.getZWCM(), bis.getCKZZD(),
                bis.getCBZJ(), bis.getCBSBH(), bis.getCBZC(), bis.getJDW(), bis.getCJSJ(),
                bis.getJYR(), bis.getYZCZRJ(),    bis.getSMHCBS(), bis.getZDW(), bis.getCCDJH(), bis.getCBJZ(),
                bis.getGX(), bis.getCXJC(), bis.getHDHQDM(), bis.getEDCW(), bis.getTJQSL(), bis.getSFZX(),
                bis.getYZCS(), bis.getMZCS(), bis.getCJBMDM(), bis.getEDXW()};
        queryRunner.update(sql, params);
    }


}
