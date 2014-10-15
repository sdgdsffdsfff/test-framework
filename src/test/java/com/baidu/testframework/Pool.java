package com.baidu.testframework;

/**
 * Created by edwardsbean on 14-10-15.
 */
public class Pool<T> {
    private T obj;

    public Pool(T obj) {
        this.obj = obj;
    }

    public T get(){
        return obj;
    }
}
