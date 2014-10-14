package com.baidu.testframework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 性能统计：QPS，RT等 需要两个配合使用的对象
 * 1、被统计对象调用incFailedCount 与 setRespondTime
 * 2、统计线程周期性调用stat()方法
 *
 * @author arksea
 */
public class PerformanceStat {

    public class Property {

        protected long failedCount;
        protected long requestCount;//总请求次数
        protected long responseTimeMax; //最大响应时间
        protected long responseTimeAvg; //平均响应时间，rtAvgSum/rtAvgCount
        protected long QPS;           //每秒请求次数，qpsCount/((now()-qpsStartTime)/1000)

        public long getQPS() {
            return QPS;
        }

        public long getFailedCount() {
            return failedCount;
        }

        public long getRequestCount() {
            return requestCount;
        }

        public long getResponseTimeAvg() {
            return responseTimeAvg;
        }

        public long getResponseTimeMax() {
            return responseTimeMax;
        }

    }

    private static Logger logger = LoggerFactory.getLogger(PerformanceStat.class.getName());
    private long rtAvgCount;    //平均值统计次数
    private long rtAvgSum;      //平均值统计总时间
    private long qpsCount;
    private long newResponseTimeMax; //新周期最大响应时间
    private long newQpsCount;
    private long qpsStartTime;
    private long newQpsStartTime;
    private final Property perf = new Property();
    private final Property queryPerf = new Property(); //读写锁分开
    private long periodSecond = 30;

    public PerformanceStat() {
        qpsStartTime = System.currentTimeMillis();
        newQpsStartTime = System.currentTimeMillis();
    }

    public PerformanceStat(long periodSecond) {
        this.periodSecond = periodSecond;
        qpsStartTime = System.currentTimeMillis();
        newQpsStartTime = System.currentTimeMillis();
    }

    public synchronized void incFailedCount() {
        ++perf.failedCount;
    }

    public synchronized void setRespondTime(long rt) {
        //总请求次数
        ++perf.requestCount;
        ++qpsCount;
        ++newQpsCount;
        //最大响应时间
        if (rt > perf.responseTimeMax) {
            perf.responseTimeMax = rt;
        }
        if (rt > newResponseTimeMax) {
            newResponseTimeMax = rt;
        }
        //平均响应时间
        if ((Long.MAX_VALUE - rt) > rtAvgSum) {
            rtAvgSum = rtAvgSum + rt;
            rtAvgCount = rtAvgCount + 1;
        } else {
            rtAvgSum = rtAvgSum / 2 + rt;
            rtAvgCount = rtAvgCount / 2 + 1;
        }
        //计算QPS的访问次数
    }

    public synchronized void stat() {
        //平均响应时间
        if (rtAvgCount > 0) {
            perf.responseTimeAvg = rtAvgSum / rtAvgCount;
        }
        //每秒请求次数
        long now = System.currentTimeMillis();
        long sec = (now - qpsStartTime) / 1000;
        if (sec > 0) {
            perf.QPS = qpsCount / sec;
        }
        String msg = "QPS=" + perf.getQPS()
                + ", RT-AVG=" + perf.getResponseTimeAvg()
                + ", RT-MAX=" + perf.getResponseTimeMax()
                + ", FailedCount=" + perf.getFailedCount()
                + ", RequestCount=" + perf.getRequestCount();
        if (sec > periodSecond * 2) {
            qpsCount = newQpsCount;
            qpsStartTime = newQpsStartTime;
            perf.responseTimeMax = newResponseTimeMax;
            logger.info(msg);
        } else if (sec > periodSecond) {
            newQpsStartTime = now;
            newQpsCount = 0;
            newResponseTimeMax = 0;
            logger.debug(msg);
        } else {
            logger.debug(msg);
        }
        synchronized (queryPerf) {
            queryPerf.QPS = perf.QPS;
            queryPerf.failedCount = perf.failedCount;
            queryPerf.requestCount = perf.requestCount;
            queryPerf.responseTimeAvg = perf.responseTimeAvg;
            queryPerf.responseTimeMax = perf.responseTimeMax;
        }
    }

    public long getQPS() {
        synchronized (queryPerf) {
            return queryPerf.QPS;
        }
    }

    public long getFailedCount() {
        synchronized (queryPerf) {
            return queryPerf.failedCount;
        }
    }

    public long getRequestCount() {
        synchronized (queryPerf) {
            return queryPerf.requestCount;
        }
    }

    public long getResponseTimeAvg() {
        synchronized (queryPerf) {
            return queryPerf.responseTimeAvg;
        }
    }

    public long getResponseTimeMax() {
        synchronized (queryPerf) {
            return queryPerf.responseTimeMax;
        }
    }

}
