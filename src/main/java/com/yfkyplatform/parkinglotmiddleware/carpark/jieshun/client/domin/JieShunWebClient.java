package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.yfkyframework.common.core.constant.responsecode.MemberResponseCode;
import com.yfkyframework.common.core.exception.BizException;
import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import com.yfkyplatform.parkinglotmiddleware.universal.web.YfkyWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

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
    private final String privateKey ;

    /**
     * 开放平台分配的应用id
     */
    private final String appId;

    public JieShunWebClient(String appId, String privateKey,String baseUrl, int readTimeOutSeconds) {
        super(baseUrl, readTimeOutSeconds);
        this.privateKey = privateKey;
        this.appId = appId;
    }

    /**
     * 加签
     *
     * @param data 需要签名字符串
     * @return 签名结果
     * @throws Exception 签名异常
     */
    private String sign(String data) throws Exception {

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] keyBytes = decoder.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateK);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());
    }

    @Override
    protected <T> WebClient.ResponseSpec postBase(T data, String url, Object headerData) {
        JieShunBase jieShunBase = (JieShunBase) data;
        jieShunBase.setAppId(appId);

        JSONObject body = JSONObject.parseObject(JSON.toJSONString(jieShunBase), Feature.OrderedField);
        StringBuilder signBuilder = new StringBuilder();
        body.forEach((key, value) -> signBuilder.append("&").append(key).append("=").append(value));
        String substring = signBuilder.substring(1);
        String signStr;
        try{
             signStr=sign(substring);
        }catch (Exception ex){
            throw new BizException(MemberResponseCode.DEFAULT,"捷顺签名异常:"+substring,ex);
        }
        body.put("sign", signStr);

        return super.postBase(body.toJSONString(), url, headerData);
    }
}
