package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.request.ViewHttpApiProxy;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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
@RequestMapping(value = "/Daoer/api/{parkingLotId}/tools")
@IgnoreCommonResponse
@RestController
public class DaoerToolsController {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final ParkingLotManager manager;

    private IDaoerTool api(Long parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    public DaoerToolsController(RestTemplate restTemplate, ObjectMapper mapper, DaoerParkingLotManager manager) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.manager = manager;
    }

    @ApiOperation(value = "图片")
    @GetMapping(value = "/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImg(@PathVariable Long parkingLotId, String imgPath) {
        return api(parkingLotId).getImage(imgPath).block();
    }

    @ApiOperation(value = "Access_Token")
    @GetMapping(value = "/token")
    public DaoerBaseResp<String> getToken(@PathVariable Long parkingLotId) {
        DaoerBaseResp<String> result = new DaoerBaseResp<String>();
        result.setBody(api(parkingLotId).getToken());
        return result;
    }

    @ApiOperation(value = "RestTemplate 代理")
    @PostMapping("/resttemplateproxy")
    public Map<String,Object> restTemplateProxy(@RequestBody ViewHttpApiProxy apiProxy) throws JsonProcessingException {
        HttpHeaders headers=new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        if(!apiProxy.getToken().isEmpty()) {
            headers.add("token",apiProxy.getToken());
        }

        HttpEntity<String> entity=new HttpEntity<String>(apiProxy.getData(),headers);
        ResponseEntity<String> result =restTemplate.exchange(apiProxy.getUrl(), HttpMethod.resolve(apiProxy.getMethod()),entity,String.class);

        Map<String,Object> resultJson= mapper.readValue(result.getBody(), new TypeReference<Map<String, Object>>() {
        });
        return resultJson;
    }

    @ApiOperation(value = "WebClient 代理")
    @PostMapping("/webclientproxy")
    public Map<String,Object> webClientProxy(@RequestBody ViewHttpApiProxy apiProxy) throws JsonProcessingException,RuntimeException {
        Mono<String> result=WebClient.create().method(HttpMethod.resolve(apiProxy.getMethod()))
                .uri(apiProxy.getUrl())
                .headers(httpHeaders -> {
                    if(!apiProxy.getToken().isEmpty()) {
                        httpHeaders.add("token",apiProxy.getToken());
                    }
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue(apiProxy.getData())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(WebClientResponseException.class, err ->{
                    String errResult=err.getResponseBodyAsString();
                    log.error(errResult);
                    throw new RuntimeException(errResult);
                });
        Map<String,Object> resultJson= mapper.readValue(result.block(), new TypeReference<Map<String, Object>>() {});
        return resultJson;
    }
}
