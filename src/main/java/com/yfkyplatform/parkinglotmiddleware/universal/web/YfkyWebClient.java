package com.yfkyplatform.parkinglotmiddleware.universal.web;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.util.function.Consumer;

/**
 * HTTP客户端(WebFlux)
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class YfkyWebClient {
    private final WebClient client;

    public YfkyWebClient(String baseUrl, int readTimeOutSeconds) {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(readTimeOutSeconds)));

        client = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headersConsumer())
                .clientConnector(new ReactorClientHttpConnector(reactor.netty.http.client.HttpClient.from(tcpClient)))
                .build();
    }

    protected Consumer<HttpHeaders> headersConsumer() {
        return (HttpHeaders httpHeaders) -> httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    protected Consumer<? super Throwable> errFunction() {
        return (Throwable err) -> {
            log.error("远端车场链接异常:"+err.toString());
            throw new RuntimeException("远端车场链接异常");
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
                .doOnError(errFunction());
    }

    protected <R, T> Mono<R> post(T data, String url, Class<R> result) {
        return postBase(data, url, null)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected <R> Mono<R> get(String url, Class<R> result) {
        return getBase(url, null)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected <R> Mono<R> get(String url, ParameterizedTypeReference<R> result) {
        return getBase(url, null)
                .bodyToMono(result)
                .doOnError(errFunction());
    }
}
