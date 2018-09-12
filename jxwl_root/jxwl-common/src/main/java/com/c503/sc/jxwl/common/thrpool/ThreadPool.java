/**
 * 文件名：ThreadPool.java
 * 版权： 航天恒星科技有限公司
 * 描述：〈描述〉
 * 修改时间：2016-11-24
 * 修改内容：〈修改内容〉
 */
package com.c503.sc.jxwl.common.thrpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.c503.sc.base.common.NumberContant;

/**
 * 〈一句话功能简述〉获取线程池
 * 〈功能详细描述〉
 * 
 * @author zz
 * @version [版本号, 2016-11-24]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class ThreadPool {
    /** 线程池 */
    private static ExecutorService threadPool;
    
    /** 私有构造器 */
    private ThreadPool() {
    }
    
    /**
     * 〈一句话功能简述〉方法同步
     * 〈功能详细描述〉
     * 
     * @return ExecutorService
     * @see [类、类#方法、类#成员]
     */
    public static synchronized ExecutorService getThreadPool() {
        if (null == threadPool) {
            threadPool = Executors.newFixedThreadPool(NumberContant.FIVE);
        }
        
        return threadPool;
    }
    
}
