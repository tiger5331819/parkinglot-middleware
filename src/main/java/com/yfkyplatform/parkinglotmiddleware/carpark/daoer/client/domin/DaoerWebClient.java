package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.token.DaoerToken;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.token.TokenResult;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.universal.web.YfkyWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.function.Consumer;

/**
 * 道尔云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class DaoerWebClient extends YfkyWebClient {
    /**
     * 令牌名称
     */
    private final String tokenName;
    /**
     * 应用名称（应用ID）
     */
    private final String appName;
    /**
     * 车场ID
     */
    protected final String parkId;

    protected RedisTool redis;

    private boolean refreshToken = false;

    public DaoerWebClient(String id, String appName, String parkId, String baseUrl, RedisTool redisTool) {
        super(baseUrl, 3);
        redis = redisTool;
        this.appName = appName;
        this.parkId = parkId;
        tokenName = "token:" + id;
    }

    @Override
    protected Consumer<HttpHeaders> httpHeadersFunction() {
        return (httpHeaders) -> {
            if (!refreshToken && !StrUtil.isBlank(getToken())) {
                httpHeaders.add("token", getToken());
            }
        };
    }

    @Override
    protected <T> WebClient.ResponseSpec postBase(T data, String url) {
        DaoerBase daoerBase = (DaoerBase) data;
        daoerBase.setParkId(parkId);
        return super.postBase(daoerBase, url);
    }

    @Override
    protected <T> WebClient.ResponseSpec getBase(String url) {
        return super.getBase(url);
    }

    private String token() {
        DaoerToken token = new DaoerToken(appName);
        TokenResult result = postBase(token, "api/index/auth/token").bodyToMono(TokenResult.class).doOnError(errFunction()).block();
        token.setToken(result.getData());
        redis.set(tokenName, token, Duration.ofSeconds(7199));
        refreshToken = false;
        return token.getToken();
    }

    public String getToken(){
        if(redis.check(tokenName)){
            DaoerToken token= redis.get(tokenName);
            return token.getToken();
        }else {
            refreshToken = true;
            return token();
        }
    }
}
