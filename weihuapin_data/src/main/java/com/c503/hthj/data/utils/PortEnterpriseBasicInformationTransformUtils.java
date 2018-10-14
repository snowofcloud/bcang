package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.PortEnterpriseBasicInformation;
import net.sf.json.JSONObject;

public class PortEnterpriseBasicInformationTransformUtils {
    //只创建一个静态对象
    private static PortEnterpriseBasicInformation pebi = new PortEnterpriseBasicInformation();

    /**查询数据*/
    public static String queryData(JSONObject jsonObject){
        Object ywjbxxid1 = jsonObject.get("YWJBXXID");
        String ywjbxxid = (String)ywjbxxid1;
        return ywjbxxid;
    }

    /**封装数据*/
    public static PortEnterpriseBasicInformation getData(JSONObject jsonObject) {
        /**获取每个属性的value*/
        Object ywjbxxid = jsonObject.get("YWJBXXID");
        Object jbxx_jbrlxdh = jsonObject.get("JBXX_JBRLXDH");
        Object jbxx_qylxr = jsonObject.get("JBXX_QYLXR");
        Object jbxx_sfzh = jsonObject.get("JBXX_SFZH");
        Object jbxx_yyzzbh = jsonObject.get("JBXX_YYZZBH");
        Object jbxx_bz = jsonObject.get("JBXX_BZ");
        Object jbxx_jjdy = jsonObject.get("JBXX_JJDY");
        Object jbxx_gkjyxkzyxq = jsonObject.get("JBXX_GKJYXKZYXQ");
        Object jbxx_sfzygkyw = jsonObject.get("JBXX_SFZYGKYW");
        Object jbxx_zcd = jsonObject.get("JBXX_ZCD");
        Object jbxx_zcdxxdz = jsonObject.get("JBXX_ZCDXXDZ");
        Object jbxx_gkssjyr = jsonObject.get("JBXX_GKSSJYR");
        Object jbxx_jbr = jsonObject.get("JBXX_JBR");
        Object jbxx_qyjjlxfl = jsonObject.get("JBXX_QYJJLXFL");
        Object jbxx_qyjjlxzl = jsonObject.get("JBXX_QYJJLXZL");
        Object jbxx_zczb = jsonObject.get("JBXX_ZCZB");
        Object jbxx_cz = jsonObject.get("JBXX_CZ");
        Object jbxx_gkjyxkzhswsz = jsonObject.get("JBXX_GKJYXKZHSWSZ");
        Object jbxx_gkjyxkzfzrq = jsonObject.get("JBXX_GKJYXKZFZRQ");
        Object jbxx_yx = jsonObject.get("JBXX_YX");
        Object jbxx_gsdz = jsonObject.get("JBXX_GSDZ");
        Object jbxx_fr = jsonObject.get("JBXX_FR");
        Object jbxx_zczbdw = jsonObject.get("JBXX_ZCZBDW");
        Object jbxx_gkjyxxfw = jsonObject.get("JBXX_GKJYXXFW");
        Object jbxx_gdzc = jsonObject.get("JBXX_GDZC");
        Object jbxx_qylxrdh = jsonObject.get("JBXX_QYLXRDH");
        Object jbxx_yb = jsonObject.get("JBXX_YB");
        Object jbxx_frlxdh = jsonObject.get("JBXX_FRLXDH");
        Object jbxx_qyjc = jsonObject.get("JBXX_QYJC");
        Object jbxx_sqjynx = jsonObject.get("JBXX_SQJYNX");
        Object jbxx_qyzylx = jsonObject.get("JBXX_QYZYLX");
        Object jbxx_gkjyxkzh = jsonObject.get("JBXX_GKJYXKZH");
        Object jbxx_gkjyfw = jsonObject.get("JBXX_GKJYFW");
        Object jbxx_csgkwxhwzy = jsonObject.get("JBXX_CSGKWXHWZY");

        /**港口企业数据进行封装*/  //每获取到一条信息，就创建一个对象比较消耗内存
        // PortEnterpriseBasicInformation pebi = new PortEnterpriseBasicInformation();
        pebi.setYWJBXXID((String)ywjbxxid);
        pebi.setJBXX_JBRLXDH((String)jbxx_jbrlxdh);
        pebi.setJBXX_QYLXR((String)jbxx_qylxr);
        pebi.setJBXX_SFZH((String)jbxx_sfzh);
        pebi.setJBXX_YYZZBH((String)jbxx_yyzzbh);
        pebi.setJBXX_BZ((String)jbxx_bz);
        pebi.setJBXX_JJDY((String)jbxx_jjdy);
        pebi.setJBXX_GKJYXKZYXQ((String)jbxx_gkjyxkzyxq);
        pebi.setJBXX_SFZYGKYW((String)jbxx_sfzygkyw);
        pebi.setJBXX_ZCD((String)jbxx_zcd);
        pebi.setJBXX_ZCDXXDZ((String)jbxx_zcdxxdz);
        pebi.setJBXX_GKSSJYR((String)jbxx_gkssjyr);
        pebi.setJBXX_JBR((String)jbxx_jbr);
        pebi.setJBXX_QYJJLXFL((String)jbxx_qyjjlxfl);
        pebi.setJBXX_QYJJLXZL((String)jbxx_qyjjlxzl);
        pebi.setJBXX_ZCZB((String)jbxx_zczb);
        pebi.setJBXX_CZ((String)jbxx_cz);
        pebi.setJBXX_GKJYXKZHSWSZ((String)jbxx_gkjyxkzhswsz);
        pebi.setJBXX_GKJYXKZFZRQ((String)jbxx_gkjyxkzfzrq);
        pebi.setJBXX_YX((String)jbxx_yx);
        pebi.setJBXX_GSDZ((String)jbxx_gsdz);
        pebi.setJBXX_FR((String)jbxx_fr);
        pebi.setJBXX_ZCZBDW((String)jbxx_zczbdw);
        pebi.setJBXX_GKJYXXFW((String)jbxx_gkjyxxfw);
        pebi.setJBXX_GDZC((String)jbxx_gdzc);
        pebi.setJBXX_QYLXRDH((String)jbxx_qylxrdh);
        pebi.setJBXX_YB((String)jbxx_yb);
        pebi.setJBXX_FRLXDH((String)jbxx_frlxdh);
        pebi.setJBXX_QYJC((String)jbxx_qyjc);
        pebi.setJBXX_SQJYNX((String)jbxx_sqjynx);
        pebi.setJBXX_QYZYLX((String)jbxx_qyzylx);
        pebi.setJBXX_GKJYXKZH((String)jbxx_gkjyxkzh);
        pebi.setJBXX_GKJYFW((String)jbxx_gkjyfw);
        pebi.setJBXX_CSGKWXHWZY((String)jbxx_csgkwxhwzy);
        /**返回ped*/
        return pebi;
    }
}
