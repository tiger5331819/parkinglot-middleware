package com.yfkyplatform.parkinglotmiddleware.api.web;

import cn.hutool.core.util.IdUtil;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"测试控制器"})
@RequestMapping(value = "api/test")
@IgnoreCommonResponse
@RestController
public class TestController {

    private final ICarPortService carPortService;

    private final Environment env;

    public TestController(ICarPortService carPortService, ICarPortService carPortService1, Environment env) {
        this.carPortService = carPortService1;
        this.env = env;
    }

    @ApiOperation(value = "获取版本信息")
    @GetMapping("/version")
    public String getVersion() {
        return env.getProperty("app.version");
    }

    @ApiOperation(value = "直接支付完整金额")
    @GetMapping("/{parkingLotManager}/{parkingLotId}/carport/{carNo}/FeeTest")
    public Boolean payAccessTest(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        CarOrderResultRpcResp rpcResp = carPortService.getCarFee(parkingLotManager, parkingLotId, carNo);

        OrderPayMessageRpcReq orderPayMessageRpcReq = new OrderPayMessageRpcReq();
        orderPayMessageRpcReq.setPayTime(rpcResp.getCreateTime());
        orderPayMessageRpcReq.setDiscountFee(rpcResp.getDiscountFee());
        orderPayMessageRpcReq.setPayType(2000);
        orderPayMessageRpcReq.setPaymentTransactionId(String.valueOf(IdUtil.getSnowflake().nextId()));
        orderPayMessageRpcReq.setPayFee(rpcResp.getPayFee());

        return carPortService.payAccess(parkingLotManager, parkingLotId, carNo, orderPayMessageRpcReq);
    }
}
