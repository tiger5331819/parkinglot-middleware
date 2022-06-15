package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.CarInMessage;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.CarOutMessage;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.response.ParkingLotPostResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车辆出入场记录通知
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"车场"})
@RequestMapping(value = "/api/carport/message")
@RestController
@Validated
public class CarInAndOutMessageController {

    @ApiOperation(value = "车辆入场通知")
    @PostMapping("/in")
    public ParkingLotPostResp carInMessage(@RequestBody CarInMessage message){
        log.info(message.toString());
        return new ParkingLotPostResp();
    }

    @ApiOperation(value = "车辆出场通知")
    @PostMapping("/out")
    public ParkingLotPostResp carOutMessage(@RequestBody CarOutMessage message){
        log.info(message.toString());
        return new ParkingLotPostResp();
    }
}
