package com.hthj.data.utils;

import com.hthj.data.domain.DockWorkInformation;
import net.sf.json.JSONObject;

public class DataTransformUtils {

    public static DockWorkInformation getData(JSONObject jsonObject){
        /**获取每个属性的value*/
        Object appliid = jsonObject.get("APPLIID");
        Object linkmannm = jsonObject.get("LINKMANNM");
        Object telephonenumnb = jsonObject.get("TELEPHONENUMNB");
        Object consigner = jsonObject.get("CONSIGNER");

        /**码头作业信息进行封装*/
        DockWorkInformation dwi = new DockWorkInformation();
        dwi.setAPPLIID((String) appliid);
        dwi.setLINKMANNM((String)linkmannm);
        dwi.setTELEPHONENUMNB((String)telephonenumnb);
        dwi.setCONSIGNER((String)consigner);
        return dwi;
    }
}
