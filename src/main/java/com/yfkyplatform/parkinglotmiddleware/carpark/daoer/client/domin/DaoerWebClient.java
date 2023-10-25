package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.token.DaoerToken;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.token.TokenResult;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import com.yfkyplatform.parkinglotmiddleware.universal.web.ParkingLotWebClient;
import io.netty.handler.ssl.SslContextBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 道尔云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class DaoerWebClient extends ParkingLotWebClient {
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

    /**
     * 图片地址
     */
    protected final String imgUrl;

    protected RedisTool redis;

    private boolean refreshToken = false;

    private final TestBox testBox;


    public DaoerWebClient(String id, String appName, String parkId, String baseUrl, String imgUrl, RedisTool redisTool, TestBox testBox, int readTimeOutSeconds) {
        super(baseUrl, readTimeOutSeconds);
        redis = redisTool;
        this.appName = appName;
        this.parkId = parkId;
        this.imgUrl = imgUrl;
        tokenName = "token:" + id;
        this.testBox=testBox;
    }

    @Override
    protected Function<HttpClient, HttpClient> bootstrap() {
        return (httpClient -> {
            SslContextBuilder sslContextBuilder=this.testBox.ssl().getIgnoreSSLBuilder();
            return ObjectUtil.isNotNull(sslContextBuilder)?httpClient.secure(spec -> spec.sslContext(sslContextBuilder)):httpClient;
        });
    }

    @Override
    protected Consumer<HttpHeaders> httpHeadersFunction(Object data) {
        return (httpHeaders) -> {
            if (!refreshToken && !StrUtil.isBlank(getToken())) {
                httpHeaders.add("token", getToken());
            }
        };
    }

    @Override
    protected <T> WebClient.ResponseSpec postBase(T data, String url, Object headerData) {
        DaoerBase daoerBase = (DaoerBase) data;
        daoerBase.setParkId(parkId);
        return super.postBase(daoerBase, url, headerData);
    }

    private String token() {
        DaoerToken token = new DaoerToken(appName);
        TokenResult result;
        int retryCount = 0;
        do {
            result = postBase(token, "api/index/auth/token", null)
                    .bodyToMono(TokenResult.class)
                    .onErrorContinue(errContinueFunction())
                    .doOnError(errFunction())
                    .block();
            retryCount++;
        } while (StrUtil.isBlank(result.getData()) && retryCount < 3);
        token.setToken(result.getData());
        redis.set(tokenName, token, Duration.ofSeconds(7198));
        refreshToken = false;
        return token.getToken();
    }

    private boolean checkToken(DaoerToken token) {
        if (StrUtil.isNotBlank(token.getToken())) {
            return StrUtil.equals(token.getAppName(), appName) && StrUtil.equals(token.getParkId(), parkId);
        }
        return false;
    }

    public String getToken() {
        if (redis.check(tokenName)) {
            DaoerToken token = redis.get(tokenName);
            if (checkToken(token)) {
                return token.getToken();
            }
        }
        refreshToken = true;
        return token();
    }
}
