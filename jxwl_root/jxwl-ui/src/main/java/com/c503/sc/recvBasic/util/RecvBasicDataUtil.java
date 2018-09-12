package com.c503.sc.recvBasic.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.zx.data.service.AutoWebService;
import com.zx.framework.util.json.DealJSON;

public class RecvBasicDataUtil {

	public static String[] getData() {
		// 返回数据企业数据信息array集合
		String[] data = null;

		try {
			URL url = new URL("http://172.31.2.195:8081/manger/ws/getEnterpriseInfo?wsdl");// 请求服务路径，根据实际情况修改
			QName qnme = new QName("http://impl.service.data.zx.com/",
					"AutoWebServiceImplService");
			Service service = Service.create(url, qnme);
			AutoWebService port = service.getPort(AutoWebService.class);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<String, Object> param = new HashMap<String, Object>();
//			Map<String, Object> param = new HashMap<>();// 参数集合
			/** param.put("columnname","") param.put("columnval","") 参数格式 */
			param.put("columnname", "ACCI_ID");// 可写,如果服务配置了参数，那么这里必须要对应
			param.put("columnval", "1");// 可写

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(param);
			String jsons = DealJSON.encodeObject2Json(list);
			// FIXME account、password 是从登录平台获取
			paramMap.put("account", "test"); // 可写,如果服务配置了口令，那么这里必须要对应
			paramMap.put("password", "123456"); // 可写
			paramMap.put("departname", "卓讯信息");// 可写
			paramMap.put("json", jsons);
			paramMap.put("page", "1"); // 分页的页数
			paramMap.put("rows", "1"); // 每页的记录数

			// 得到数据集合：data[0]是数据 、data[1]是总项数
			data = port.execute(paramMap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return data;
	}

	public static void main(String[] args) {
		RecvBasicDataUtil.getData();
	}
}
