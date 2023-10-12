package com.yfkyplatform.parkinglotmiddleware.universal.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * SaaS HTTP客户端(WebFlux)
 *
 * @author Suhuyuan
 */
@Slf4j
public class SaaSWebClient extends ParkingLotWebClient {

    public SaaSWebClient(String saasBaseUrl) {
        super(saasBaseUrl, 30);
    }

    @Override
    protected BiConsumer<Throwable, Object> errContinueFunction() {
        return (Throwable err,Object val) -> {
            if (err instanceof WebClientResponseException) {
                String errResult = ((WebClientResponseException) err).getResponseBodyAsString();
                log.error(errResult);
                throw new RuntimeException("网络错误");
            } else {
                throw new RuntimeException(err.getMessage());
            }
        };
    }

    @Override
    protected Consumer<HttpHeaders> httpHeadersFunction(Object token) {
        return (httpHeaders) -> {
            httpHeaders.add("uctoken", (String) token);
            httpHeaders.add("ucdevice", "pc");
        };
    }

    private Map<String, Object> getData(String resp) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultJson = mapper.readValue(resp, new TypeReference<Map<String, Object>>() {
        });

        if ((int) resultJson.get("code") != 0) {
            throw new RuntimeException("SaaS 接口错误：" + resultJson.get("msg"));
        }

        return (Map<String, Object>) resultJson.get("data");
    }

    public Map<String, Object> post(String data, String url, String token) throws JsonProcessingException {
        String result = postBase(data, url, token)
                .bodyToMono(String.class)
                .onErrorContinue(errContinueFunction())
                .block();

        return getData(result);
    }

    public Map<String, Object> get(String url, String token) throws JsonProcessingException {
        String result = getBase(url, token)
                .bodyToMono(String.class)
                .onErrorContinue(errContinueFunction())
                .block();

        return getData(result);
    }

}
