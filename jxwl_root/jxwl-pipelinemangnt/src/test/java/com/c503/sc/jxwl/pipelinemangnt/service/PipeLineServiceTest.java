package com.c503.sc.jxwl.pipelinemangnt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.base.entity.Page;
import com.c503.sc.jxwl.common.constant.NumberContant;
import com.c503.sc.jxwl.pipelinemangnt.base.BaseTest;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineLocationEntity;
import com.c503.sc.jxwl.pipelinemangnt.bean.PipeLineStatusEntity;
import com.c503.sc.jxwl.pipelinemangnt.dao.IPipeLineDao;
import com.c503.sc.utils.common.SystemContants;

public class PipeLineServiceTest extends BaseTest {

	@InjectMocks
	@Autowired
	private IPipeLineService PipeLineService;

	@Mock
	@Autowired
	private IPipeLineDao pipelinedao;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindById() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("pipeid", "1hyrhhawgt");
		map.put("remove", SystemContants.ON);
		PipeLineEntity result = new PipeLineEntity();
		PowerMockito.when(this.pipelinedao.findById(map)).thenReturn(result);
		this.PipeLineService.findById(map);
	}

	@Test
	public void testfindByParams() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("remove", SystemContants.ON);
		map.put("pipeName", "管gf道9");
		map.put("pipeType", "1");
		Page pageEntity = new Page();
		pageEntity.setCurrentPage(1);
		pageEntity.setPageSize(1000);
		map.put("page", pageEntity);
		List<PipeLineEntity> list = new ArrayList<PipeLineEntity>();
		PowerMockito.when(this.pipelinedao.findByParams(map)).thenReturn(list);
		this.PipeLineService.findByParams(map);
	}

	/*@Test
	public void testfindLocationById() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("id", "rseujh6eu45rth");
		map.put("remove", SystemContants.ON);
		Page pageEntity = new Page();
		pageEntity.setCurrentPage(1);
		pageEntity.setPageSize(NumberContant.ONE_THOUSAND);
		map.put("page", pageEntity);
		List<PipeLineLocationEntity> list = new ArrayList<PipeLineLocationEntity>();
		PowerMockito.when(this.pipelinedao.findLocationById(map)).thenReturn(
				list);
		this.PipeLineService.findLocationById(map);
	}*/

	@Test
	public void testfindLiquid() throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put("pipeid", "1");
		map.put("remove", SystemContants.ON);
		Page pageEntity = new Page();
		pageEntity.setCurrentPage(1);
		pageEntity.setPageSize(NumberContant.ONE_THOUSAND);
		map.put("page", pageEntity);
		map.put("pipeType", "1");
		this.PipeLineService.findLiquid(map);
		map.put("pipeType", "2");
		this.PipeLineService.findLiquid(map);
		map.put("pipeType", "3");
		this.PipeLineService.findLiquid(map);
	}

}
