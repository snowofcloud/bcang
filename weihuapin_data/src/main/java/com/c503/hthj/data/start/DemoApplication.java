package com.c503.hthj.data.start;

import com.c503.hthj.data.methods.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		//港口企业数据，目前有48条数据，后来103条数据，工程启动1秒后开始执行，12小时后开始循环更新数据
		//V_WH_SBQYXX
		PortEnterpriseDatas.method();

		//港口企业基本信息，目前有3267条数据，工程启动5秒后开始执行，12小时后开始循环更新数据
		//V_XZXK_GKQYJBXX
		PortEnterpriseBasicInformations.method();

		//人员基础信息，目前有839条数据，工程启动5秒后开始执行，12小时后开始循环更新数据
		//V_WH_RYJCXX
		BasicPersonnelInformations.method();

		//码头作业信息，目前有81984条数据，工程启动5秒后开始执行，12小时后开始循环更新数据
		//V_WH_WHZYSBD
		DockWorkInformations.method();

		//危货作业申报单_海事申报记录，目前有134577条数据，工程启动5秒后开始执行，12小时后开始循环更新数据
		//V_WH_WHZYSBD_HSSBJL
		DangerousGoodsDeclarationMarineDeclarationRecords.method();

		//危货作业申报单_危险货物，目前有195244条数据，工程启动5秒后开始执行，12小时后开始循环更新数据
		//V_WH_WHZYSBD_WXHW
		DangerousGoodsDeclarationDangerousGoods.method();

		//船舶基本信息(全国船舶基本信息)，目前有195244条数据，工程启动5秒后开始执行，12小时后开始循环更新数据
		//v_cb_cbjbxx_qg
		BasicInformationOfShips.method();
	}
}
