package com.parkinglotmiddleware.universal.testbox;

import org.springframework.core.env.Environment;

/**
 * 修改费用
 *
 * @author Suhuyuan
 */

public class EnvUrlBox {

    private final Environment env;
    private final String prefix = "TestBox.env.";

    public EnvUrlBox(Environment env) {
        this.env = env;
    }

    /**
     * 获取网关环境域名
     *
     * @param environment
     * @return
     */
    public String environmentGateWayURL(String environment) {
        return env.getProperty(prefix + "gateway." + environment, "");
    }

    /**
     * 获取网页环境域名
     *
     * @param environment
     * @return
     */
    public String environmentWebURL(String environment) {
        return env.getProperty(prefix + "web." + environment, "");
    }
}
