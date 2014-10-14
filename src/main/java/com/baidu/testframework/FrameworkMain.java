package com.baidu.testframework;

import com.baidu.testframework.core.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class FrameworkMain {
    private final static Logger log = LoggerFactory.getLogger(FrameworkMain.class.getName());

    public static void main(String[] args) {
        log.info("Start test framework");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Config config = (Config)context.getBean("config");
    }
}
