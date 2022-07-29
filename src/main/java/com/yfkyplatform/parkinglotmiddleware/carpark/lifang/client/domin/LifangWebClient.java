package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model.LifangBase;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.function.Consumer;

/**
 * 道尔云WebClient
 *
 * @author Suhuyuan
 */
@Slf4j
public abstract class LifangWebClient {
    private final WebClient client;

    protected RedisTool redis;

    protected String secret;

    public LifangWebClient(String secret, String baseUrl, RedisTool redisTool) {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(3)));

        client = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeaders(httpHeaders -> httpHeaders.setContentType(MediaType.APPLICATION_JSON))
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
        redis = redisTool;
        this.secret = secret;
    }

    protected Consumer<? super Throwable> errFunction() {
        return (Throwable err) -> {
            if (err instanceof WebClientResponseException) {
                String errResult = ((WebClientResponseException) err).getResponseBodyAsString();
                throw new RuntimeException(errResult);
            }
        };
    }

    private <T extends LifangBase> WebClient.ResponseSpec postBase(T data) {
        WebClient.RequestBodySpec t = client.post()
                .uri(data.getUri());

        return t.bodyValue(data)
                .retrieve();
    }

    private <T extends LifangBase> WebClient.ResponseSpec getBase(T data) {
        return client.get()
                .uri(data.getUri())
                .retrieve();
    }

    protected <R, T extends LifangBase> Mono<R> post(T data, ParameterizedTypeReference<R> result) {
        return postBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }

    protected <R, T extends LifangBase> Mono<R> post(T data, Class<R> result) {
        return postBase(data)
                .bodyToMono(result)
                .doOnError(errFunction());
    }
}
