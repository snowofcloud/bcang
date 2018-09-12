package com.c503.sc.jxwl.transacation.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.dao.IBayonetDao;
import com.c503.sc.jxwl.transacation.vo.BayOne;
import com.c503.sc.utils.basetools.C503StringUtils;
import com.c503.sc.utils.common.SystemContants;

public class BayOneServiceTest extends BaseTest{
	
	 @InjectMocks
	    @Autowired
	    private IBayOnetService bayOnetService;
	    
	    @Mock
	    @Autowired
	    private IBayonetDao bayonetdao;
	    
	    
	    @Before
	    public void setUp()
	        throws Exception {
	      /* MockitoAnnotations.initMocks(this);*/
	        Map<String, Object> map = new HashMap<>();
	        map.put("bayonetport", "awdf");
	        map.put("updateBy", "wef");
	        map.put("createBy", "sefe");
	        map.put("verifyperson", "esfsf");
	    	map.put("remove", SystemContants.ON);
			map.put("updateTime", new Date());
	        map.put("createTime", new Date());
	        map.put("verifytime", new Date());
	        map.put("orders", "ATFGAS3E");
	        map.put("verifystatus", "150001");
	        map.put("id","ATFGAS3E");
	        this.bayonetdao.save(map);
	    }
	    
	    @Test
	    public void testFindByParams()
	        throws Exception {
	    	 Map<String, Object> map = new HashMap<>();
		        map.put("bayonetport", "awdf");
		        map.put("updateBy", "wef");
		        map.put("createBy", "sefe");
		        map.put("verifyperson", "esfsf");
		    	map.put("remove", SystemContants.ON);
				map.put("updateTime", new Date());
		        map.put("createTime", new Date());
		        map.put("verifytime", new Date());
		        map.put("orders", "ATFGAS3E");
		        map.put("verifystatus", "150001");
		        map.put("id","ATFGAS3E");
	        List<BayOne> list = this.bayOnetService.findByParams(map);
	        Assert.assertEquals(list.size(), 0);
	        
	    }
	    
	    @Test
	    public void testFindById()
	        throws Exception {
	        Map<String, Object> map = new HashMap<>();
	        map.put("orders", "1");
	        map.put("remove",SystemContants.ON );
	        this.bayOnetService.findByOrders(map);
	    }
	    
	    @Test
	    public void testFindByCarName()
	        throws Exception {
	        Map<String, Object> map = new HashMap<>();
	        map.put("carName", "ASEWFEWA");
	        this.bayOnetService.findByCarName(map);
	    }
	    
	    @Test
	    public void testFindRecordById()
	        throws Exception {
	        Map<String, Object> map = new HashMap<>();
	        map.put("id",C503StringUtils.createUUID());
	        map.put("orders", "efseesa");
	        map.put("remove", SystemContants.ON);
	        this.bayOnetService.findRecordById(map);
	    }
	    
	    @Test
	    public void testSave()
	        throws Exception {
	    	Map<String, Object> map = new HashMap<>();
	    	 map.put("bayonetport", "awdf");
		        map.put("updateBy", "wef");
		        map.put("createBy", "sefe");
		        map.put("verifyperson", "esfsf");
		    	map.put("remove", SystemContants.ON);
				map.put("updateTime", new Date());
		        map.put("createTime", new Date());
		        map.put("verifytime", new Date());
		        map.put("orders", "ATFGAS3E");
		        map.put("verifystatus", "150001");
		        map.put("id","ATFGAS3E");
	        this.bayOnetService.save(map);
	        
	        map.put("orders", "13GAsgtaS3E");
	        this.bayOnetService.save(map);
	    }
}
