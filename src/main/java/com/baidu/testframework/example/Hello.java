package com.baidu.testframework.example;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class Hello {
    private String message;
    private int time;

    public String sayHello(String message, int time) {
        return "hello," + message + "," + time;
    }
    public static String sayEnum(String message, SoftSortOption sort) {
        return "hello," + message + "," + sort;
    }
}
