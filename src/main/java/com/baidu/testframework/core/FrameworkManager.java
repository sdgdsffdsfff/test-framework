package com.baidu.testframework.core;


import com.baidu.dsf.DSFramework;
import com.baidu.dsf.adaptor.ServiceAdaptor;
import com.baidu.testframework.config.ClientConfigProvider;
import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.testframework.pool.ClientServer;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class FrameworkManager {
    private final static Logger log = LoggerFactory.getLogger(FrameworkManager.class.getName());
    private MethodConfig methodConfig;
    private FrameworkConfig frameworkConfig;
    private ClientConfigProvider clientConfigProvider;
    public static final MetricRegistry metricRegistry = new MetricRegistry();
    public static final Timer timer = metricRegistry.timer(MetricRegistry.name(FrameworkManager.class,"framework-calculation"));

    public FrameworkManager(MethodConfig methodConfig, FrameworkConfig frameworkConfig) {
        this.methodConfig = methodConfig;
        this.frameworkConfig = frameworkConfig;
        this.clientConfigProvider = new ClientConfigProvider();
        clientConfigProvider.setServiceName(frameworkConfig.getServiceName());
        clientConfigProvider.setClientThreadCount(frameworkConfig.getClientThreadCount());
        clientConfigProvider.setFailedRequestDelay(frameworkConfig.getFailedRequestDelay());
        clientConfigProvider.setRequestDelay(frameworkConfig.getRequestDelay());
    }

    public void startApplication() {
        init();
        MethodSelector methodSelector = new RandomMethodSelector();
        for (int index = 0; index < frameworkConfig.getClientServerCount(); index++) {
            log.info("Create clientServer" + index);
            ClientServer clientServer = new ClientServer("clientServer" + index,methodConfig,methodSelector,clientConfigProvider);
            clientServer.run();
        }
    }

    public void stopApplication() {
        /**
         * TODO
         */
    }

    public void init() {
        InetSocketAddress[] addrs = parseSocketAddrArray(frameworkConfig.getRegAddrsCfg());
        DSFramework.start(addrs, "test-framework", 200);
        ServiceAdaptor.subscribeService(frameworkConfig.getServiceName());
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error("Sleep exception:", e);
        }
    }

    public void startReport() {
        //注册metrics,每个1秒打印metrics到控制台
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(1, TimeUnit.SECONDS);
    }

    private InetSocketAddress[] parseSocketAddrArray(String cfgStr) {
        String[] cfgList = cfgStr.split(";");
        int count = cfgList.length;
        InetSocketAddress[] addrs = new InetSocketAddress[count];
        for (int i = 0; i < count; ++i) {
            String cfg = cfgList[i];
            String[] strs = cfg.split(":");
            String host = strs[0];
            int port = Integer.parseInt(strs[1]);
            addrs[i] = new InetSocketAddress(host, port);
        }
        return addrs;
    }


}
