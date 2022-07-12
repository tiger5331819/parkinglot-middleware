package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.controller.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.LifangParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.controller.tools.request.ViewHttpApiProxy;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 工具控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"基础支持（工具）"})
@RequestMapping(value = "/Lifang/api/{parkingLotId}/tools")
@IgnoreCommonResponse
@RestController
public class LifangToolsController {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final ParkingLotManager manager;

    public LifangToolsController(RestTemplate restTemplate, ObjectMapper mapper, LifangParkingLotManager manager) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.manager = manager;
    }

    private ILifangTool api(String parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    @ApiOperation(value = "AES_Secret")
    @GetMapping(value = "/secret")
    public String getSecret(@PathVariable String parkingLotId) {
        return api(parkingLotId).getSecret();
    }

    @ApiOperation(value = "WebClient 代理")
    @PostMapping("/webclientproxy")
    public Map<String, Object> webClientProxy(@RequestBody ViewHttpApiProxy apiProxy) throws JsonProcessingException, RuntimeException {
        Mono<String> result = WebClient.create().method(HttpMethod.resolve(apiProxy.getMethod()))
                .uri(apiProxy.getUrl())
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue(apiProxy.getData())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(WebClientResponseException.class, err -> {
                    String errResult = err.getResponseBodyAsString();
                    log.error(errResult);
                    throw new RuntimeException(errResult);
                });
        Map<String, Object> resultJson = mapper.readValue(result.block(), new TypeReference<Map<String, Object>>() {
        });
        return resultJson;
    }
}
