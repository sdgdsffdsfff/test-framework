//package com.baidu.testframework.core;
//
//import com.baidu.dsf.DSFramework;
//import com.baidu.dsf.adaptor.ServiceAdaptor;
//import com.baidu.dsf.pool.PooledClientSource;
//import com.baidu.testframework.SoftSortOption;
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.util.Map;
//
///**
// *
// * @author nelson
// */
//public class StressTest implements ITest {
//    private static Logger logger = LoggerFactory.getLogger(StressTest.class.getName());
//    private String serviceName;
//    private int clientServerCount;
//    private int clientThreadCount;
//    private int requestDelay;
//    private int failedRequestDelay;
//    private String testStrategy;
//    private String[] testMethods;
//    private PerformanceStat performanceStat;
//    private Map<String, Object[]> parameterData = new HashMap<>();
//    private int defaultDisplayNum = 15;
//    @Autowired
//    private Config config;
//    List<DeviceInfo> deviceInfoList;
//    private static SoftSortOption[] sortArray = {
//            SoftSortOption.f_idASC,SoftSortOption.f_idDESC,
//            SoftSortOption.f_update_timeDESC,SoftSortOption.f_update_timeASC,
//    };
//
//    //初始化参数包括读取配置文件和构造测试数据，构造测试参数
//    @Override
//    public void init() {
//        //读取配置参数
//        performanceStat = new PerformanceStat(10);
//        FileInputStream fis = null;
//        try {
//            InetSocketAddress[] addrs = parseSocketAddrArray(config.getRegAddrsCfg());
//            DSFramework.start(addrs, "softquery", 200);
//            ServiceAdaptor.subscribeService(config.getServiceName());
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            logger.error("初始化出错", ex);
//        } finally {
//            try {
//                if (fis != null) {
//                    fis.close();
//                }
//            } catch (IOException ex) {
//            }
//        }
//        //构造输入测试数据参数
//        deviceInfoList = new ArrayList<>();
//        DeviceInfo deviceInfo = new DeviceInfo();
//        deviceInfo.Fwversion = FwVersionOption.Android;
//        deviceInfo.Platform = MobileOption.Android;
//        deviceInfo.pad = PadOption.Phone;
//        deviceInfoList.add(deviceInfo);
//        deviceInfo = new DeviceInfo();
//        deviceInfo.Fwversion = FwVersionOption.Android;
//        deviceInfo.Platform = MobileOption.Android;
//        deviceInfo.pad = PadOption.Pad;
//        deviceInfoList.add(deviceInfo);
//        deviceInfo = new DeviceInfo();
//        deviceInfo.Fwversion = FwVersionOption.iPhone;
//        deviceInfo.Platform = MobileOption.iPhone;
//        deviceInfo.pad = PadOption.Phone;
//        deviceInfoList.add(deviceInfo);
//        deviceInfo = new DeviceInfo();
//        deviceInfo.Fwversion = FwVersionOption.iPhone;
//        deviceInfo.Platform = MobileOption.iPhone;
//        deviceInfo.pad = PadOption.Pad;
//        deviceInfoList.add(deviceInfo);
//
//
//    }
//
//    private  InetSocketAddress[] parseSocketAddrArray(String cfgStr) {
//        String[] cfgList = cfgStr.split(";");
//        int count = cfgList.length;
//        InetSocketAddress[] addrs = new InetSocketAddress[count];
//        for (int i = 0; i < count; ++i) {
//            String cfg = cfgList[i];
//            String[] strs = cfg.split(":");
//            String host = strs[0];
//            int port = Integer.parseInt(strs[1]);
//            addrs[i] = new InetSocketAddress(host, port);
//        }
//        return addrs;
//    }
//
//    //开始测试
//    @Override
//    public void test() {
//        for (int index = 0; index < clientServerCount; index++) {
//            ClientServer clientServer = new ClientServer("clientServer" + index);
//            clientServer.test();
//        }
//    }
//
//    //统计输出结果
//    @Override
//    public void printResult() {
//        StatTask statTask = new StatTask("统计结果");
//        statTask.start();
//    }
//
//    //客户端服务器
//    class ClientServer {
//
//        private final String name;
//        private final IClientSource<SoftQuery.Iface> source;
//        public ClientServer(String name) {
//            this.name = name;
//            GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
//            cfg.setMinIdle(2);
//            cfg.setMaxIdle(100);
//            cfg.setMaxTotal(100);
//            cfg.setTestOnBorrow(true);
//            cfg.setTestWhileIdle(true);
//            source = new PooledClientSource(serviceName, cfg, 10000);
//        }
//
//        public void test() {
//            for (int i = 0; i < clientThreadCount; i++) {
//                RequestTask requestTask = new RequestTask(name + " request " + i, source);
//                requestTask.start();
//            }
//        }
//    }
//
//    //任务请求线程
//    class RequestTask extends Thread {
//
//        private final String name;
//        private final IClientSource<SoftQuery.Iface> source;
//
//        public RequestTask(String name, IClientSource<SoftQuery.Iface> source) {
//            this.name = name;
//            this.source = source;
//        }
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    request();
//                } catch (Throwable ex) {
//                    logger.error("请求出错:", ex);
//                    performanceStat.incFailedCount();
//                    try {
//                        Thread.sleep(failedRequestDelay);
//                    } catch (InterruptedException ex1) {
//                    }
//                }
//            }
//        }
//
//        private void request() throws Throwable {
//            SoftQuery.Iface client = source.getClient();
//            long currentTime = System.currentTimeMillis();
//            try {
//                Random random = new Random();
//                int sortValue = random.nextInt(6);
//                //  System.out.println("sortValue=" + sortValue);
//                SoftSortOption sortType = sortArray[sortValue];
//                // int deviceIndex = random.nextInt(3);
//                int deviceIndex = 1;
//                DeviceInfo deviceInfo = deviceInfoList.get(deviceIndex);
//                TinySoftPage tinySoftPage = client.GetTinySofts(sortType, defaultDisplayNum, deviceInfo);
//                // logger.info("test continue");
//            } finally {
//                source.returnClient(client);
//            }
//            performanceStat.setRespondTime(System.currentTimeMillis() - currentTime);
//            try {
//                Thread.sleep(requestDelay);
//            } catch (InterruptedException ex1) {
//            }
//        }
//
//    }
//
//    //每１分钟收集一次结果
//    class StatTask extends Thread {
//
//        public StatTask(String name) {
//            super(name);
//        }
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    Thread.sleep(60000);
//                    performanceStat.stat();
//                } catch (InterruptedException ex) {
//                }
//            }
//        }
//    }
//
//}
