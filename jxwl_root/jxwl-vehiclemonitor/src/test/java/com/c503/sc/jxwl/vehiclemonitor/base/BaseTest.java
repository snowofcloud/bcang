/*
 * 文件名：BaseTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉Junit测试基类
 * 修改时间：2015年6月11日
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.vehiclemonitor.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring-mvc.xml",
    "classpath*:spring-mybatis.xml"})
public abstract class BaseTest extends
    AbstractTransactionalJUnit4SpringContextTests {
    
}
