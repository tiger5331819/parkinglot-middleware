package com.yfkyplatform.parkinglotmiddleware.universal.web;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
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
        SslContextBuilder sslContextBuilder = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE);

        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                .secure(spec->spec.sslContext(sslContextBuilder))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(readTimeOutSeconds)));

        client = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headersConsumer())
                .clientConnector(new ReactorClientHttpConnector(reactor.netty.http.client.HttpClient.from(tcpClient)))
                .build();
    }

    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("TLS");
        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
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
                log.error(err.toString());
                throw new RuntimeException(err.getMessage());
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
