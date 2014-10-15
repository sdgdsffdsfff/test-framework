package com.baidu.testframework.config;

import com.baidu.testframework.core.MethodParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
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
    public Class getServiceClass(){
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
    public void checkMethodParam() throws Exception{
        /**
         * TODO 检查
         */
        if (serviceClassChecked = true) {
            Method method[] = clazz.getMethods();
            for (int i = 0; i < method.length; ++i) {
                String methodName = method[i].getName();
                List<String> paramList = methods.get(methodName);
                //获取待测试方法的method反射
                if(paramList != null){
                    /**
                     * 检查方法类型，并实例化
                     */
                    Class<?> paramClassList[] = method[i].getParameterTypes();
                    Object paramInstances[] = new Object[paramList.size()];
                    int paramIndex = 0;
                    for (Class paramClass: paramClassList) {
                        String paramValue = paramList.get(paramIndex);
                        //原始类型，如int
                        if(paramClass.isPrimitive()){
                           paramInstances[paramIndex] = Integer.class.getConstructor(String.class).newInstance(paramValue);
                        //基本类型，如String
                        }else if(isBasicType(paramClass)) {
                            paramInstances[paramIndex] = paramClass.getConstructor(String.class).newInstance(paramValue);
                        }else{
                            paramValue
                        }
                        paramIndex++;
                    }
                    MethodParam methodParam = new MethodParam();
                    methodParam.setMethod(method[i]);
                    methodParam.setMethodParam(paramInstances);
                    reflectMethods.add(methodParam);
                }
            }
        }
        paramChecked = true;
    }

    /**
     * TODO 更多基本类型？
     * @param clazz
     * @return
     */
    private boolean isBasicType(Class clazz){
        if (clazz.getSimpleName().equals("String")){
            return true;
        }
        return false;
    }

    private Object parse(Class paramClass,String value) throws Exception{
        Object instance;
        //原始类型，如int
        if(paramClass.isPrimitive()){
            instance = Integer.class.getConstructor(String.class).newInstance(value);
            //基本类型，如String
        }else if(isBasicType(paramClass)) {
            instance = paramClass.getConstructor(String.class).newInstance(value);
        }else{
            paramValue
        }
        return instance;
    }
}
