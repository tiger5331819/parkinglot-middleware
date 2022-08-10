package com.yfkyplatform.parkinglotmiddleware.universal.web;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.util.function.Consumer;

/**
 * HTTP客户端(WebFlux)
 *
 * @author Suhuyuan
 */

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
            if (err instanceof WebClientResponseException) {
                String errResult = ((WebClientResponseException) err).getResponseBodyAsString();
                throw new RuntimeException(errResult);
            } else {
                throw new RuntimeException(err.getMessage());
            }
        };
    }

    protected Consumer<HttpHeaders> httpHeadersFunction() {
        return (httpHeaders) -> {
        };
    }

    protected <T extends WebRequestBase> WebClient.ResponseSpec postBase(T data) {
        return client.post()
                .uri(data.getUri())
                .headers(httpHeadersFunction())
                .bodyValue(data)
                .retrieve();
    }

    protected <T extends WebRequestBase> WebClient.ResponseSpec getBase(T data) {
        return client.get()
                .uri(data.getUri())
                .headers(httpHeadersFunction())
                .retrieve();
    }

    protected <R, T extends WebRequestBase> Mono<R> post(T data, ParameterizedTypeReference<R> result) {
        return postBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected <R, T extends WebRequestBase> Mono<R> post(T data, Class<R> result) {
        return postBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected <R, T extends WebRequestBase> Mono<R> get(T data, Class<R> result) {
        return getBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected <R, T extends WebRequestBase> Mono<R> get(T data, ParameterizedTypeReference<R> result) {
        return getBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }
}
