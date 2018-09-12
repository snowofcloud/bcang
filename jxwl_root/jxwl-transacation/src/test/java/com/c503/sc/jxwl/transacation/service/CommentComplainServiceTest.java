package com.c503.sc.jxwl.transacation.service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.sc.jxwl.transacation.base.BaseTest;
import com.c503.sc.jxwl.transacation.bean.CommentComplain;
import com.c503.sc.jxwl.transacation.dao.ICommentComplainDao;
import com.c503.sc.jxwl.transacation.service.impl.CommentComplainServiceImpl;

public class CommentComplainServiceTest extends BaseTest {
    @InjectMocks
    @Autowired
    private ICommentComplainService commentComplainService;
    
    @SuppressWarnings("unused")
    @Mock
    @Resource
    private ICommentComplainDao commentComplainDao;
    
    Class<CommentComplainServiceImpl> clazz = CommentComplainServiceImpl.class;
    
    @Before
    public void setup()
        throws Exception {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testFpdate()
        throws Exception {
        CommentComplain arg0 = new CommentComplain();
        arg0.setContent("xxxxxxx");
        arg0.setId("123123123000");
        this.commentComplainService.update(arg0);
    }
    
    @Test
    public void testFindById()
        throws Exception {
        this.commentComplainService.findById("657114589");
    }
    
    @Test
    public void testFindByParams()
        throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("moudle", "0");
        map.put("who", "0");
        this.commentComplainService.findByParams(map);
        
        map.put("moudle", "1");
        map.put("who", "1");
        this.commentComplainService.findByParams(map);
    }
    
    @Test
    public void testFindAvgScore()
        throws Exception {
        this.commentComplainService.findAvgScore("657114589");
    }
    
    @Test
    public void testGetBaseDao()
        throws Exception {
        Method m = clazz.getDeclaredMethod("getBaseDao");
        m.setAccessible(true);
        m.invoke(this.commentComplainService);
    }
    
    @Test
    public void testlogger()
        throws Exception {
        Method m = clazz.getDeclaredMethod("logger");
        m.setAccessible(true);
        m.invoke(this.commentComplainService);
    }
}
