package com.baidu.testframework.tools;

import com.baidu.testframework.example.Person;
import com.baidu.testframework.example.Sex;
import org.junit.Test;

public class ReflectionUtilTest {
    Class clazz = Person.class;
    String paramLine = "{name:edwardsbean,age:23,sex:MAN,job:{name:码农}}";

    @Test
    public void testNewInstance() throws Exception {
        Person person = (Person)ReflectionUtil.newInstance(clazz,paramLine);
        assert person.getName().equals("edwardsbean");
        assert person.getAge().equals("23");
        assert person.getSex().equals(Sex.MAN);
        assert person.getJob().getName().equals("码农");

    }
}