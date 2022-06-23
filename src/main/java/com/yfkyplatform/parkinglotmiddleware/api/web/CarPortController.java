package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyplatform.ordercenter.api.resp.OrderParkingRecordRpcResp;
import com.yfkyplatform.ordercenter.api.resp.OrderPayDetailRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.BlankCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarPortSpaceRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.ChannelInfoResultRpcResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import java.util.List;

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
    public CarPortSpaceRpcResp getCarPortSpace(@PathVariable Integer parkingLotManager, @PathVariable Long parkingLotId) {
        return carPortService.getCarPortSpace(parkingLotManager, parkingLotId);
    }

    @ApiOperation(value = "无牌车出场")
    @PostMapping("/blankCarOut")
    public CarOrderResultRpcResp blankCarOut(@PathVariable Integer parkingLotManager, @PathVariable Long parkingLotId, @RequestBody BlankCarRpcReq blankCar) {
        return carPortService.blankCarOut(parkingLotManager, parkingLotId, blankCar);
    }

    @ApiOperation(value = "无牌车入场")
    @PostMapping("/blankCarIn")
    public String blankCarIn(@PathVariable Integer parkingLotManager, @PathVariable Long parkingLotId, @RequestBody BlankCarRpcReq blankCar) {
        return carPortService.blankCarIn(parkingLotManager, parkingLotId, blankCar);
    }

    @ApiOperation(value = "临时车出场（获取车辆费用）")
    @GetMapping("/{carNo}/Fee")
    public CarOrderResultRpcResp getCarFee(@PathVariable Integer parkingLotManager, @PathVariable Long parkingLotId, @PathVariable String carNo) {
        return carPortService.getCarFee(parkingLotManager, parkingLotId, carNo);
    }

    @ApiOperation(value = "车辆缴费")
    @PatchMapping("/{carNo}/Fee")
    public Boolean payAccess(@PathVariable Integer parkingLotManager, @PathVariable Long parkingLotId, @PathVariable String carNo, @RequestBody OrderPayMessage payData) {
        OrderParkingRecordRpcResp orderParkingRecord = new OrderParkingRecordRpcResp();
        orderParkingRecord.setPlateNumber(carNo);
        orderParkingRecord.setParkinglotId(parkingLotId);
        OrderPayDetailRpcResp payMessage = new OrderPayDetailRpcResp();

        return carPortService.payAccess(parkingLotManager, orderParkingRecord, payMessage);
    }

    @ApiOperation(value = "根据通道号获取车辆费用信息")
    @GetMapping("/channel/Fee")
    public CarOrderResultRpcResp getChannelCarFee(@PathVariable Integer parkingLotManager, @PathVariable Long parkingLotId, String channelId, @Nullable String carNo, @Nullable String openId) {
        return carPortService.getChannelCarFee(parkingLotManager, parkingLotId, channelId, carNo, openId);
    }

    @ApiOperation(value = "获取通道列表")
    @GetMapping("/channel")
    public List<ChannelInfoResultRpcResp> getChannelCarFee(@PathVariable Integer parkingLotManager, @PathVariable Long parkingLotId) {
        return carPortService.getChannelsInfo(parkingLotManager, parkingLotId);
    }
}
