package com.c503.hthj.data.utils;

import com.c503.hthj.data.domain.DangerousGoodsDeclarationDangerousGood;
import net.sf.json.JSONObject;

public class DangerousGoodsDeclarationDangerousGoodTransformUtils {

    private static DangerousGoodsDeclarationDangerousGood dgddg = new DangerousGoodsDeclarationDangerousGood();

    /**
     * 查询数据
     */
    public static String queryData(JSONObject jsonObject){
        Object goods_id1 = jsonObject.get("GOODS_ID");
        String goods_id = (String)goods_id1;
        return goods_id;
    }

    /**
     * 封装数据
     */
    public static DangerousGoodsDeclarationDangerousGood getData(JSONObject jsonObject) {
        /**获取每个属性的value*/

        Object goods_id = jsonObject.get("GOODS_ID");
        Object dangergoodscnnm = jsonObject.get("DANGERGOODSCNNM");
        Object uncode = jsonObject.get("UNCODE");
        Object tallykind = jsonObject.get("TALLYKIND");
        Object amountnb = jsonObject.get("AMOUNTNB");
        Object casingnm = jsonObject.get("CASINGNM");

        Object appliid = jsonObject.get("APPLIID");
        Object dangergoodsennm = jsonObject.get("DANGERGOODSENNM");
        Object dangergoodsid = jsonObject.get("DANGERGOODSID");
        Object dangergoodsmaintype = jsonObject.get("DANGERGOODSMAINTYPE");
        Object is_delete = jsonObject.get("IS_DELETE");
        Object is_read = jsonObject.get("IS_READ");
        Object sjc = jsonObject.get("SJC");
        Object consigner_name = jsonObject.get("CONSIGNER_NAME");
        Object consigner_id = jsonObject.get("CONSIGNER_ID");



        /**码头作业信息进行封装*/
        //DangerousGoodsDeclarationDangerousGood dgddg = new DangerousGoodsDeclarationDangerousGood();

        dgddg.setGOODS_ID((String) goods_id);
        dgddg.setDANGERGOODSCNNM((String) dangergoodscnnm);
        dgddg.setUNCODE((String) uncode);
        dgddg.setTALLYKIND((String)tallykind);
        dgddg.setAMOUNTNB((String)amountnb);
        dgddg.setCASINGNM((String)casingnm);
        dgddg.setAPPLIID((String) appliid);
        dgddg.setDANGERGOODSENNM((String) dangergoodsennm);
        dgddg.setDANGERGOODSID((String) dangergoodsid);
        dgddg.setDANGERGOODSMAINTYPE((String) dangergoodsmaintype);
        dgddg.setIS_DELETE((String) is_delete);
        dgddg.setIS_READ((String) is_read);
        dgddg.setSJC((String) sjc);
        dgddg.setCONSIGNER_NAME((String) consigner_name);
        dgddg.setCONSIGNER_ID((String) consigner_id);


        /**返回dwi*/
        return dgddg;
    }
}
