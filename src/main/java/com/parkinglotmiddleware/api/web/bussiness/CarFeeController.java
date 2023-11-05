package com.parkinglotmiddleware.api.web.bussiness;

import com.parkinglotmiddleware.api.dubbo.service.carfee.ICarFeeService;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.CarFeeRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.ChannelCarRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.OrderPayMessageRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.response.CarOrderResultByListRpcResp;
import com.parkinglotmiddleware.api.web.bussiness.req.OrderPayMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 车场费用控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "车场费用控制器")
@RequestMapping(value= "api/{parkingLotManager}/{parkingLotId}/carport")
@RestController
public class CarFeeController {

    private final ICarFeeService carPortService;

    public CarFeeController(ICarFeeService service){
        carPortService=service;
    }



    @Operation(summary =  "临时车出场（获取车辆费用）")
    @GetMapping("/{carNo}/Fee")
    public CarOrderResultByListRpcResp getCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @PathVariable String carNo) {
        CarFeeRpcReq carFeeRpcReq=new CarFeeRpcReq();
        carFeeRpcReq.setCarNo(carNo);
        carFeeRpcReq.setParkingLotManagerCode(parkingLotManager);
        carFeeRpcReq.setParkingLotId(parkingLotId);

        return carPortService.getCarFee(carFeeRpcReq);
    }

    @Operation(summary =  "车辆缴费")
    @PatchMapping("/{carNo}/Fee")
    public void payAccess(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId,@PathVariable String carNo,@RequestBody OrderPayMessage payData) {

        OrderPayMessageRpcReq orderPayMessageRpcReq = new OrderPayMessageRpcReq();
        orderPayMessageRpcReq.setCarNo(carNo);
        orderPayMessageRpcReq.setPayTime(payData.getPayTime());
        orderPayMessageRpcReq.setDiscountFee(payData.getDiscountFee());
        orderPayMessageRpcReq.setPayType(payData.getPayType());
        orderPayMessageRpcReq.setPaymentTransactionId(payData.getPaymentTransactionId());
        orderPayMessageRpcReq.setPayFee(payData.getPayFee());
        orderPayMessageRpcReq.setInId(payData.getInId());
        orderPayMessageRpcReq.setParkingLotManagerCode(parkingLotManager);
        orderPayMessageRpcReq.setParkingLotId(parkingLotId);

        carPortService.payAccess(orderPayMessageRpcReq);
    }

    @Operation(summary =  "根据通道号获取车辆费用信息")
    @PostMapping("/channel/Fee")
    public CarOrderResultByListRpcResp getChannelCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotId, @RequestBody ChannelCarRpcReq rpcReq) {
        rpcReq.setParkingLotManagerCode(parkingLotManager);
        rpcReq.setParkingLotId(parkingLotId);
        return carPortService.getChannelCarFee(rpcReq);
    }
}
