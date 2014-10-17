package com.baidu.testframework.pool;

import com.baidu.dsf.pool.IClientSource;
import com.baidu.dsf.pool.PooledClientSource;
import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.testframework.core.FrameworkManager;
import com.baidu.testframework.core.MethodParam;
import com.baidu.testframework.core.MethodSelector;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 客户端服务器
 * Created by edwardsbean on 14-10-15.
 */
public class ClientServer {
    private final static Logger log = LoggerFactory.getLogger(ClientServer.class.getName());

    private final String name;
    private final IClientSource source;
    private ClientConfigProvider clientConfigProvider;
    private MethodConfig methodConfig;
    private MethodSelector methodSelector;

    public ClientServer(String name,MethodConfig methodConfig, MethodSelector methodSelector,ClientConfigProvider clientConfigProvider) {
        this.methodConfig = methodConfig;
        this.methodSelector = methodSelector;
        this.clientConfigProvider = clientConfigProvider;
        this.name = name;
        GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
        cfg.setMinIdle(2);
        cfg.setMaxIdle(100);
        cfg.setMaxTotal(100);
        cfg.setTestOnBorrow(true);
        cfg.setTestWhileIdle(true);
        source = new PooledClientSource(clientConfigProvider.getServiceName(), cfg, 10000);
    }

    public void run() {
        for (int i = 0; i < clientConfigProvider.getClientThreadCount(); i++) {
            log.info(name + " Create request task " + i);
            RequestTask requestTask = new RequestTask(name + " request " + i);
            requestTask.start();
        }
    }

    //任务请求线程
    class RequestTask extends Thread {
        private final String name;
        public RequestTask(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    request();
                } catch (Throwable ex) {
                    log.error("RPC request error:", ex);
                    FrameworkManager.performanceStat.incFailedCount();
                    try {
                        Thread.sleep(clientConfigProvider.getFailedRequestDelay());
                    } catch (InterruptedException ex1) {
                        log.error("Sleep error:",ex1);
                    }
                }
            }
        }

        private void request() throws Throwable {
            Object client = source.getClient();
            long currentTime = System.currentTimeMillis();
            try {
                /**
                 * RPC请求
                 */
                MethodParam methodParam = methodSelector.getMethod(methodConfig.getReflectMethods());
                Method method = methodParam.getMethod();
                method.invoke(client,methodParam.getMethodParam());
            } finally {
                source.returnClient(client);
            }
            FrameworkManager.performanceStat.setRespondTime(System.currentTimeMillis() - currentTime);
            try {
                Thread.sleep(clientConfigProvider.getRequestDelay());
            } catch (InterruptedException ex1) {
                log.error("Sleep error:",ex1);
            }
        }

    }
}
