package com.yfkyplatform.parkinglotmiddleware.universal.web;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.NettyDataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * HTTP客户端(WebFlux)
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class ParkingLotWebClient {
    private final WebClient client;
    private final static SslContextBuilder sslContextBuilder = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE);

    public ParkingLotWebClient(String baseUrl, int timeOutSeconds) {



        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client -> client
                        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                        .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(timeOutSeconds))
                                .addHandlerLast(new WriteTimeoutHandler(timeOutSeconds)))
                ).secure(spec -> spec.sslContext(sslContextBuilder));

        client = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headersConsumer())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    protected Consumer<HttpHeaders> headersConsumer() {
        return (HttpHeaders httpHeaders) -> httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    protected BiConsumer<Throwable, Object> errContinueFunction() {
        return (Throwable err,Object val) -> {
            String body = null;
            if(val instanceof NettyDataBuffer){
                NettyDataBuffer nettyDataBuffer=((NettyDataBuffer)val);
                body =nettyDataBuffer.toString(CharsetUtil.UTF_8);
                nettyDataBuffer.release();
            }
            if(StrUtil.isNotBlank(body)){
                log.error("远端车场链接异常。异常包信息："+body+"\n错误信息:"+ err);
            }
        };
    }

    protected Consumer<? super Throwable> errFunction() {
        return (Throwable err) -> {
            if (err instanceof WebClientResponseException) {
                String body = ((WebClientResponseException) err).getResponseBodyAsString();
                log.error("远端车场链接异常。异常包信息："+body+"\n错误信息:"+ err);
                throw new ParkingLotConnectException(err);
            } else{
                throw new ParkingLotConnectException(err);
            }
        };
    }

    protected Consumer<HttpHeaders> httpHeadersFunction(Object data) {
        return (httpHeaders) -> {
        };
    }

    protected <T> WebClient.ResponseSpec postBase(T data, String url, Object headerData) {
        return client.post()
                .uri(url)
                .headers(httpHeadersFunction(headerData))
                .bodyValue(data)
                .retrieve();
    }

    protected WebClient.ResponseSpec getBase(String url, Object headerData) {
        return client.get()
                .uri(url)
                .headers(httpHeadersFunction(headerData))
                .retrieve();
    }

    protected <R, T> Mono<R> post(T data, String url, ParameterizedTypeReference<R> result) {
        return postBase(data, url, null)
                .bodyToMono(result)
                .onErrorContinue(errContinueFunction());
    }

    protected <R, T> Mono<R> post(T data, String url, Class<R> result) {

        return postBase(data, url, null)
                .bodyToMono(result)
                .onErrorContinue(errContinueFunction())
                .doOnError(errFunction());
    }

    protected <R> Mono<R> get(String url, Class<R> result) {
        return getBase(url, null)
                .bodyToMono(result)
                .onErrorContinue(errContinueFunction())
                .doOnError(errFunction());
    }

    protected <R> Mono<R> get(String url, ParameterizedTypeReference<R> result) {
        return getBase(url, null)
                .bodyToMono(result)
                .onErrorContinue(errContinueFunction())
                .doOnError(errFunction());
    }
}
