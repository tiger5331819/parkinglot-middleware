package com.parkinglotmiddleware.universal.testbox;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.core.env.Environment;

/**
 * 修改费用
 *
 * @author Suhuyuan
 */

public class SSLBox {

    private final Environment env;
    private final String prefix = "TestBox.ignoreSSL";

    private final static SslContextBuilder SSL_CONTEXT_BUILDER = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE);

    public SSLBox(Environment env) {
        this.env = env;
    }

    /**
     * 获取忽略证书建造者
     *
     * @return
     */
    public SslContextBuilder getIgnoreSSLBuilder() {
        return env.getProperty(prefix,Boolean.class, false)?SSL_CONTEXT_BUILDER:null;
    }

}
