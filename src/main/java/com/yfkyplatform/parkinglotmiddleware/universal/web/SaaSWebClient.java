package com.yfkyplatform.parkinglotmiddleware.universal.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.tcp.TcpClient;

import java.util.Map;
import java.util.function.Consumer;

/**
 * SaaS HTTP客户端(WebFlux)
 *
 * @author Suhuyuan
 */
@Slf4j
public class SaaSWebClient {
    private final WebClient client;

    public SaaSWebClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(30)));

        client = WebClient
                .builder()
                .baseUrl("https://mgnt-pc.q-parking.com/api/")
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
                log.error(errResult);
                throw new RuntimeException("网络错误");
            } else {
                throw new RuntimeException(err.getMessage());
            }
        };
    }

    protected Consumer<HttpHeaders> httpHeadersFunction(String token) {
        return (httpHeaders) -> {
            httpHeaders.add("Authorization", token);
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        };
    }

    protected WebClient.ResponseSpec postBase(String data, String url, String token) {
        return client.post()
                .uri(url)
                .headers(httpHeadersFunction(token))
                .bodyValue(data)
                .retrieve();
    }

    protected WebClient.ResponseSpec getBase(String url, String token) {
        return client.get()
                .uri(url)
                .headers(httpHeadersFunction(token))
                .retrieve();
    }

    private Map<String, Object> getData(String resp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultJson = mapper.readValue(resp, new TypeReference<Map<String, Object>>() {
        });

        return (Map<String, Object>) resultJson.get("data");
    }

    public Map<String, Object> post(String data, String url, String token) throws JsonProcessingException {
        String result = postBase(data, url, token)
                .bodyToMono(String.class)
                .doOnError(errFunction())
                .block();

        return getData(result);
    }

    public Map<String, Object> get(String url, String token) throws JsonProcessingException {
        String result = getBase(url, token)
                .bodyToMono(String.class)
                .doOnError(errFunction())
                .block();

        return getData(result);
    }

}
