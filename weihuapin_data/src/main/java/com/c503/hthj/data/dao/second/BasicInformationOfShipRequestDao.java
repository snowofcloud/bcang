package com.c503.hthj.data.dao.second;

import com.c503.hthj.data.domain.BasicInformationOfShip;
import com.c503.hthj.data.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class BasicInformationOfShipRequestDao {


    /**
     * 查询数据
     */
    public BasicInformationOfShip queryData(String cbzj) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from edi_v_cb_cbjbxx_qg  where CBZJ = ?";
        BasicInformationOfShip bis = queryRunner.query(sql, new BeanHandler<BasicInformationOfShip>
                (BasicInformationOfShip.class), cbzj);
        return bis;
    }


    public void saveData(BasicInformationOfShip bis) throws SQLException {

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());

        String sql = "insert into edi_v_cb_cbjbxx_qg (CBZJ,CBDJH,CBSBH,CCDJH,CJDJH,CYZWCM,PBH,ZWCM,YWCM,CBICKH,MMSI,IMO,HH," +
                "CJGDM,CQGDM,HHCBZDM,CBZLDM,ZDW,JDW,CKZZD,ZJZGL,CBZC,CBXK,CBXS,HDHXDM,HDHQDM,HXQY,CTCLDM,CSYS,CBJZ," +
                "JZRQ,ZCCMC,ZCCYWMC,ZCDD,ZCDDYWMC,GJCMC,GJCYWMC,GJRQ,GJDD,GJDDYWMC,AFLGRQ,LGYSZDGD,ZJZLDM,ZJSM,ZJZZCMC," +
                "ZJXH,TJQZLDM,TJQSL,XJMZCS,MZCS,KZCS,MZPSL,KZPSL,HDKFDJ,GX,EDXW,EDCW,CKDEZJ,JSDY,ZDHS,CXJC,JBCL," +
                "JBCS,HCSL,SMHCBS,SCDWZ,ZCBSL,JCZDHCDDM,DSKX,FDJGL,GMDSSSBQK,CJJGBM,HSJGDM,BZ,SFZX,TPZJ,CJSJ,CJR," +
                "CJJGDM,CJBMDM,ZHXGSJ,ZHXGR,SYRZJ,SYR,SYRDZ,SYRLXDH,SYRFRDB,JYRZJ,JYR,JYRDZ,JYRFRDB,JYRLXDH,GLRZJ," +
                "GLR,GLRDZ,GLRFRDB,GLRLXDH,CBLXDM,STPZH,TZPZDW,ZJBH,CTCL,CYRS,YZCS,YZCZRJ,GXSJ) values (?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?," +
                "?,?,?,?,?,?,?,?,?   )";
        Object[] params = {bis.getCBZJ(), bis.getCBDJH(), bis.getCBSBH(), bis.getCCDJH(), bis.getCJDJH(), bis.getCYZWCM(),
                bis.getPBH(), bis.getZWCM(), bis.getYWCM(), bis.getCBICKH(), bis.getMMSI(), bis.getIMO(), bis.getHH(),
                bis.getCJGDM(), bis.getCQGDM(), bis.getHHCBZDM(), bis.getCBZLDM(), bis.getZDW(), bis.getJDW(), bis.getCKZZD(),
                bis.getZJZGL(), bis.getCBZC(), bis.getCBXK(), bis.getCBXS(), bis.getHDHXDM(), bis.getHDHQDM(), bis.getHXQY(),
                bis.getCTCLDM(), bis.getCSYS(), bis.getCBJZ(), bis.getJZRQ(), bis.getZCCMC(), bis.getZCCYWMC(),
                bis.getZCDD(), bis.getZCDDYWMC(), bis.getGJCMC(), bis.getGJCYWMC(), bis.getGJRQ(), bis.getGJDD(),
                bis.getGJDDYWMC(), bis.getAFLGRQ(), bis.getLGYSZDGD(), bis.getZJZLDM(), bis.getZJSM(),
                bis.getZJZZCMC(), bis.getZJXH(), bis.getTJQZLDM(), bis.getTJQSL(), bis.getXJMZCS(), bis.getMZCS(),
                bis.getKZCS(), bis.getMZPSL(), bis.getKZPSL(), bis.getHDKFDJ(), bis.getGX(), bis.getEDXW(),
                bis.getEDCW(), bis.getCKDEZJ(), bis.getJSDY(), bis.getZDHS(), bis.getCXJC(), bis.getJBCL(), bis.getJBCS(),
                bis.getHCSL(), bis.getSMHCBS(), bis.getSCDWZ(), bis.getZCBSL(), bis.getJCZDHCDDM(), bis.getDSKX(),
                bis.getFDJGL(), bis.getGMDSSSBQK(), bis.getCJJGBM(), bis.getHSJGDM(), bis.getBZ(), bis.getSFZX(),
                bis.getTPZJ(), bis.getCJSJ(), bis.getCJR(), bis.getCJJGDM(), bis.getCJBMDM(), bis.getZHXGSJ(),
                bis.getZHXGR(), bis.getSYRZJ(), bis.getSYR(), bis.getSYRDZ(), bis.getSYRLXDH(),
                bis.getSYRFRDB(), bis.getJYRZJ(), bis.getJYR(), bis.getJYRDZ(), bis.getJYRFRDB(),
                bis.getJYRLXDH(), bis.getGLRZJ(), bis.getGLR(), bis.getGLRDZ(), bis.getGLRFRDB(), bis.getGLRLXDH(), bis.getCBLXDM(),
                bis.getSTPZH(), bis.getTZPZDW(), bis.getZJBH(), bis.getCTCL(), bis.getCYRS(),
                bis.getYZCS(), bis.getYZCZRJ(), bis.getGXSJ()};
        queryRunner.update(sql, params);
    }
}