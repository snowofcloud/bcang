/**
 * 文件名：TrainingServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-13
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.transacation.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.SrcGoods;
import com.c503.sc.jxwl.transacation.bean.SrcGoodsInfo;
import com.c503.sc.jxwl.transacation.dao.ISrcGoodsDao;
import com.c503.sc.utils.basetools.C503StringUtils;

public class GoodsDetailServiceTest extends BaseTest {
	@Autowired
	private IGoodsSourceDetailService goodsSourceDetailService;

	@Autowired
	private ISrcGoodsDao srcGoodsDao;

	SrcGoods srcGood = new SrcGoods();
	SrcGoodsInfo srcGoodsInfo = new SrcGoodsInfo();

	String srcGoodPk = null;

	@Before
	public void setUp() throws Exception {
		srcGood.setId(C503StringUtils.createUUID());
		srcGood.setTradeStatus("110001001");
		
		srcGoodsInfo.setAmount(new BigDecimal(56));
		srcGoodsInfo.setId(C503StringUtils.createUUID());
		srcGoodsInfo.setSrcGoodsId(srcGood.getId());
		srcGoodsInfo.setGoodsName("adfjdt");

//		srcGood.setSrcGoodsInfo(srcGoodsInfo);

		this.srcGoodsDao.save(srcGood);

		srcGoodPk = srcGood.getId();
	}

	@Test
	public void testFindByParams() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userCode","sssss");
		List<SrcGoods> rec = this.goodsSourceDetailService.findByParams(map);
		if (!rec.isEmpty()) {
			Assert.assertNotNull(rec);
		}
	}

	@Test
	public void testFindById() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			this.goodsSourceDetailService.findById(map);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof Exception);
		}

		map.put("id", srcGoodPk);
		this.goodsSourceDetailService.findById(map);
	}

}