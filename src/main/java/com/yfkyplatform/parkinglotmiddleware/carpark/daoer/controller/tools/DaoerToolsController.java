package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools;

import cn.hutool.core.util.ObjectUtil;
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
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp.SaaSPayMessageResultResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp.URLResultResp;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import com.yfkyplatform.parkinglotmiddleware.universal.web.SaaSWebClient;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工具控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "基础支持（工具）")
@RequestMapping(value= "/Daoer/api/tools")
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

    @Operation(summary =  "图片")
    @GetMapping(value= "/{parkingLotId}/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImg(@PathVariable String parkingLotId, String imgPath) {
        return api(parkingLotId).getImage(imgPath).block();
    }

    @Operation(summary =  "Access_Token")
    @GetMapping(value= "/{parkingLotId}/token")
    public DaoerBaseResp<String> getToken(@PathVariable String parkingLotId) {
        DaoerBaseResp<String> result = new DaoerBaseResp<String>();
        result.setBody(api(parkingLotId).getToken());
        return result;
    }

    @Operation(summary =  "URL地址")
    @GetMapping(value= "/url/{environment}")
    public URLResultResp getURL(@PathVariable String parkingLotId, @PathVariable String environment, @Parameter(description =  "微信配置ID") String wechatPay, @Parameter(description =  "支付宝配置ID") String aliPay) {
        URLResult urlResult = api(parkingLotId).makeURL();
        String webUrl = testBox.envUrl().environmentWebURL(environment);

        urlResult.getBlankCarURLList().forEach(item -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(webUrl);
            stringBuilder.append("scanh5/daoer/scanenter/");
            if (StrUtil.isNotBlank(wechatPay) && (StrUtil.isBlank(aliPay) || StrUtil.equals(aliPay, "null"))) {
                stringBuilder.append(wechatPay);
            } else if (StrUtil.isNotBlank(aliPay) && (StrUtil.isBlank(wechatPay) || StrUtil.equals(wechatPay, "null"))) {
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
            if (StrUtil.isNotBlank(wechatPay) && (StrUtil.isBlank(aliPay) || StrUtil.equals(aliPay, "null"))) {
                stringBuilder.append(wechatPay);
            } else if (StrUtil.isNotBlank(aliPay) && (StrUtil.isBlank(wechatPay) || StrUtil.equals(wechatPay, "null"))) {
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

    @Operation(summary =  "获取SaaS 租户和支付id")
    @GetMapping(value= "/saasToken")
    public SaaSPayMessageResultResp getSaaSToken(@Parameter(description =  "environment") String environment, @Parameter(description =  "token") String token) throws JsonProcessingException {
        SaaSWebClient saaSWebClient = testBox.saasClient(environment);
        Map<String, Object> data = saaSWebClient.get("mgntpc/tenant/get-tenant", token);
        Integer tenantId = (Integer) data.get("tenantId");
        Long aliThirdId = null;
        Long wxThirdId = null;


        Map<String, Object> data2 = saaSWebClient.get("mgntpc/tenant/get-tenant-thirdparty", token);
        Map<String, Object> aliApp = (Map<String, Object>) data2.get("aliApp");
        if(ObjectUtil.isNotNull(aliApp)){
            aliThirdId=(Long) aliApp.get("thirdpartyAppId");
        }


        Map<String, Object> wxMPApp = (Map<String, Object>) data2.get("wxMpApp");
        if(ObjectUtil.isNotNull(wxMPApp)){
            wxThirdId=(Long) wxMPApp.get("thirdpartyAppId");
        }

        SaaSPayMessageResultResp resp = new SaaSPayMessageResultResp();
        resp.setTenantId(tenantId);
        resp.setAliThirdId(aliThirdId);
        resp.setWxThirdId(wxThirdId);

        return resp;
    }


    @Operation(summary =  "全部URL地址")
    @GetMapping(value= "/url/{environment}/all")
    public List<AllURLResultResp> getAllURL(@PathVariable String environment, @Parameter(description =  "车场描述") String parkingLotName,
                                            @Parameter(description =  "token") String token) throws JsonProcessingException {
        SaaSPayMessageResultResp resultResp = getSaaSToken(environment, token);


        List<DaoerParkingLotConfiguration> configurationList = manager.configurationList(null);
        String origin = testBox.envUrl().environmentGateWayURL(environment) + "outside/passthough/" + resultResp.getTenantId();

        if (!AssertTool.checkCollectionNotNull(configurationList)) {
            String data = "{\"pageNum\":1,\"pageSize\":10,\"parkName\":\"" + parkingLotName + "\"}";
            Map<String, Object> result = testBox.drCloudClient().post(data, "api/backstage/regist/findall");
            Map<String, Object> resultData = (Map<String, Object>) result.get("data");
            List<Map<String, Object>> list = (List<Map<String, Object>>) resultData.get("list");
            configurationList = list.stream().map(item -> {
                String saasParkingLotConfigPrefix = "saasParkingLotConfig.Daoer.";
                DaoerParkingLotConfiguration cfg = new DaoerParkingLotConfiguration((String) item.get("parkNo"), (String) item.get("appName"),
                        (String) item.get("parkNo"),
                        testBox.env.getProperty(saasParkingLotConfigPrefix + "baseUrl"), (String) item.get("parkName"),
                        testBox.env.getProperty(saasParkingLotConfigPrefix + "imgUrl"), false);
                manager.addParkingLot(cfg);
                return cfg;
            }).collect(Collectors.toList());
        }

        return configurationList.stream().filter(item -> item.getDescription().contains(parkingLotName)).map(cfg -> {
            URLResultResp resp = getURL(cfg.getId(), environment, String.valueOf(resultResp.getWxThirdId()), String.valueOf(resultResp.getAliThirdId()));

            AllURLResultResp allURLResultResp = new AllURLResultResp();
            allURLResultResp.setParkingLotId(cfg.getId());
            allURLResultResp.setParkingLotName(cfg.getDescription());
            allURLResultResp.setParkingLotThirdCode(cfg.getParkId());
            allURLResultResp.setParkingLotThirdAppName(cfg.getAppName());
            allURLResultResp.setHealthCheck(manager.parkingLot(cfg.getId()).healthCheck());

            allURLResultResp.setBlankCarURLList(resp.getBlankCarURLList());
            allURLResultResp.setCarOutPayURLList(resp.getCarOutPayURLList());

            allURLResultResp.setCarInUrl(origin + "/daoer/in");
            allURLResultResp.setCarInListUrl(origin + "/daoer/in/list");
            allURLResultResp.setCarOutUrl(origin + "/daoer/out");
            allURLResultResp.setCarOutListUrl(origin + "/daoer/out/list");
            allURLResultResp.setDueUrl(testBox.envUrl().environmentGateWayURL(environment)+ "outside/duecar/"+ resultResp.getTenantId());

            return allURLResultResp;
        }).collect(Collectors.toList());
    }

    @Operation(summary =  "RestTemplate 代理")
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

    @Operation(summary =  "WebClient 代理")
    @PostMapping("/webclientproxy")
    public Map<String,Object> webClientProxy(@RequestBody ViewHttpApiProxy apiProxy) throws JsonProcessingException,RuntimeException {
        SslContextBuilder sslContextBuilder = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE);

        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10_000)
                .secure(spec->spec.sslContext(sslContextBuilder))
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(60)));

        Mono<String> result=WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(reactor.netty.http.client.HttpClient.from(tcpClient)))
                .build().method(HttpMethod.resolve(apiProxy.getMethod()))
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


}
