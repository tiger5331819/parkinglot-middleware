package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.BlankCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.ChannelCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarMessageRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultByListRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarPortSpaceRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.web.req.OrderPayMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"车场控制器"})
@RequestMapping(value = "api/{parkingLotManager}/{parkingLotId}/carport")
@RestController
public class CarPortController {

    private final ICarPortService carPortService;

    public CarPortController(ICarPortService service){
        carPortService=service;
    }

    @ApiOperation(value = "车场余位")
    @GetMapping("/space")
    public CarPortSpaceRpcResp getCarPortSpace(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId) {
        return carPortService.getCarPortSpace(parkingLotManager, parkingLotId, null);
    }

    @ApiOperation(value = "无牌车入场")
    @PostMapping("/blankCarIn")
    public String blankCarIn(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @RequestBody BlankCarRpcReq blankCar) {
        return carPortService.blankCarIn(parkingLotManager, parkingLotId, blankCar, null);
    }

    @ApiOperation(value = "临时车出场（获取车辆费用）")
    @GetMapping("/{carNo}/Fee")
    public CarOrderResultByListRpcResp getCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        return carPortService.getCarFee(parkingLotManager, parkingLotId, carNo, null);
    }

    @ApiOperation(value = "车辆缴费")
    @PatchMapping("/{carNo}/Fee")
    public Boolean payAccess(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo, @RequestBody OrderPayMessage payData) {

        OrderPayMessageRpcReq orderPayMessageRpcReq = new OrderPayMessageRpcReq();
        orderPayMessageRpcReq.setPayTime(payData.getPayTime());
        orderPayMessageRpcReq.setDiscountFee(payData.getDiscountFee());
        orderPayMessageRpcReq.setPayType(payData.getPayType());
        orderPayMessageRpcReq.setPaymentTransactionId(payData.getPaymentTransactionId());
        orderPayMessageRpcReq.setPayFee(payData.getPayFee());
        orderPayMessageRpcReq.setInId(payData.getInId());


        return carPortService.payAccess(parkingLotManager, parkingLotId, carNo, orderPayMessageRpcReq, null);
    }

    @ApiOperation(value = "根据通道号获取车辆费用信息")
    @PostMapping("/channel/Fee")
    public CarOrderResultByListRpcResp getChannelCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @RequestBody ChannelCarRpcReq rpcReq) {
        return carPortService.getChannelCarFee(parkingLotManager, parkingLotId, rpcReq, null);
    }

    @ApiOperation(value = "获取车辆信息")
    @GetMapping("/{carNo}")
    public CarMessageRpcResp getChannelCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, String carNo) {
        return carPortService.getCarInfo(parkingLotManager, parkingLotId, carNo, null);
    }


}
