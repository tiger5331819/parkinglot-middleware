package com.yfkyplatform.parkinglot.carpark.daoer.controller.tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglot.carpark.daoer.controller.tools.request.ViewHttpApiProxy;
import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManagerFactory;
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
@RequestMapping(value = "/api/tools")
@RestController
public class ToolsController {

    private RestTemplate restTemplate;
    private ObjectMapper mapper;

    private IDaoerTool api;

    public ToolsController(RestTemplate restTemplate, ObjectMapper mapper, ParkingLotManagerFactory factory){
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.api= factory.manager("Daoer").parkingLot("DaoerTest").client();
    }

    @ApiOperation(value = "图片")
    @GetMapping(value = "/img",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImg(String imgPath){
        return api.getImage(imgPath).block();
    }

    @ApiOperation(value = "Access_Token")
    @GetMapping(value = "/token")
    public DaoerBaseResp<String> getToken(){
        DaoerBaseResp<String> result=new DaoerBaseResp<String>();
        result.setBody(api.getToken());
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

    @ApiOperation(value = "路径参数测试")
    @GetMapping("/test/{txt}")
    public String testPathVariable(@PathVariable String txt){
        return txt;
    }

    @ApiOperation(value = "查询字符串测试")
    @GetMapping("/test")
    public String stringTest(String txt){
        return txt;
    }

}
