package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.AllShipInfo;
import net.sf.json.JSONObject;

/**
 * @auther xuxq
 * @date 2018/11/29 10:44
 */
public class AllShipInfoTransformUtils {

    private static AllShipInfo allShipInfo = new AllShipInfo();

    /**
     * 查询主键
     */
    public static String queryData(JSONObject jsonObject) {
        Object cbdjh1 = jsonObject.get("CBDJH");
        String cbdjh = (String) cbdjh1;
        return cbdjh;
    }

    public static AllShipInfo getData(JSONObject jsonObject){
        Object hsjgdm = jsonObject.get("HSJGDM");allShipInfo.setHSJGDM((String) hsjgdm);
        Object cbxs = jsonObject.get("CBXS");allShipInfo.setCBXS((Integer) cbxs);
        Object jyrdz = jsonObject.get("JYRDZ");allShipInfo.setJYRDZ((String) jyrdz);
        Object zhxgsj = jsonObject.get("ZHXGSJ");allShipInfo.setZHXGSJ((String) zhxgsj);
        Object jbcs = jsonObject.get("JBCS");allShipInfo.setJBCS((Integer) jbcs);
        Object cbdjh = jsonObject.get("CBDJH");allShipInfo.setCBDJH((String) cbdjh);
        Object cbxk = jsonObject.get("CBXK");allShipInfo.setCBXK((Integer) cbxk);
        Object kzpsl = jsonObject.get("KZPSL");allShipInfo.setKZPSL((Integer) kzpsl);
        Object lgyszdgd = jsonObject.get("LGYSZDGD");allShipInfo.setLGYSZDGD((Integer) lgyszdgd);
        Object zjzgl = jsonObject.get("ZJZGL");allShipInfo.setZJZGL((Integer) zjzgl);
        Object cjgdm = jsonObject.get("CJGDM");allShipInfo.setCJGDM((String) cjgdm);
        Object zdhs = jsonObject.get("ZDHS");allShipInfo.setZDHS((Integer) zdhs);
        Object zjsm = jsonObject.get("ZJSM");allShipInfo.setZJSM((Integer) zjsm);
        Object ywcm = jsonObject.get("YWCM");allShipInfo.setYWCM((String) ywcm);
        Object hdkfdj = jsonObject.get("HDKFDJ");allShipInfo.setHDKFDJ((String) hdkfdj);
        Object tjqzldm = jsonObject.get("TJQZLDM");allShipInfo.setTJQZLDM((String) tjqzldm);
        Object hdhxdm = jsonObject.get("HDHXDM");allShipInfo.setHDHXDM((String) hdhxdm);
        Object cyrs = jsonObject.get("CYRS");allShipInfo.setCYRS((Integer) cyrs);
        Object jzrq = jsonObject.get("JZRQ");allShipInfo.setJZRQ((String) jzrq);
        Object syr = jsonObject.get("SYR");allShipInfo.setSYR((String) syr);
        Object xjmzcs = jsonObject.get("XJMZCS");allShipInfo.setXJMZCS((Integer) xjmzcs);
        Object row_id = jsonObject.get("ROW_ID");allShipInfo.setROW_ID((Integer) row_id);
        Object cqgdm = jsonObject.get("CQGDM");allShipInfo.setCQGDM((String) cqgdm);
        Object hcsl = jsonObject.get("HCSL");allShipInfo.setHCSL((Integer) hcsl);
        Object ctcldm = jsonObject.get("CTCLDM");allShipInfo.setCTCLDM((String) ctcldm);
        Object cyzwcm = jsonObject.get("CYZWCM");allShipInfo.setCYZWCM((String) cyzwcm);
        Object cbzldm = jsonObject.get("CBZLDM");allShipInfo.setCBZLDM((String) cbzldm);
        Object zjzldm = jsonObject.get("ZJZLDM");allShipInfo.setZJZLDM((String) zjzldm);
        Object mzpsl = jsonObject.get("MZPSL");allShipInfo.setMZPSL((Integer) mzpsl);
        Object kzcs = jsonObject.get("KZCS");allShipInfo.setKZCS((Integer) kzcs);
        Object jsdy = jsonObject.get("JSDY");allShipInfo.setJSDY((Integer) jsdy);
        Object zcdd = jsonObject.get("ZCDD");allShipInfo.setZCDD((String) zcdd);
        Object zcbsl = jsonObject.get("ZCBSL");allShipInfo.setZCBSL((Integer) zcbsl);
        Object zccmc = jsonObject.get("ZCCMC");allShipInfo.setZCCMC((String) zccmc);
        Object hhcbzdm = jsonObject.get("HHCBZDM");allShipInfo.setHHCBZDM((String) hhcbzdm);
        Object ckdezj = jsonObject.get("CKDEZJ");allShipInfo.setCKDEZJ((Integer) ckdezj);
        Object zwcm = jsonObject.get("ZWCM");allShipInfo.setZWCM((String) zwcm);
        Object ckzzd = jsonObject.get("CKZZD");allShipInfo.setCKZZD((Integer) ckzzd);
        Object cbzj = jsonObject.get("CBZJ");allShipInfo.setCBZJ((String) cbzj);
        Object cbsbh = jsonObject.get("CBSBH");allShipInfo.setCBSBH((String) cbsbh);
        Object cbzc = jsonObject.get("CBZC");allShipInfo.setCBZC((Integer) cbzc);
        Object jdw = jsonObject.get("JDW");allShipInfo.setJDW((Integer) jdw);
        Object cjsj = jsonObject.get("CJSJ");allShipInfo.setCJSJ((String) cjsj);
        Object jyr = jsonObject.get("JYR");allShipInfo.setJYR((String) jyr);
        Object yzczrj = jsonObject.get("YZCZRJ");allShipInfo.setYZCZRJ((Integer) yzczrj);
        Object smhcbs = jsonObject.get("SMHCBS");allShipInfo.setSMHCBS((Integer) smhcbs);
        Object zdw = jsonObject.get("ZDW");allShipInfo.setZDW((Integer) zdw);
        Object ccdjh = jsonObject.get("CCDJH");allShipInfo.setCCDJH((String) ccdjh);
        Object cbjz = jsonObject.get("CBJZ");allShipInfo.setCBJZ((String) cbjz);
        Object gx = jsonObject.get("GX");allShipInfo.setGX((Integer) gx);
        Object cxjc = jsonObject.get("CXJC");allShipInfo.setCXJC((Integer) cxjc);
        Object hdhqdm = jsonObject.get("HDHQDM");allShipInfo.setHDHQDM((String) hdhqdm);
        Object edcw = jsonObject.get("EDCW");allShipInfo.setEDCW((Integer) edcw);
        Object tjqsl = jsonObject.get("TJQSL");allShipInfo.setTJQSL((Integer) tjqsl);
        Object sfzx = jsonObject.get("SFZX");allShipInfo.setSFZX((Integer) sfzx);
        Object yzcs = jsonObject.get("YZCS");allShipInfo.setYZCS((Integer) yzcs);
        Object mzcs = jsonObject.get("MZCS");allShipInfo.setMZCS((Integer) mzcs);
        Object cjbmdm = jsonObject.get("CJBMDM");allShipInfo.setCJBMDM((String) cjbmdm);
        Object edxw = jsonObject.get("EDXW");allShipInfo.setEDXW((Integer) edxw);

        return allShipInfo;
    }

}
