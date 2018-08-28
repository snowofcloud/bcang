package com.company;

import com.xuxueqiang.Cbjbxx;
import com.xuxueqiang.CbjbxxService_Service;
import org.apache.log4j.Logger;

public class Main {

    private static Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        CbjbxxService_Service cbjbxxService_service = new CbjbxxService_Service();
        Cbjbxx cbjbxxPort= cbjbxxService_service.getCbjbxxPort();
        String s = cbjbxxPort.cbjbxxService("HH_ZHGS","HH_ZHGS",
                18,30000,
                "v_cb_cbjbxx_qg","0");
        //System.out.println(s);

        log.info(s);

    }
}
