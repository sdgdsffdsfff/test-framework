package com.baidu.testframework.core;


import java.util.List;
import java.util.Map;

/**
 * Created by edwardsbean on 14-10-15.
 */
public interface MethodSelector {
    public MethodParam getMethod(Map<String, List<String>> methods);
}
