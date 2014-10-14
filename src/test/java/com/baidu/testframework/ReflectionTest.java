package com.baidu.testframework;

import org.apache.thrift.TServiceClient;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by edwardsbean on 14-10-14.
 */
public class ReflectionTest {
    Class demo;
    @Value("${service.interface}")
    String serviceName;

    @Before
    public void setUp() throws Exception {

        demo = Class.forName("com.baidu.test.Hello");

    }

    @Test
    public void testThriftInstance() throws Exception {
        TSocket sock = new TSocket("localhost", 4000, 10000);
        TTransport transport = new TFramedTransport(sock);
        transport.open();
        TProtocol protocol = new TBinaryProtocol(transport);
        String className = serviceName + "$Client";
        Class<?> clientClass = Class.forName(className);
        Constructor con = clientClass.getConstructor(TProtocol.class);
        //得到rpc客户端
        TServiceClient client = (TServiceClient) con.newInstance(protocol);
        //调用rpc方法
        Method method = clientClass.getMethod("ping");
        method.invoke(client);
    }


    //带有参数的方法调用
    @Test
    public void testGetMethod() throws Exception {
        Method method = demo.getMethod("sayHello",String.class,int.class);
        String s = (String)method.invoke(demo.newInstance(),"world",3);
        System.out.println(s);
    }
    //获取方法参数
    @Test
    public void testGetParam() throws Exception {
        Method method = demo.getMethod("sayHello",String.class,int.class);
        Class<?> para[] = method.getParameterTypes();
        for (int j = 0; j < para.length; ++j) {
            System.out.print(para[j].getName() + " " + "arg" + j);
            if (j < para.length - 1) {
                System.out.print(",");
            }
        }
    }
    //获得所有方法
    @Test
    public void testGetAllMethod() throws Exception {
        Method method[] = demo.getMethods();
        for (int i = 0; i < method.length; ++i) {
            Class<?> returnType = method[i].getReturnType();
            Class<?> para[] = method[i].getParameterTypes();
            int temp = method[i].getModifiers();
            System.out.print(Modifier.toString(temp) + " ");
            System.out.print(returnType.getName() + "  ");
            System.out.print(method[i].getName() + " ");
            System.out.print("(");
            for (int j = 0; j < para.length; ++j) {
                System.out.print(para[j].getName() + " " + "arg" + j);
                if (j < para.length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println(")");
        }
    }

    @Test
    public void testGetClassType() throws Exception {
        demo.getComponentType();
    }

    @Test
    public void testNewInstance() throws Exception {
    }

    @Test
    public void testFindMethod() throws Exception {
        Method method[] = demo.getMethods();
        Method sayEnum = null;
        for (int i = 0; i < method.length; ++i) {
            if(method[i].getName().equals("sayEnum"))
                sayEnum = method[i];
        }
        System.out.println(sayEnum.getName());
    }

    /**
     * 获取方法中的Enum参数，并实例化
     * @throws Exception
     */
    @Test
    public void testNewEnumParam() throws Exception {
        Method method[] = demo.getMethods();
        Method sayEnum = null;
        for (int i = 0; i < method.length; ++i) {
            if(method[i].getName().equals("sayEnum"))
                sayEnum = method[i];
        }
        Class para[] = sayEnum.getParameterTypes();
        Class enumClass = para[1];
        System.out.println("get Enum param class:" + enumClass);
        System.out.println("get Enum value:" + Enum.valueOf(enumClass,"f_idASC"));
        System.out.println("invoke method:" + sayEnum.invoke(demo.newInstance(),"a",Enum.valueOf(enumClass,"f_idASC")));
    }


}
