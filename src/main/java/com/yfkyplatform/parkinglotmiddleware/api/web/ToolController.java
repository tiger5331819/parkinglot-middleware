package com.yfkyplatform.parkinglotmiddleware.api.web;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageWithArrearRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.web.req.ChannelPayAccessReq;
import com.yfkyplatform.parkinglotmiddleware.api.web.req.CleanCarReq;
import com.yfkyplatform.parkinglotmiddleware.api.web.req.PayAccessReq;
import com.yfkyplatform.parkinglotmiddleware.api.web.resp.CleanCarListResp;
import com.yfkyplatform.parkinglotmiddleware.api.web.resp.CleanCarResp;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import com.yfkyplatform.parkinglotmiddleware.universal.web.SaaSWebClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

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

    private final TestBox testBox;

    public ToolController(ICarPortService carPortService, Environment env, TestBox testBox) {
        this.carPortService = carPortService;
        this.env = env;
        this.testBox = testBox;
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

        OrderPayMessageRpcReq orderPayMessageRpcReq = new OrderPayMessageRpcReq();
        orderPayMessageRpcReq.setPayTime(rpcResp.getCreateTime());
        orderPayMessageRpcReq.setDiscountFee(rpcResp.getDiscountFee());
        orderPayMessageRpcReq.setPayType(2000);
        orderPayMessageRpcReq.setPaymentTransactionId(String.valueOf(IdUtil.getSnowflake().nextId()));
        orderPayMessageRpcReq.setPayFee(ObjectUtil.isNull(payAccess.getPayFee()) ? rpcResp.getPayFee() : payAccess.getPayFee().movePointRight(2));

        return carPortService.payAccess(parkingLotManager, parkingLotId, payAccess.getCarNo(), orderPayMessageRpcReq);
    }

    @ApiOperation(value = "直接支付金额(欠费)")
    @GetMapping("/{parkingLotManager}/{parkingLotId}/carport/ArrearFeeTest")
    public Boolean payAccessArrearTest(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, PayAccessReq payAccess) {
        CarOrderResultRpcResp rpcResp = carPortService.getCarFee(parkingLotManager, parkingLotId, payAccess.getCarNo());

        OrderPayMessageWithArrearRpcReq orderPayMessageRpcReq = new OrderPayMessageWithArrearRpcReq();
        orderPayMessageRpcReq.setPayTime(rpcResp.getCreateTime());
        orderPayMessageRpcReq.setDiscountFee(rpcResp.getDiscountFee());
        orderPayMessageRpcReq.setPayType(2000);
        orderPayMessageRpcReq.setPaymentTransactionId(String.valueOf(IdUtil.getSnowflake().nextId()));
        orderPayMessageRpcReq.setPayFee(ObjectUtil.isNull(payAccess.getPayFee()) ? rpcResp.getPayFee() : payAccess.getPayFee().movePointRight(2));
        orderPayMessageRpcReq.setInId(rpcResp.getInId());

        return carPortService.payAccess(parkingLotManager, parkingLotId, payAccess.getCarNo(), orderPayMessageRpcReq);
    }

    @ApiOperation(value = "直接支付通道金额(欠费)")
    @GetMapping("/{parkingLotManager}/{parkingLotId}/carport/channelArrearFeeTest")
    public Boolean payAccessChannelArrearTest(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, ChannelPayAccessReq payAccess) {
        CarOrderResultRpcResp rpcResp = carPortService.getChannelCarFee(parkingLotManager, parkingLotId, payAccess.getChannelId());

        OrderPayMessageWithArrearRpcReq orderPayMessageRpcReq = new OrderPayMessageWithArrearRpcReq();
        orderPayMessageRpcReq.setPayTime(rpcResp.getCreateTime());
        orderPayMessageRpcReq.setDiscountFee(rpcResp.getDiscountFee());
        orderPayMessageRpcReq.setPayType(2000);
        orderPayMessageRpcReq.setPaymentTransactionId(String.valueOf(IdUtil.getSnowflake().nextId()));
        orderPayMessageRpcReq.setPayFee(ObjectUtil.isNull(payAccess.getPayFee()) ? rpcResp.getPayFee() : payAccess.getPayFee().movePointRight(2));
        orderPayMessageRpcReq.setInId(rpcResp.getInId());

        return carPortService.payAccess(parkingLotManager, parkingLotId, rpcResp.getCarNo(), orderPayMessageRpcReq);
    }

    @ApiOperation(value = "批量人工清场")
    @PostMapping("/cleanCar")
    public CleanCarListResp cleanCar(@RequestBody CleanCarReq req) throws JsonProcessingException {

        SaaSWebClient saaSWebClient = testBox.saasClient("prod");
        Map<String, Object> resultJson = saaSWebClient.post("{\"parkinglotId\":\"" + req.getParkingLotId() + "\",\"orderPayState\":1000,\"chargeTimeStart\":\"" + req.getChargeTimeStart() + "\",\"chargeTimeClose\":\"" + req.getChargeTimeClose() + "\",\"plateNumberNotNull\":true,\"current\":1,\"size\":" + req.getSize() + "}", "mgntpc/pc/order/page", req.getToken());

        List<Map<String, Object>> records = (List<Map<String, Object>>) resultJson.get("records");
        List<Long> orderId = records.stream().map(item -> (Long) item.get("orderId")).collect(Collectors.toList());

        List<CleanCarResp> cleanCarRespList = orderId.stream().map(item -> {

            try {
                saaSWebClient.post("{\"explain\":\"人工清场，取消订单\",\"orderId\":" + item + "}", "mgntpc/pc/order/outsideClean", req.getToken());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

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
