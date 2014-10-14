package com.baidu.testframework;

import com.baidu.testframework.core.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by edwardsbean on 14-10-14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SpringTest {
    @Autowired
    Config config;

    @Test
    public void testGetMethodList() throws Exception {
        System.out.println("get service name:" + config.getServiceName());
        System.out.println("get method and param:");
        for (String key:config.getMethods().keySet()){
            System.out.println("method:" + key);
            for (String param:config.getMethods().get(key)){
                System.out.println("param:" + param);
            }
        }
    }
}
