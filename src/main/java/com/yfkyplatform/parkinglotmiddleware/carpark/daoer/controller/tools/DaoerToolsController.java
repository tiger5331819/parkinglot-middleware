package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool.URLResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.req.ViewHttpApiProxy;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp.AllURLResultResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp.CarCheckResultResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp.SaaSPayMessageResultResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp.URLResultResp;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.PageResult;
import com.yfkyplatform.parkinglotmiddleware.universal.TestBox;
import com.yfkyplatform.parkinglotmiddleware.universal.web.SaaSWebClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private final TestBox testBox;

    private IDaoerTool api(String parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    public DaoerToolsController(RestTemplate restTemplate, ObjectMapper mapper, DaoerParkingLotManager manager, TestBox testBox) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.manager = manager;
        this.testBox = testBox;
    }

    @ApiOperation(value = "图片")
    @GetMapping(value = "/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImg(@PathVariable String parkingLotId, String imgPath) {
        return api(parkingLotId).getImage(imgPath).block();
    }

    @ApiOperation(value = "Access_Token")
    @GetMapping(value = "/token")
    public DaoerBaseResp<String> getToken(@PathVariable String parkingLotId) {
        DaoerBaseResp<String> result = new DaoerBaseResp<String>();
        result.setBody(api(parkingLotId).getToken());
        return result;
    }

    @ApiOperation(value = "URL地址")
    @GetMapping(value = "/url/{environment}")
    public URLResultResp getURL(@PathVariable String parkingLotId, @PathVariable String environment, @ApiParam(value = "微信配置ID") String wechatPay, @ApiParam(value = "支付宝配置ID") String aliPay) {
        URLResult urlResult = api(parkingLotId).makeURL();
        String webUrl = testBox.environmentWebURL(environment);

        urlResult.getBlankCarURLList().forEach(item -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(webUrl);
            stringBuilder.append("scanh5/daoer/scanenter/");
            if (StrUtil.isNotBlank(wechatPay) && StrUtil.isBlank(aliPay)) {
                stringBuilder.append(wechatPay);
            } else if (StrUtil.isNotBlank(aliPay) && StrUtil.isBlank(wechatPay)) {
                stringBuilder.append(aliPay);
            } else {
                stringBuilder.append(wechatPay);
                stringBuilder.append("-");
                stringBuilder.append(aliPay);
            }
            stringBuilder.append(item.getUrl());

            item.setUrl(stringBuilder.toString());
        });

        urlResult.getCarOutPayURLList().forEach(item -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(webUrl);
            stringBuilder.append("scanh5/daoer/orderdetailpay/");
            if (StrUtil.isNotBlank(wechatPay) && StrUtil.isBlank(aliPay)) {
                stringBuilder.append(wechatPay);
            } else if (StrUtil.isNotBlank(aliPay) && StrUtil.isBlank(wechatPay)) {
                stringBuilder.append(aliPay);
            } else {
                stringBuilder.append(wechatPay);
                stringBuilder.append("-");
                stringBuilder.append(aliPay);
            }
            stringBuilder.append(item.getUrl());

            item.setUrl(stringBuilder.toString());
        });

        URLResultResp resp = new URLResultResp();
        resp.setBlankCarURLList(urlResult.getBlankCarURLList());
        resp.setCarOutPayURLList(urlResult.getCarOutPayURLList());

        return resp;
    }

    @ApiOperation(value = "获取SaaS 租户和支付id")
    @GetMapping(value = "/saasToken")
    public SaaSPayMessageResultResp getSaaSToken(@ApiParam(value = "token") String token) throws JsonProcessingException {
        SaaSWebClient saaSWebClient = new SaaSWebClient();
        Map<String, Object> data = saaSWebClient.get("mgntpc/tenant/get-tenant", token);
        Integer tenantId = (Integer) data.get("tenantId");
        AtomicReference<Long> aliThirdId = new AtomicReference<>(null);
        AtomicReference<Long> wxThirdId = new AtomicReference<>(null);


        Map<String, Object> data2 = saaSWebClient.get("mgntpc/tenant/get-tenant-thirdparty", token);
        List<Map<String, Object>> aliApps = (List<Map<String, Object>>) data2.get("aliApps");
        aliApps.stream().filter(item -> !((Boolean) item.get("main")))
                .findFirst()
                .ifPresent(item -> aliThirdId.set((Long) item.get("thirdpartyAppId")));

        List<Map<String, Object>> wxMPApps = (List<Map<String, Object>>) data2.get("wxMPApps");
        wxMPApps.stream().filter(item -> (Boolean) item.get("main"))
                .findFirst()
                .ifPresent(item -> wxThirdId.set((Long) item.get("thirdpartyAppId")));

        SaaSPayMessageResultResp resp = new SaaSPayMessageResultResp();
        resp.setTenantId(tenantId);
        resp.setAliThirdId(aliThirdId.get());
        resp.setWxThirdId(wxThirdId.get());

        return resp;
    }


    @ApiOperation(value = "全部URL地址")
    @GetMapping(value = "/url/{environment}/all")
    public List<AllURLResultResp> getAllURL(@PathVariable String environment, @ApiParam(value = "车场描述") String parkingLotName,
                                            @ApiParam(value = "token") String token) throws JsonProcessingException {
        SaaSPayMessageResultResp resultResp = getSaaSToken(token);


        List<DaoerParkingLotConfiguration> configurationList = manager.configurationList(null);
        String origin = testBox.environmentGateWayURL(environment) + "outside/passthough/" + resultResp.getTenantId();

        return configurationList.stream().filter(item -> item.getDescription().contains(parkingLotName)).map(cfg -> {
            URLResultResp resp = getURL(cfg.getId(), environment, String.valueOf(resultResp.getWxThirdId()), String.valueOf(resultResp.getAliThirdId()));
            AllURLResultResp allURLResultResp = new AllURLResultResp();
            allURLResultResp.setParkingLotId(cfg.getId());
            allURLResultResp.setParkingLotName(cfg.getDescription());
            allURLResultResp.setParkingLotThirdCode(cfg.getParkId());
            allURLResultResp.setBlankCarURLList(resp.getBlankCarURLList());
            allURLResultResp.setCarOutPayURLList(resp.getCarOutPayURLList());
            allURLResultResp.setParkingLotThirdAppName(cfg.getAppName());
            allURLResultResp.setCarInUrl(origin + "/daoer/in");
            allURLResultResp.setCarOutUrl(origin + "/daoer/out");

            return allURLResultResp;
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "RestTemplate 代理")
    @PostMapping("/resttemplateproxy")
    public Map<String, Object> restTemplateProxy(@RequestBody ViewHttpApiProxy apiProxy) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        if (!apiProxy.getToken().isEmpty()) {
            headers.add("token", apiProxy.getToken());
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
                .doOnError(WebClientResponseException.class, err -> {
                    String errResult = err.getResponseBodyAsString();
                    log.error(errResult);
                    throw new RuntimeException(errResult);
                });
        Map<String, Object> resultJson = mapper.readValue(result.block(), new TypeReference<Map<String, Object>>() {
        });
        return resultJson;
    }

    @ApiOperation(value = "检查车辆是否在场")
    @PostMapping("/checkCar")
    public List<CarCheckResultResp> checkCar(@PathVariable String parkingLotId, @RequestBody String data) {
        String[] carNos = data.split("\r\n");
        List<CarCheckResultResp> resultResps = new LinkedList<>();
        for (String carNo : carNos) {
            PageResult result = manager.parkingLot(parkingLotId).carport().getCarInInfo(carNo, "", "", 1, 10);
            CarCheckResultResp resp = new CarCheckResultResp();
            resp.setCarNo(carNo);
            resp.setIn(result.getTotal() != 0);

            resultResps.add(resp);
        }

        return resultResps;
    }
}
