package com.baidu.testframework;

import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.core.WebPlatTool;
import com.baidu.testframework.tools.Page;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by edwardsbean on 14-10-17.
 */
public class InterfaceDownloader {
    private final static Logger log = LoggerFactory.getLogger(InterfaceDownloader.class.getName());

    public static void main(String[] args) throws Exception {
        log.info("Checking interface jar");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("basic.xml");
        FrameworkConfig frameworkConfig = (FrameworkConfig)  context.getBean("frameworkConfig");
        String serviceName = frameworkConfig.getServiceName();
        Preconditions.checkNotNull(serviceName,"Service name not configured");
        String webToolAddrs = frameworkConfig.getWebToolPlatAddrsCfg();
        Preconditions.checkNotNull(webToolAddrs,"Web tool platform address not configured");
        List<Page> pages = WebPlatTool.getAllService(webToolAddrs);
        String id = WebPlatTool.getServiceId(serviceName,pages);
        WebPlatTool.download(webToolAddrs,id, "lib");
    }
}
