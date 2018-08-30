package com.company;

import com.heqin.Ghsjzx;
import com.heqin.GhsjzxService_Service;
import org.apache.log4j.Logger;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        GhsjzxService_Service ghsjzxService_service = new GhsjzxService_Service();

        Ghsjzx ghsjzxPort = ghsjzxService_service.getGhsjzxPort();

        //码头作业信息
        String s = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                80821, 1, "V_WH_WHZYSBD", "0");
        //港口企业数据
        /*String ss = ghsjzxPort.ghsjzxService("HH_ZHGS", "HH_ZHGS",
                1, 10, "V_WH_SBQYXX", "0");*/

        System.out.println(s);
        //log.info(ss);
        //JSONObject.fromObjec
        /*for (int i = 0; i < s.length(); i++) {
            System.out.println(s);

        }*/


    }
}
