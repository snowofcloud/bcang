### 危化品数据接入工程说明

```
工程技术要点：springboot架构，JDK1.8，IDEA2018.2，Maven(apache-maven-3.5.3)；
```



### 船舶基本信息

	接口：
		http://10.100.70.132:8090/services/sjfw?wsdl
		http://202.107.242.115:10090/services/sjfw?wsdl
	1 抽取接口数据
		{"CBZJ":"260214000822","CBDJH":"260214000822","CBSBH":"CN20149346219","CCDJH":"260214000822","CJDJH":"2014Q2501082","CYZWCM":"--","PBH":"","ZWCM":"鲁济宁拖2278","YWCM":"LU JI NING TUO 2278","CBICKH":"2611016132","MMSI":"         ","IMO":"","HH":"","CJGDM":"0546","CQGDM":"125","HHCBZDM":"0","CBZLDM":"0601","ZDW":"123","JDW":"36","CKZZD":"","ZJZGL":"382","CBZC":"31.6","CBXK":"6.6","CBXS":"2.6","HDHXDM":"1","HDHQDM":"10","HXQY":"A级:;","CTCLDM":"01","CSYS":"","CBJZ":"","JZRQ":"2014-12-05 00:00:00.0","ZCCMC":"山东航宇船业集团有限公司","ZCCYWMC":"","ZCDD":"山东微山","ZCDDYWMC":"","GJCMC":"","GJCYWMC":"","GJRQ":"1900-01-01 00:00:00.0","GJDD":"","GJDDYWMC":"","AFLGRQ":"2014-10-20 00:00:00.0","LGYSZDGD":"4.6","ZJZLDM":"01","ZJSM":"1","ZJZZCMC":"潍柴重机股份有限公司；","ZJXH":"X6170ZC520-2；","TJQZLDM":"01","TJQSL":"1","XJMZCS":"0","MZCS":"2","KZCS":"1.829","MZPSL":"145.8","KZPSL":"114.9","HDKFDJ":"  ","GX":"0.6","EDXW":"0","EDCW":"0","CKDEZJ":"0","JSDY":"","ZDHS":"16","CXJC":"27.8","JBCL":"钢质","JBCS":"","HCSL":"","SMHCBS":"8","SCDWZ":"","ZCBSL":"","JCZDHCDDM":"主推进装置驾驶室遥控","DSKX":"","FDJGL":"","GMDSSSBQK":"","CJJGBM":"2540","HSJGDM":"260200","BZ":"","SFZX":"0","TPZJ":"","CJSJ":"2015-05-18 14:44:03.0","CJR":"","CJJGDM":"","CJBMDM":"260214000822","ZHXGSJ":"2015-05-18 14:44:03.0","ZHXGR":"","SYRZJ":"","SYR":"山东润杨物流有限公司","SYRDZ":"","SYRLXDH":"2119666","SYRFRDB":"张永静","JYRZJ":"","JYR":"-","JYRDZ":"-","JYRFRDB":"张永静","JYRLXDH":"2119666","GLRZJ":"","GLR":"","GLRDZ":"","GLRFRDB":"","GLRLXDH":"","CBLXDM":"2050100041","STPZH":"201225406641","TZPZDW":"山东省济宁船舶检验局","ZJBH":"1714J002837；","CTCL":"钢质","CYRS":"4","YZCS":"","YZCZRJ":"","GXSJ":"2018-06-16 20:46:41.0","RN":"1"}
	
	2 处理接口接进来的数据，存入mysql数据库;
	
	3 加入分页功能及定时器,实现定时刷新功能、去除重复数据;
	
	4 清理工程;

### 港口企业数据

	接口：
		内网 http://10.100.70.132:8090/services/Ghsjzx?wsdl
		外网 http://202.107.242.115:10090/services/Ghsjzx?wsdl
	1 抽取接口数据
		{"status":1,"message":"请求成功！","data":[{"MAPID":"86e484d13609adee01360f2a31d90002","PBDEPARTID":"20060315094447212164911220","PBDEPARTNAME":"宁波大榭开发区港口局","DCDEPARTID":"20080114150032702963784759","DCDEPARTNAME":"宁波大榭招商国际码头有限公司","DCAPPLYTYPE":"D","DCGOODSSCALE":"","CREATE_TIME":"2012-03-14 11:04:45.0","USER_ID":"dxgkj","UPDATE_TIME":"2012-03-14 11:35:43.0","OPERATOR":"dxgkj","RESERVE1":"10","RESERVE2":"","DISTRICT":"","IS_DELETE":"0","IS_READ":"0","DCAPPLYTYPE_NAME":"散货危险品","SJC":"2016-12-04 20:07:32.0","RN":"1"}],"count":48}
		
	2 处理接口接进来的数据，存入mysql数据库;
	
	3 加入分页功能及定时器,实现定时刷新功能、去除重复数据;
	
	4 清理工程;

### 港口企业基本信息

```
接口：
	内网 http://10.100.70.132:8090/services/Ghsjzx?wsdl
	外网 http://202.107.242.115:10090/services/Ghsjzx?wsdl
1 抽取接口数据
	{"status":1,"message":"请求成功！","data":[{"ZT":"","YWJBXXID":"00000123456789020160722153523302","JBXX_JBRLXDH":"","JBXX_QYLXR":"陈养兴","JBXX_SFZH":"330902195311291521","JBXX_YYZZBH":"330902000001927","JBXX_BZ":"","JBXX_JJDY":"舟山港域马岙港区舟山金能石化有限公司园山燃料油（柴油）库基地码头","JBXX_GKJYXKZYXQ":"2017-05-22 00:00:00.0","JBXX_SFZYGKYW":"1","JBXX_ZCD":"定海区","JBXX_ZCDXXDZ":"干览镇朝阳村龙潭街","JBXX_GKSSJYR":"舟山金能石化有限公司","JBXX_JBR":"","JBXX_QYJJLXFL":"有限责任公司经济","JBXX_QYJJLXZL":"其他有限责任公司","JBXX_ZCZB":"2800","JBXX_CZ":"0580-2263888","JBXX_GKJYXKZHSWSZ":"0288","JBXX_GKJYXKZFZRQ":"2014-05-23 00:00:00.0","JBXX_YX":"412146462@qq.com","JBXX_GSDZ":"舟山市定海干览镇朝阳村龙潭街","JBXX_FR":"张松娣","JBXX_ZCZBDW":"0","JBXX_GKJYXXFW":"1.为船舶提供码头设施 \n2.在港区内提供货物装卸服务；在港区内提供货物仓储服务","JBXX_GDZC":"12701","JBXX_QYLXRDH":"13857211858","JBXX_YB":"316014","JBXX_FRLXDH":"13587057777","JBXX_QYJC":"金能石化","JBXX_SQJYNX":"","JBXX_QYZYLX":"装卸、仓储企业(危货)","JBXX_GKJYXKZH":"（浙舟）港经证（0288）号","JBXX_GKJYFW":"1000|1001|3000|3001|3000|3002|","JBXX_CSGKWXHWZY":"1|","RN":"1"}],"count":3267}
	
2 处理接口接进来的数据，存入mysql数据库;

3 加入分页功能及定时器,实现定时刷新功能、去除重复数据;

4 清理工程。
```

### 人员基础信息

```
接口：
	内网 http://10.100.70.132:8090/services/Ghsjzx?wsdl
	外网 http://202.107.242.115:10090/services/Ghsjzx?wsdl
1 抽取接口数据
	{"status":1,"message":"请求成功！","data":[{"ID":"86e484d136c9bbf80136eca6a9670017","OU":"20091203101029332375706849","MAN_NAME":"吴旭伦","MAN_NUM":"12345","MAN_PHONE_NO":"86750629","MAN_MOBILE_NO":"15888518326","TYPE":"10","STATUS":"N","CREATOR":"renjing","CREATE_TIME":"2012-04-26 11:16:47.0","OPERATOR":"renjing","UPDATE_TIME":"2012-04-26 11:16:47.0","RESERVE1":"wuxulun","RESERVE2":"","ORG_NAME":"宁波大榭关外码头有限公司","IS_DELETE":"0","IS_READ":"0","SJC":"2016-12-06 09:04:28.0","RN":"1"}],"count":839}
	
2 处理接口接进来的数据，存入mysql数据库;

3 加入分页功能及定时器,实现定时刷新功能、去除重复数据;

4 清理工程。
```

### 码头作业信息

```
接口：
	内网 http://10.100.70.132:8090/services/Ghsjzx?wsdl
	外网 http://202.107.242.115:10090/services/Ghsjzx?wsdl

1 抽取接口数据
	{"status":1,"message":"请求成功！","data":[{"APPLIID":"NBDX201200010","LINKMANNM":"胡豪杰","TELEPHONENUMNB":"13958303958","CONSIGNER":"","CONSIGNERTELNB":"","PLANSTARTDATEDT":"2012-04-02 16:01:00.0","PLANWORKTIME":"12","CARRIERDEPARTNM":"南京石油运输有限公司","CARRIERSHIPNM":"宁化424","CARRIERVOYAGENM":"20120402","SHIPCHECKLOADNB":"6551","ARRIVEDATEDT":"2012-04-03 13:01:00.0","SPECIALDEMAND":"","WORKDOCKID":"","SAFETYWORK":"","ANNEX":"","APPLIDEPARTID":"","WORKDOCKBERTHID":"","WORKPORTNM":"韩国蔚山","SHIPWORKTYPEID":"1","AUDITSTATENM":"4","AUDITMANID":"zhouquan","AUDITDATEDT":"2012-04-01 15:07:26.0","AUDITNOTE":"严格按照操作规程操作，并加强现场安全监管。","SENDMANID":"","SENDDATEDT":"2012-04-01 13:04:51.0","ISIN":"","ISAPPEND":"","AUDITDEPARTID":"20060315094447212164911220","WORKMANNM":"6","DEAPPLYNOTE":"","BACKAPPLYNOTE":"","ORG_NAME":"宁波三菱化学有限公司","OU":"20060911125033156162811345","CREATE_TIME":"2012-04-01 13:03:50.0","USER_ID":"yinyahong","UPDATE_TIME":"","OPERATOR":"","RESERVE1":"","RESERVE2":"","HSE_MANAGER":"刘晓帆","HSE_MOBILE":"13732118056","MARINE_AUDIT":"N","IS_ADDITIVE":"N","DISPATCHER":"李文全,(GL)危岗证字第06131,13777777777;","AUDITMANNAME":"周全","RECHECKNOTE":"","CNGOODSNAME":"","WORKPLACE":"5万吨级泊位","WORKPLACEID":"86eee68f3459a535013459f572710002","MMSI":"413358930","APPLIID_CERT_NO":"(CZ)危岗证字第06132","CONSIGNER_ORG_NAME":"","EMERGENCY_SUPPLIES":"干粉灭火器：8KG*10个；\r\n泡沫灭火器：4000L*2个；\r\n移动式消防炮：2个（口径65MM;射程55-50M）；\r\n推车式干粉灭火器：35KG*2,消防隔热服：2套\r\n消防战斗服：2套,围油栏：1000m\r\n吸油毡：1吨\r\n吸油棉：1吨\r\n消油剂：1吨\r\n吸油机：15m3*1台\r\n油拖网：SW-3*2套;","EMERGENCY_RESPONSE":"码头防污事故应急预案\r\n目  录\r\n第一章 总则\r\n第二章 公司简介\r\n第三章 组织结构及职责\r\n第四章 报告程序和内容\r\n第五章 应急反应等级\r\n第六章 应急反应行动\r\n第七章 应急终止\r\n第八章 信息报告和发布\r\n第九章 应急培训和演练\r\n第十章 预案的修改、更新\r\n第十一章 附则\r\n,码头紧急事故应急预案(包括泄漏、溢油应急预案）\r\n\r\n目  录\r\n第一章 总 则\r\n第二章 公司简介\r\n第三章 组织结构及职责\r\n第四章 报告程序和内容\r\n第五章 紧急应变程序 \r\n第六章 应急救援保障\r\n第七章 应急终止\r\n第八章 信息报告和发布\r\n第九章 应急培训和演练\r\n第十章 预案的修改、更新\r\n第十一章 附则;","EMERGENCY_TEAM":"","MARINE_STATUS":"50","DRAUGHT":"6.5","SHIP_LENGTH":"105","SHIP_CERT_NO":"","CERT_VALID_DATE":"","MARINE_APP_NO":"X100995689","MARINE_APP_TIME":"","MARINE_PAPER_FLAG":"N","MESSAGE_FLAG":"","MESSAGE_REFNO":"","NEW_MESSAGE_REFNO":"","REMARK":"","XML_MODIFY_NUM":"","IN_PORT":"","IS_DELETE":"0","IS_READ":"0","SJC":"2016-12-05 17:41:12.0","TSZT_Q":"0","TSZT_H":"0","WORKDOCKNAME":"","APPLIDEPARTNAME":"","WORKDOCKBERTHNAME":"","SENDMANNAME":"","AUDITDEPARTNAME":"","USER_NAME":"","OPERATORNAME":"","REPORT_TYPE":"","RN":"1"}],"count":81026}

2 处理接口接进来的数据，存入mysql数据库;

3 加入分页功能及定时器,实现定时刷新功能、去除重复数据;

4 清理工程。
```

### 危货作业申报单_海事申报记录

```
接口：
	内网 http://10.100.70.132:8090/services/Ghsjzx?wsdl
	外网 http://202.107.242.115:10090/services/Ghsjzx?wsdl

1 抽取接口数据
{"status":1,"message":"请求成功！","data":[{"RECORD_ID":"ff80808163d9318d0163f3c218164e72","APPLIID":"NBDX201802111","MARINE_APP_NO":"","MARINE_AUDIT":"N","CARRIERDEPARTNM":"实华南京油运","CARRIERSHIPNM":"朝阳902","CARRIERVOYAGENM":"1810","SHIPCHECKLOADNB":"10452","ARRIVEDATEDT":"2018-06-14 12:50:00.0","DRAUGHT":"7.3","SHIP_LENGTH":"137.6","SHIP_CERT_NO":"","CERT_VALID_DATE":"","WORKPORTNM":"仪征","MARINE_APP_TIME":"","MARINE_STATUS":"10","CREATOR":"dxzy","CREATE_TIME":"2018-06-12 19:29:33.0","OPERATOR":"","UPDATE_TIME":"2018-06-12 19:29:33.0","RESERVE1":"","RESERVE2":"","FROM_FLAG":"","IS_DELETE":"0","IS_READ":"0","SJC":"2018-06-28 12:00:26.0","RN":"5775"}],"count":134950}

2 处理接口接进来的数据，存入mysql数据库;

3 加入分页功能及定时器,实现定时刷新功能、去除重复数据;

4 清理工程。
```

### 危货作业申报单_危险货物

```
接口：
	内网 http://10.100.70.132:8090/services/Ghsjzx?wsdl
	外网 http://202.107.242.115:10090/services/Ghsjzx?wsdl

1 抽取接口数据
{"status":1,"message":"请求成功！","data":[{"GOODS_ID":"ff8080815ed1dcbf015f328764c15d37","DANGERGOODSCNNM":"氢氧化钠溶液","UNCODE":"1824","TALLYKIND":"散装","AMOUNTNB":"970","CASINGNM":"腐蚀性液体","APPLIID":"NBDX201703780","DANGERGOODSENNM":"","DANGERGOODSID":"1127","DANGERGOODSMAINTYPE":"8","IS_DELETE":"0","IS_READ":"0","SJC":"2018-06-28 11:37:18.0","CONSIGNER_NAME":"","CONSIGNER_ID":"","RN":"1"}],"count":195244}

2 处理接口接进来的数据，存入mysql数据库;

3 加入分页功能及定时器,实现定时刷新功能、去除重复数据;

4 清理工程；
```