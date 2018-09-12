/*
 * 文件名：FullScreenServiceImpl.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-8-31
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.c503.sc.base.exception.CustomException;
import com.c503.sc.jxwl.common.constant.CommonConstant;
import com.c503.sc.jxwl.common.constant.SysCommonConstant;
import com.c503.sc.jxwl.transacation.bean.FullScreenData;
import com.c503.sc.jxwl.transacation.service.IFullScreenService;
import com.c503.sc.log.LoggingManager;
import com.c503.sc.log.resource.ResourceManager;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;
import com.zx.exch.core.ws.AutoWebService;
import com.zx.framework.util.json.DealJSON;
import com.zx.framework.util.sercurity.BaseCodeUtil;

/**
 * 〈一句话功能简述〉大屏展示业务实现层 〈功能详细描述〉
 * 
 * @author wangwenhuan
 * @version [版本号, 2016-11-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Service(value = "fullScreenService")
public class FullScreenServiceImpl implements IFullScreenService {
	/** 日志器 */
	private static final LoggingManager LOGGER = LoggingManager.getLogger(FullScreenServiceImpl.class);

	/** 大屏展示路径 */
	@Value("#{configProperties['fullScreenUrl']}")
	private String fullScreenUrl;

	/** 截取条数 */
	@Value("#{configProperties['dataSize']}")
	private String dataSize;

	private List<String> images = new ArrayList<String>();

	private List<FullScreenData> newsList = new ArrayList<FullScreenData>();

	private List<FullScreenData> announceList = new ArrayList<FullScreenData>();

	private List<FullScreenData> rulesList = new ArrayList<FullScreenData>();

	/**
	 * 〈一句话功能简述〉设置fullSrceenUrl 〈功能详细描述〉
	 * 
	 * @param url
	 *            远程地址
	 * @see [类、类#方法、类#成员]
	 */
	public void setFullScreenUrl(String fullScreenUrl) {
		this.fullScreenUrl = fullScreenUrl;
	}

	public Map<String, Object> findAllInfo(Map<String, Object> map) throws Exception {
		try {
			List<FullScreenData> news = this.findNews(map);
			List<FullScreenData> announcements = this.findAnnouncement(map);
			List<FullScreenData> rules = this.findRule(map);
			List<String> images = this.getImages();
			map.clear();
			map.put("news", news);
			map.put("announcements", announcements);
			map.put("rules", rules);
			map.put("images", images);
			LOGGER.info(CommonConstant.FIND_SUC_OPTION, map);
		} catch (Exception e) {
			throw new CustomException(CommonConstant.SYS_EXCEPTION, e, map);
		}
		return map;
	}

	private List<FullScreenData> findNews(Map<String, Object> map) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, map);
		String channelName = getMessageType(SysCommonConstant.FULL_SCREEN_NEWS);
		String result = getData(fullScreenUrl, channelName);
		// 数据解析
		List<FullScreenData> dataList = parseData(result, "news");
		LOGGER.debug(SystemContants.DEBUG_END, dataList);
		return dataList;
	}

	private List<FullScreenData> findAnnouncement(Map<String, Object> map) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, map);
		String channelName = getMessageType(SysCommonConstant.FULL_SCREEN_ANNOUNCEMENT);
		String result = getData(fullScreenUrl, channelName);
		// 数据解析
		List<FullScreenData> dataList = parseData(result, "announce");
		LOGGER.debug(SystemContants.DEBUG_END, dataList);
		return dataList;
	}

	private List<FullScreenData> findRule(Map<String, Object> map) throws Exception {
		LOGGER.debug(SystemContants.DEBUG_START, map);
		String channelName = getMessageType(SysCommonConstant.FULL_SCREEN_RULE);
		String result = getData(fullScreenUrl, channelName);
		// 数据解析
		List<FullScreenData> dataList = parseData(result, "rules");
		LOGGER.debug(SystemContants.DEBUG_END, dataList);
		return dataList;
	}

	private String getData(String fullSrceenUrl, String channelName) throws Exception {
		String result = "";
		URL url = new URL(fullSrceenUrl);// 请求服务路径，根据实际情况修改
		QName qnme = new QName("http://ws.core.exch.zx.com/", "AutoWebServiceImplService");// ws.core.exch.zx.com/
		javax.xml.ws.Service service = javax.xml.ws.Service.create(url, qnme);
		AutoWebService port = service.getPort(AutoWebService.class);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		// 授权的节点账号密码，如有授权必须填写
		paramMap.put("nodeuser", "1");
		paramMap.put("nodepwd", "1");
		// 连接服务参数
		paramMap.put("usercode", "1");// 可写,如果服务配置了口令，那么这里必须要对应
		paramMap.put("userpwd", "1");// 可写
		paramMap.put("issecrity", "1");// 是否加密传输

		// 条件参数（根据服务数据等标准）有两种方法，封装成List或Map。根据服务配置更改，服务的数据标准配置为Map则封装为Map，反之为List.
		// 方法一：把参数封装到Map,支持多个数据标准集合的参数
		Map<String, Object> setMap = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("channel_name", channelName);
		setMap.put("page", param);

		String setStr = DealJSON.encodeObject2Json(setMap);
		if ("1".equals(paramMap.get("issecrity"))) {
			setStr = BaseCodeUtil.encrypt(setStr);
		}
		paramMap.put("data", setStr);
		String jsonStr = DealJSON.encodeObject2Json(paramMap);
		// 得到数据集合
		result = port.execute(jsonStr);
		return result;
	}

	@SuppressWarnings("unchecked")
	private List<String> getImages() {
		images.clear();
		// 剔除无照片内容
		List<FullScreenData> tmpNewsList = findImage(newsList);
		List<FullScreenData> tmpAnnounceList = findImage(announceList);
		List<FullScreenData> tmpRulesList = findImage(rulesList);
		Object[] objArr = new Object[] { tmpNewsList, tmpAnnounceList, tmpRulesList };
		int len = objArr.length;
		List<FullScreenData> list = null;
		int imgCnt = 1;
		for (int i = 0; i < len; i++) {
			list = (List<FullScreenData>) objArr[i];
			imgCnt = findAndFillImage(list, imgCnt);
			if (images.size() >= 3) {
				break;
			}
		}
		// 不足三个，重新调用一遍
		if (images.size() < 3) {
			for (int i = 0; i < len; i++) {
				list = (List<FullScreenData>) objArr[i];
				imgCnt = findAndFillImage(list, imgCnt);
				if (imgCnt >= 3) {
					break;
				}
			}
		}
		return imgCnt <= 3 ? images.subList(0, imgCnt) : images.subList(0, 4);
	}

	private List<FullScreenData> findImage(List<FullScreenData> list) {
		List<FullScreenData> tmpList = new ArrayList<FullScreenData>();
		String imgPath = null;
		FullScreenData data = null;
		for (int i = 0; i < list.size(); i++) {
			data = list.get(i);
			imgPath = data.getTitle_img_path();
			if (!StringUtils.isEmpty(imgPath)) {
				tmpList.add(data);
			}
		}
		return tmpList;
	}

	/**
	 * 〈一句话功能简述〉获得填充图片 〈功能详细描述〉
	 * 
	 * @param list
	 *            展示数据
	 * @param num
	 *            需要填充的图片个数
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private int findAndFillImage(List<FullScreenData> list, int num) {
		String imgPath = null;
		int n = 0;
		for (FullScreenData data : list) {
			imgPath = data.getTitle_img_path();
			if (!StringUtils.isEmpty(imgPath) && n < num && isNoRepeated(imgPath)) {
				images.add(imgPath);
				n++;
				if (n == num) {
					break;
				}
			}
		}
		// 返回值为下一个类别需要填充的图片的个数
		if (n < num) {
			return 1 + (num - n);
		} else {
			return 1;
		}
	}

	/**
	 * 〈一句话功能简述〉图片是否不重复 〈功能详细描述〉
	 * 
	 * @param imgPath
	 *            待对比图片路径
	 * @return true/false:不重复/重复
	 * @see [类、类#方法、类#成员]
	 */
	private boolean isNoRepeated(String imgPath) {
		boolean isNoRepeated = true;
		int size = images.size();
		String tmpImgPath = null;
		for (int i = 0; i < size; i++) {
			tmpImgPath = images.get(i);
			if (C503StringUtils.equals(imgPath, tmpImgPath)) {
				isNoRepeated = false;
				break;
			}
		}
		return isNoRepeated;
	}

	/**
	 * 〈一句话功能简述〉获取消息类型 〈功能详细描述〉
	 * 
	 * @param messageType
	 *            消息类型代码
	 * @return 消息类型对应汉字
	 * @see [类、类#方法、类#成员]
	 */
	private String getMessageType(String messageType) {
		String messageTypeName = ResourceManager.getMessage(messageType);
		return messageTypeName;
	}

	private List<FullScreenData> parseData(String result, String type) {
		List<FullScreenData> list = null;
		if (!C503StringUtils.isEmpty(result)) {
			JSONObject object = JSON.parseObject(result);
			Object jsonArray = object.get("xwdt");
			list = JSON.parseArray(jsonArray + "", FullScreenData.class);
			if ("news".equals(type)) {
				newsList.clear();
				newsList.addAll(list);
			} else if ("announce".equals(type)) {
				announceList.clear();
				announceList.addAll(list);
			} else if ("rules".equals(type)) {
				rulesList.clear();
				rulesList.addAll(list);
			}
			int size = 16;
			size = Integer.parseInt(dataSize);
			if (list.size() > size) {
				list = list.subList(0, size);
			}
		}
		return list;
	}
}
