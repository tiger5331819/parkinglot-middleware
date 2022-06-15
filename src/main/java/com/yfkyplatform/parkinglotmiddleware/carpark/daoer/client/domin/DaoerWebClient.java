package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.token.DaoerToken;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.token.TokenResult;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Consumer;

/**
 * 道尔云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class DaoerWebClient {
    private WebClient client;
    /**
     * 令牌名称
     */
    private String tokenName;
    /**
     * 应用名称（应用ID）
     */
    private String appName;
    /**
     * 车场ID
     */
    private String parkId;

    protected RedisTool redis;

    public DaoerWebClient(String id,String appName, String parkId, String baseUrl, RedisTool redisTool){
        client= WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
                .build();
        redis= redisTool;
        this.appName=appName;
        this.parkId=parkId;
        tokenName="token:"+id;
    }

    protected  Consumer<? super Throwable> errFunction(){
        return (Throwable err)->{
            if(err instanceof  WebClientResponseException) {
                String errResult=((WebClientResponseException)err).getResponseBodyAsString();
                Throwable ex=new RuntimeException(errResult);
                log.error(ex.getMessage(),ex);
                throw new RuntimeException(errResult);
            }
        };
    }

    private Consumer<HttpHeaders> httpHeadersFunction(){
        return (httpHeaders -> {
            if(!StrUtil.isBlank(getToken())) {
                httpHeaders.add("token",getToken());
            }
        });
    }

    private <T extends DaoerBase> WebClient.ResponseSpec postBase(T data,boolean isNeedToken){
        data.setParkId(parkId);
        WebClient.RequestBodySpec t= client.post()
                .uri(data.getUri());
        if(isNeedToken) {
            t.headers(httpHeadersFunction());
        }

        return t.bodyValue(data)
                .retrieve();
    }

    private <T extends DaoerBase> WebClient.ResponseSpec getBase(T data){
        data.setParkId(parkId);
        return client.get()
                .uri(data.getUri())
                .headers(httpHeadersFunction())
                .retrieve();
    }

    protected  <R,T extends DaoerBase> Mono<R> post(T data, ParameterizedTypeReference<R> result){
        return postBase(data,true)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected  <R,T extends DaoerBase> Mono<R> post(T data, Class<R> result){
        return postBase(data,true)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected  <R,T extends DaoerBase> Mono<R> get(T data, Class<R> result){
        return getBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected  <R,T extends DaoerBase> Mono<R> get(T data, ParameterizedTypeReference<R> result){
        return getBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    private String token(){
        DaoerToken token=new DaoerToken(parkId,appName);
        TokenResult result=postBase(token,false).bodyToMono(TokenResult.class).doOnError(errFunction()).block();
        token.setToken(result.getData());
        redis.set(tokenName,token, Duration.ofSeconds(7199));
        return token.getToken();
    }

    public String getToken(){
        if(redis.check(tokenName)){
            DaoerToken token= redis.get(tokenName);
            return token.getToken();
        }else {
            return token();
        }
    }

    public boolean healthCheck(){
        try{
            String token=token();
            return !StrUtil.isEmpty(token)&&!StrUtil.isBlank(token);
        }catch (Throwable ex){
            return false;
        }
    }
}