package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin;

import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import com.yfkyplatform.parkinglotmiddleware.universal.web.YfkyWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.function.Consumer;

/**
 * 道尔云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class JieShunWebClient extends YfkyWebClient {
    /**
     * 应用私钥
     */
    private static final String PRIVATE_KEY = "<your app secret>";

    /**
     * 开放平台分配的应用id
     */
    private String appId;

    public JieShunWebClient(String baseUrl, int readTimeOutSeconds) {
        super(baseUrl, readTimeOutSeconds);
    }

    /**
     * 加签
     *
     * @param data 需要签名字符串
     * @return 签名结果
     * @throws Exception 签名异常
     */
    private static String sign(String data) throws Exception {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(PRIVATE_KEY);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateK);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    @Override
    protected Consumer<HttpHeaders> httpHeadersFunction(Object data) {
        return (httpHeaders) -> {

        };
    }

    @Override
    protected <T> WebClient.ResponseSpec postBase(T data, String url, Object headerData) {
        JieShunBase jieShunBase = (JieShunBase) data;
        //jieShunBase.setParkId(parkId);
        return super.postBase(jieShunBase, url, headerData);
    }
}
