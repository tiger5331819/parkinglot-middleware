package com.yfkyplatform.parkinglotmiddleware.api.web;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.web.req.CleanCarReq;
import com.yfkyplatform.parkinglotmiddleware.api.web.req.PayAccessReq;
import com.yfkyplatform.parkinglotmiddleware.api.web.resp.CleanCarListResp;
import com.yfkyplatform.parkinglotmiddleware.api.web.resp.CleanCarResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 工具控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"工具控制器"})
@RequestMapping(value = "api/tool")
@IgnoreCommonResponse
@RestController
public class ToolController {

    private final ICarPortService carPortService;

    private final Environment env;

    public ToolController(ICarPortService carPortService, Environment env) {
        this.carPortService = carPortService;
        this.env = env;
    }

    @ApiOperation(value = "获取版本信息")
    @GetMapping("/version")
    public String getVersion() {
        return env.getProperty("app.version");
    }

    @ApiOperation(value = "直接支付金额")
    @GetMapping("/{parkingLotManager}/{parkingLotId}/carport/FeeTest")
    public Boolean payAccessTest(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, PayAccessReq payAccess) {
        CarOrderResultRpcResp rpcResp = carPortService.getCarFee(parkingLotManager, parkingLotId, payAccess.getCarNo());

        BigDecimal carFee = ObjectUtil.isNull(payAccess.getPayFee()) ? rpcResp.getPayFee() : payAccess.getPayFee().movePointRight(2);

        OrderPayMessageRpcReq orderPayMessageRpcReq = new OrderPayMessageRpcReq();
        orderPayMessageRpcReq.setPayTime(rpcResp.getCreateTime());
        orderPayMessageRpcReq.setDiscountFee(rpcResp.getDiscountFee());
        orderPayMessageRpcReq.setPayType(2000);
        orderPayMessageRpcReq.setPaymentTransactionId(String.valueOf(IdUtil.getSnowflake().nextId()));
        orderPayMessageRpcReq.setPayFee(rpcResp.getPayFee());

        return carPortService.payAccess(parkingLotManager, parkingLotId, payAccess.getCarNo(), orderPayMessageRpcReq);
    }

    @ApiOperation(value = "批量人工清场")
    @PostMapping("/cleanCar")
    public CleanCarListResp cleanCar(@RequestBody CleanCarReq req) throws JsonProcessingException {
        Mono<String> result = WebClient.create().method(HttpMethod.POST)
                .uri("https://mgnt-pc.q-parking.com/api/mgntpc/pc/order/page")
                .headers(httpHeaders -> {
                    httpHeaders.add("Authorization", req.getToken());
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue("{\"parkinglotId\":\"" + req.getParkingLotId() + "\",\"orderPayState\":1000,\"chargeTimeStart\":\"" + req.getChargeTimeStart() + "\",\"chargeTimeClose\":\"" + req.getChargeTimeClose() + "\",\"plateNumberNotNull\":true,\"current\":1,\"size\":" + req.getSize() + "}")
                .retrieve()
                .bodyToMono(String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultJson = mapper.readValue(result.block(), new TypeReference<Map<String, Object>>() {
        });

        Map<String, Object> data = (Map<String, Object>) resultJson.get("data");
        List<Map<String, Object>> records = (List<Map<String, Object>>) data.get("records");
        List<Long> orderId = records.stream().map(item -> (Long) item.get("orderId")).collect(Collectors.toList());

        List<CleanCarResp> cleanCarRespList = orderId.stream().map(item -> {
            Mono<String> result2 = WebClient.create().method(HttpMethod.POST)
                    .uri("https://mgnt-pc.q-parking.com/api/mgntpc/pc/order/outsideClean")
                    .headers(httpHeaders -> {
                        httpHeaders.add("Authorization", req.getToken());
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    })
                    .bodyValue("{\"explain\":\"人工清场，取消订单\",\"orderId\":" + item + "}")
                    .retrieve()
                    .bodyToMono(String.class);
            result2.block();

            CleanCarResp resp = new CleanCarResp();
            resp.setSuccess(true);
            resp.setOrderId(item);
            return resp;
        }).collect(Collectors.toList());

        CleanCarListResp resp = new CleanCarListResp();
        resp.setData(cleanCarRespList);
        resp.setTotal(cleanCarRespList.size());

        return resp;
    }
}
