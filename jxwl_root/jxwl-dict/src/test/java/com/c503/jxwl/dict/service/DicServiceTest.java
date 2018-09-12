/**
 * 文件名：DicServiceTest.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-7-15
 * 修改内容：〈修改内容〉
 */
package com.c503.jxwl.dict.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.c503.jxwl.dict.base.BaseTest;
import com.c503.sc.dict.service.IDictionaryService;

@Ignore
public class DicServiceTest extends BaseTest {
    
    @Autowired
    private IDictionaryService dictionaryService;
    
    @Test
    public void test() {
        
    }
    
    @Test
    public void testFindDictByCode()
        throws Exception {
        this.dictionaryService.findDictByCode("1");
        this.dictionaryService.findDictByCode("2");
    }
    
    @Test
    public void testFindFromDB()
        throws Exception {
        this.dictionaryService.findDictFromDB("100");
        this.dictionaryService.findDictFromDB("100001");
        this.dictionaryService.findDictFromDB("100001001");
    }
}
