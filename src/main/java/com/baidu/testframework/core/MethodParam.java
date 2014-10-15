package com.baidu.testframework.core;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by edwardsbean on 14-10-15.
 */
public class MethodParam {
    private Method method;
    private Object[] methodParam;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(Object[] methodParam) {
        this.methodParam = methodParam;
    }
}
