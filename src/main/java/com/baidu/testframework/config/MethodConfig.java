package com.baidu.testframework.config;

import com.baidu.testframework.core.MethodParam;
import com.baidu.testframework.tools.ReflectionUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by edwardsbean on 14-10-14.
 */
@Component
public class MethodConfig {
    private String serviceName;
    //方法名：参数值
    private Map<String, List<String>> methods;
    private boolean paramChecked = false;
    private boolean serviceClassChecked = false;
    private List<MethodParam> reflectMethods;
    private Class clazz;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, List<String>> getMethods() {
        return methods;
    }

    public List<MethodParam> getReflectMethods() {
        if (paramChecked == true) {
            return reflectMethods;
        } else
            return null;

    }

    public Class getServiceClass() {
        if (serviceClassChecked = true)
            return clazz;
        else
            return null;
    }

    public void checkServiceClass() throws ClassNotFoundException {
        clazz = Class.forName(serviceName);
        serviceClassChecked = true;
    }

    public void setMethods(Map<String, List<String>> methods) {
        this.methods = methods;
    }

    /**
     * 检查方法,方法参数是否存在，并保存
     */
    public void checkMethodParam() throws Exception {
        if (serviceClassChecked = true) {
            reflectMethods = ReflectionUtil.getMethodReflection(clazz, methods);
        }
        paramChecked = true;
    }
}
