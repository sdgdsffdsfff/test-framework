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

    public String getRegAddrsCfg() {
        return regAddrsCfg;
    }

    public void setRegAddrsCfg(String regAddrsCfg) {
        this.regAddrsCfg = regAddrsCfg;
    }
}
