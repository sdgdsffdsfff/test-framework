package com.baidu.testframework.example;

/**
 * Created by edwardsbean on 14-10-16.
 */
public class ComplexHello {
    public String sayHello(String message, int time, Person person,Job job) {
        return "hello," + message + ",from "
                + person.getName()
                + "age:" + person.getAge()
                + ",sex:" + person.getSex()
                + ",job:" + job.getName()
                + ",number:" + time;
    }
}
