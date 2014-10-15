package com.baidu.testframework.core;

import java.util.*;

/**
 * 随机获取一个待测试的方法
 * Created by edwardsbean on 14-10-15.
 */
public class RandomMethodSelector implements MethodSelector {
    private Random random;

    public RandomMethodSelector() {
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public MethodParam getMethod(Map<String, List<String>> methods) {
        if (methods != null && !methods.isEmpty()) {
            int radomIndex = random.nextInt(methods.size());
            List<String> keys = new ArrayList<String>(methods.keySet());
            String randomKey = keys.get(radomIndex);
            List<String> value = methods.get(randomKey);
            MethodParam methodParam = new MethodParam();
//            methodParam.setMethodName(randomKey);
//            methodParam.setMethodParam(value);
            return methodParam;
        }
        return null;
    }
}
