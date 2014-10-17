package com.baidu.testframework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统计线程，打印信息
 * Created by edwardsbean on 14-10-17.
 */
//每１分钟收集一次结果
class StatTask extends Thread {
    private final static Logger log = LoggerFactory.getLogger(StatTask.class);

    public StatTask(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                FrameworkManager.performanceStat.stat();
            } catch (InterruptedException ex) {
            }
        }
    }
}