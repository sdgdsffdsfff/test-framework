package com.baidu.testframework;

import com.baidu.testframework.config.FrameworkConfig;
import com.baidu.testframework.config.MethodConfig;
import com.baidu.testframework.core.FrameworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by edwardsbean on 14-10-14.
 */
public class FrameworkMain {
    private final static Logger log = LoggerFactory.getLogger(FrameworkMain.class.getName());
    public static ClassPathXmlApplicationContext context;
    public static void main(String[] args) {
        try {
            log.info("Start test framework");
            context = new ClassPathXmlApplicationContext("applicationContext.xml", "basic.xml");
            MethodConfig config = (MethodConfig) context.getBean("config");
            FrameworkConfig frameworkConfig = (FrameworkConfig) context.getBean("frameworkConfig");
            log.info("Checking configuation");
            frameworkConfig.checkConfig();
            log.info("Checking method configuration");
            config.setServiceInterface(frameworkConfig.getTestClazz());
            config.checkConfig();
            FrameworkManager frameworkManager = new FrameworkManager(config, frameworkConfig);
            frameworkManager.initStatistics();
            frameworkManager.startApplication();
            frameworkManager.startReport();
            final FrameworkManager reference = frameworkManager;
            Runtime.getRuntime().addShutdownHook(new Thread("agent-shutdown-hook") {
                @Override
                public void run() {
                    reference.stopApplication();
                }
            });
        } catch (Exception e) {
            log.error("A fatal error occurred while running. Exception follows.",
                    e);
        }
    }


}
