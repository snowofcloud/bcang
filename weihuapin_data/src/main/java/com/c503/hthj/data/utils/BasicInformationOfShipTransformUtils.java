package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.BasicInformationOfShip;
import net.sf.json.JSONObject;

public class BasicInformationOfShipTransformUtils {

    private static BasicInformationOfShip bis = new BasicInformationOfShip();

    /**
     * 查询主键
     */
    public static String queryData(JSONObject jsonObject) {
        Object cbzj1 = jsonObject.get("CBZJ");
        String cbzj = (String) cbzj1;
        return cbzj;
    }

    public static BasicInformationOfShip getData(JSONObject jsonObject) {
        /**获取每个属性的value*/
        Object cbzj = jsonObject.get("CBZJ");
        Object cbdjh = jsonObject.get("CBDJH");
        Object cbsbh = jsonObject.get("CBSBH");
        Object ccdjh = jsonObject.get("CCDJH");
        Object cjdjh = jsonObject.get("CJDJH");
        Object cyzwcm = jsonObject.get("CYZWCM");
        Object pbh = jsonObject.get("PBH");
        Object zwcm = jsonObject.get("ZWCM");
        Object ywcm = jsonObject.get("YWCM");
        Object cbickh = jsonObject.get("CBICKH");
        Object mmsi = jsonObject.get("MMSI");
        Object imo = jsonObject.get("IMO");
        Object hh = jsonObject.get("HH");
        Object cjgdm = jsonObject.get("CJGDM");
        Object cqgdm = jsonObject.get("CQGDM");
        Object hhcbzdm = jsonObject.get("HHCBZDM");
        Object cbzldm = jsonObject.get("CBZLDM");
        Object zdw = jsonObject.get("ZDW");
        Object jdw = jsonObject.get("JDW");
        Object ckzzd = jsonObject.get("CKZZD");
        Object zjzgl = jsonObject.get("ZJZGL");
        Object cbzc = jsonObject.get("CBZC");
        Object cbxk = jsonObject.get("CBXK");
        Object cbxs = jsonObject.get("CBXS");
        Object hdhxdm = jsonObject.get("HDHXDM");
        Object hdhqdm = jsonObject.get("HDHQDM");
        Object hxqy = jsonObject.get("HXQY");
        Object ctcldm = jsonObject.get("CTCLDM");
        Object csys = jsonObject.get("CSYS");
        Object cbjz = jsonObject.get("CBJZ");
        Object jzrq = jsonObject.get("JZRQ");
        Object zccmc = jsonObject.get("ZCCMC");
        Object zccywmc = jsonObject.get("ZCCYWMC"); //33个


        Object zcdd = jsonObject.get("ZCDD");
        Object zcddywmc = jsonObject.get("ZCDDYWMC");
        Object gjcmc = jsonObject.get("GJCMC");
        Object gjcywmc = jsonObject.get("GJCYWMC");
        Object gjrq = jsonObject.get("GJRQ");
        Object gjdd = jsonObject.get("GJDD");
        Object gjddywmc = jsonObject.get("GJDDYWMC");
        Object aflgrq = jsonObject.get("AFLGRQ");
        Object lgyszdgd = jsonObject.get("LGYSZDGD");
        Object zjzldm = jsonObject.get("ZJZLDM");
        Object zjsm = jsonObject.get("ZJSM");
        Object zjzzcmc = jsonObject.get("ZJZZCMC");
        Object zjxh = jsonObject.get("ZJXH");
        Object tjqzldm = jsonObject.get("TJQZLDM");
        Object tjqsl = jsonObject.get("TJQSL");
        Object xjmzcs = jsonObject.get("XJMZCS");
        Object mzcs = jsonObject.get("MZCS");
        Object kzcs = jsonObject.get("KZCS");
        Object mzpsl = jsonObject.get("MZPSL");
        Object kzpsl = jsonObject.get("KZPSL");
        Object hdkfdj = jsonObject.get("HDKFDJ");
        Object gx = jsonObject.get("GX");
        Object edxw = jsonObject.get("EDXW");
        Object edcw = jsonObject.get("EDCW");
        Object ckdezj = jsonObject.get("CKDEZJ");
        Object jsdy = jsonObject.get("JSDY");
        Object zdhs = jsonObject.get("ZDHS");
        Object cxjc = jsonObject.get("CXJC");
        Object jbcl = jsonObject.get("JBCL");
        Object jbcs = jsonObject.get("JBCS");
        Object hcsl = jsonObject.get("HCSL");
        Object smhcbs = jsonObject.get("SMHCBS");
        Object scdwz = jsonObject.get("SCDWZ");
        Object zcbsl = jsonObject.get("ZCBSL");//34个

        Object jczdhcddm = jsonObject.get("JCZDHCDDM");
        Object dskx = jsonObject.get("DSKX");
        Object fdjgl = jsonObject.get("FDJGL");
        Object gmdsssbqk = jsonObject.get("GMDSSSBQK");
        Object cjjgbm = jsonObject.get("CJJGBM");
        Object hsjgdm = jsonObject.get("HSJGDM");
        Object bz = jsonObject.get("BZ");
        Object sfzx = jsonObject.get("SFZX");
        Object tpzj = jsonObject.get("TPZJ");
        Object cjsj = jsonObject.get("CJSJ");
        Object cjr = jsonObject.get("CJR");
        Object cjjgdm = jsonObject.get("CJJGDM");
        Object cjbmdm = jsonObject.get("CJBMDM");
        Object zhxgsj = jsonObject.get("ZHXGSJ");
        Object zhxgr = jsonObject.get("ZHXGR");
        Object syrzj = jsonObject.get("SYRZJ");
        Object syr = jsonObject.get("SYR");
        Object syrdz = jsonObject.get("SYRDZ");
        Object syrlxdh = jsonObject.get("SYRLXDH");
        Object syrfrdb = jsonObject.get("SYRFRDB");
        Object jyrzj = jsonObject.get("JYRZJ");
        Object jyr = jsonObject.get("JYR");
        Object jyrdz = jsonObject.get("JYRDZ");
        Object jyrfrdb = jsonObject.get("JYRFRDB");
        Object jyrlxdh = jsonObject.get("JYRLXDH");
        Object glrzj = jsonObject.get("GLRZJ");
        Object glr = jsonObject.get("GLR");
        Object glrdz = jsonObject.get("GLRDZ");
        Object glrfrdb = jsonObject.get("GLRFRDB");
        Object glrlxdh = jsonObject.get("GLRLXDH");
        Object cblxdm = jsonObject.get("CBLXDM");
        Object stpzh = jsonObject.get("STPZH");
        Object tzpzdw = jsonObject.get("TZPZDW");
        Object zjbh = jsonObject.get("ZJBH");
        Object ctcl = jsonObject.get("CTCL");
        Object cyrs = jsonObject.get("CYRS");
        Object yzcs = jsonObject.get("YZCS");
        Object yzczrj = jsonObject.get("YZCZRJ");
        Object gxsj = jsonObject.get("GXSJ");//39个

        /**港口企业数据进行封装*/
        //BasicInformationOfShip bis = new BasicInformationOfShip();

        bis.setCBZJ((String) cbzj);
        bis.setCBDJH((String) cbdjh);
        bis.setCBSBH((String) cbsbh);
        bis.setCCDJH((String) ccdjh);
        bis.setCJDJH((String) cjdjh);
        bis.setCYZWCM((String) cyzwcm);
        bis.setPBH((String) pbh);
        bis.setZWCM((String) zwcm);
        bis.setYWCM((String) ywcm);
        bis.setCBICKH((String) cbickh);
        bis.setMMSI((String) mmsi);
        bis.setIMO((String) imo);
        bis.setHH((String) hh);
        bis.setCJGDM((String) cjgdm);
        bis.setCQGDM((String) cqgdm);
        bis.setHHCBZDM((String) hhcbzdm);
        bis.setCBZLDM((String) cbzldm);
        bis.setZDW((String) zdw);
        bis.setJDW((String) jdw);
        bis.setCKZZD((String) ckzzd);
        bis.setZJZGL((String) zjzgl);
        bis.setCBZC((String) cbzc);
        bis.setCBXK((String) cbxk);
        bis.setCBXS((String) cbxs);
        bis.setHDHXDM((String) hdhxdm);
        bis.setHDHQDM((String) hdhqdm);
        bis.setHXQY((String) hxqy);
        bis.setCTCLDM((String) ctcldm);
        bis.setCSYS((String) csys);
        bis.setCBJZ((String) cbjz);
        bis.setJZRQ((String) jzrq);
        bis.setZCCMC((String) zccmc);
        bis.setZCCYWMC((String) zccywmc);//33个

        bis.setZCDD((String) zcdd);
        bis.setZCDDYWMC((String) zcddywmc);
        bis.setGJCMC((String) gjcmc);
        bis.setGJCYWMC((String) gjcywmc);
        bis.setGJRQ((String) gjrq);
        bis.setGJDD((String) gjdd);
        bis.setGJDDYWMC((String) gjddywmc);
        bis.setAFLGRQ((String) aflgrq);
        bis.setLGYSZDGD((String) lgyszdgd);
        bis.setZJZLDM((String) zjzldm);
        bis.setZJSM((String) zjsm);
        bis.setZJZZCMC((String) zjzzcmc);
        bis.setZJXH((String) zjxh);
        bis.setTJQZLDM((String) tjqzldm);
        bis.setTJQSL((String) tjqsl);
        bis.setXJMZCS((String) xjmzcs);
        bis.setMZCS((String) mzcs);
        bis.setKZCS((String) kzcs);
        bis.setMZPSL((String) mzpsl);
        bis.setKZPSL((String) kzpsl);
        bis.setHDKFDJ((String) hdkfdj);
        bis.setGX((String) gx);
        bis.setEDXW((String) edxw);
        bis.setEDCW((String) edcw);
        bis.setCKDEZJ((String) ckdezj);
        bis.setJSDY((String) jsdy);
        bis.setZDHS((String) zdhs);
        bis.setCXJC((String) cxjc);
        bis.setJBCL((String) jbcl);
        bis.setJBCS((String) jbcs);
        bis.setHCSL((String) hcsl);
        bis.setSMHCBS((String) smhcbs);
        bis.setSCDWZ((String) scdwz);
        bis.setZCBSL((String) zcbsl);//34个

        bis.setJCZDHCDDM((String) jczdhcddm);
        bis.setDSKX((String) dskx);
        bis.setFDJGL((String) fdjgl);
        bis.setGMDSSSBQK((String) gmdsssbqk);
        bis.setCJJGBM((String) cjjgbm);
        bis.setHSJGDM((String) hsjgdm);
        bis.setBZ((String) bz);
        bis.setSFZX((String) sfzx);
        bis.setTPZJ((String) tpzj);
        bis.setCJSJ((String) cjsj);
        bis.setCJR((String) cjr);
        bis.setCJJGDM((String) cjjgdm);
        bis.setCJBMDM((String) cjbmdm);
        bis.setZHXGSJ((String) zhxgsj);
        bis.setZHXGR((String) zhxgr);
        bis.setSYRZJ((String) syrzj);
        bis.setSYR((String) syr);
        bis.setSYRDZ((String) syrdz);
        bis.setSYRLXDH((String) syrlxdh);
        bis.setSYRFRDB((String) syrfrdb);
        bis.setJYRZJ((String) jyrzj);
        bis.setJYR((String) jyr);
        bis.setJYRDZ((String) jyrdz);
        bis.setJYRFRDB((String) jyrfrdb);
        bis.setJYRLXDH((String) jyrlxdh);
        bis.setGLRZJ((String) glrzj);
        bis.setGLR((String) glr);
        bis.setGLRDZ((String) glrdz);
        bis.setGLRFRDB((String) glrfrdb);
        bis.setGLRLXDH((String) glrlxdh);
        bis.setCBLXDM((String) cblxdm);
        bis.setSTPZH((String) stpzh);
        bis.setTZPZDW((String) tzpzdw);
        bis.setZJBH((String) zjbh);
        bis.setCTCL((String) ctcl);
        bis.setCYRS((String) cyrs);
        bis.setYZCS((String) yzcs);
        bis.setYZCZRJ((String) yzczrj);
        bis.setGXSJ((String) gxsj);
        /**返回ped*/
        return bis;
    }
}
