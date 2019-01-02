package com.c503.hthj.asoco.dangerchemical.waste.base;

import java.util.concurrent.ThreadFactory;

/**
 * @auther xuxq
 * @date 2019/1/2 22:34
 */
public class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    }
    //看下ThreadFactory接口的定义
    //从注释我们可以清楚的了解到，我们可以实现ThreadFactory，为自己的线程定义优先级、daemon状态等等，
    // 我们通过工厂就可以获取自定义的线程实现类，可以将其传递给Executors.newCachedThreadPool()。
}
