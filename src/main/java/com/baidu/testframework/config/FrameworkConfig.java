package com.baidu.testframework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by edwardsbean on 14-10-14.
 */
@Component("frameworkConfig")
public class FrameworkConfig {
    @Value("${register_server_adds}")
    private String regAddrsCfg;
    private String serviceName;

    public int getRequestDelay() {
        return requestDelay;
    }

    public void setRequestDelay(int requestDelay) {
        this.requestDelay = requestDelay;
    }

    private int failedRequestDelay;
    private int requestDelay;

    public int getFailedRequestDelay() {
        return failedRequestDelay;
    }

    public void setFailedRequestDelay(int failedRequestDelay) {
        this.failedRequestDelay = failedRequestDelay;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getClientServerCount() {
        return clientServerCount;
    }

    public void setClientServerCount(int clientServerCount) {
        this.clientServerCount = clientServerCount;
    }

    public int getClientThreadCount() {
        return clientThreadCount;
    }

    public void setClientThreadCount(int clientThreadCount) {
        this.clientThreadCount = clientThreadCount;
    }

    private int clientServerCount;
    private int clientThreadCount;

    public String getRegAddrsCfg() {
        return regAddrsCfg;
    }



    public void setRegAddrsCfg(String regAddrsCfg) {
        this.regAddrsCfg = regAddrsCfg;
    }
}
