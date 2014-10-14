package com.baidu.testframework.core;

import java.util.List;
import java.util.Map;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class Config {
    private String serviceName;
    //方法名：参数值
    private Map<String,List<String>> methods;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, List<String>> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, List<String>> methods) {
        this.methods = methods;
    }
}
