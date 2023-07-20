package com.yfkyplatform.parkinglotmiddleware.universal.web;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 道尔云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public class DrCloudWebClient extends YfkyWebClient {
    /**
     * 令牌名称
     */
    private final String tokenName;

    protected RedisTool redis;

    private boolean refreshToken = false;

    public DrCloudWebClient(String drCloudBaseUrl, RedisTool redisTool) {
        super(drCloudBaseUrl, 30);
        redis = redisTool;
        tokenName = "DRCloudtoken";
    }

    @Override
    protected Consumer<HttpHeaders> httpHeadersFunction(Object data) {
        return (httpHeaders) -> {
            if (!refreshToken && !StrUtil.isBlank(getToken())) {
                httpHeaders.add("Authorization", "bearer " + getToken());
            }
        };
    }

    public Map<String, Object> post(String data, String url) throws JsonProcessingException {
        String result = postBase(data, url, null)
                .bodyToMono(String.class)
                .doOnError(errFunction())
                .block();

        return getData(result);
    }

    private Map<String, Object> getData(String resp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultJson = mapper.readValue(resp, new TypeReference<Map<String, Object>>() {
        });

        if (ObjectUtil.isNull(resultJson)) {
            throw new RuntimeException("道尔云 接口错误：");
        }

        return resultJson;
    }

    private String token() throws JsonProcessingException {
        String result = postBase("", "login/oauth/token?grant_type=password&client_id=SampleClientId&client_secret=123456&username=admin&password=62c1c48636b618319f9414999ccfb420c8fc7943&inputStr=5sse", null)
                .bodyToMono(String.class).doOnError(errFunction()).block();

        Map<String, Object> resultData = getData(result);

        String token = (String) resultData.get("access_token");
        Integer expires = (Integer) resultData.get("expires_in");

        redis.set(tokenName, token, Duration.ofSeconds(expires - 10));
        refreshToken = false;
        return token;
    }

    public String getToken() {
        if (redis.check(tokenName)) {
            return redis.get(tokenName);
        }
        refreshToken = true;
        try {
            return token();
        } catch (Exception ex) {
            throw new RuntimeException("道尔云获取token失败");
        }

    }
}
