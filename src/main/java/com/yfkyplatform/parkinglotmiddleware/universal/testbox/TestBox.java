package com.yfkyplatform.parkinglotmiddleware.universal.testbox;

import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.universal.web.DrCloudWebClient;
import com.yfkyplatform.parkinglotmiddleware.universal.web.SaaSWebClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 测试盒
 *
 * @author Suhuyuan
 */
@Component
public class TestBox {
    public final Environment env;
    private final String prefix = "TestBox";

    private final ChangeFeeBox changeFeeBox;

    private final EnvUrlBox envUrlBox;

    private final RedisTool redis;

    public TestBox(Environment env, RedisTool redis) {
        this.env = env;
        changeFeeBox = new ChangeFeeBox(env);
        envUrlBox = new EnvUrlBox(env);
        this.redis = redis;
    }

    public ChangeFeeBox changeFee() {
        return changeFeeBox;
    }

    public EnvUrlBox envUrl() {
        return envUrlBox;
    }

    /**
     * 获取测试存储库状态
     *
     * @return
     */
    public Boolean checkTest() {
        return env.getProperty(prefix + ".configRepository", Boolean.class, false);
    }

    /**
     * 获取SaaS Web API client
     *
     * @return
     */
    public SaaSWebClient saasClient(String saas) {
        return new SaaSWebClient(env.getProperty(prefix + ".saas." + saas));
    }

    /**
     * 获取道尔云 Web API client
     *
     * @return
     */
    public DrCloudWebClient drCloudClient() {
        return new DrCloudWebClient(env.getProperty(prefix + ".drcloud"), redis);
    }
}
