package com.baidu.testframework.example;

/**
 * Created by edwardsbean on 14-10-16.
 */
public enum Sex {
    MAN(1),
    WOMAN(2);
    private final int value;

    private Sex(int value) {
        this.value = value;
    }
}
