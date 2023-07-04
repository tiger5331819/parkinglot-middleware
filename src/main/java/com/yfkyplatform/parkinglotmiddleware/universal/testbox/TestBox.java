package com.yfkyplatform.parkinglotmiddleware.universal.testbox;

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
    private final Environment env;
    private final String prefix = "TestBox";

    private final ChangeFeeBox changeFeeBox;

    private final EnvUrlBox envUrlBox;

    public TestBox(Environment env) {
        this.env = env;
        changeFeeBox = new ChangeFeeBox(env);
        envUrlBox = new EnvUrlBox(env);
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
}
