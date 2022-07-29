package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.controller.carport;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.LifangParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.controller.carport.request.CarFeePayRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 车场控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"车场"})
@RequestMapping(value = "/Lifang/api/{parkingLotId}/carport")
@IgnoreCommonResponse
@RestController
public class LifangCarPortController {

    private final ParkingLotManager manager;

    public LifangCarPortController(LifangParkingLotManager manager) {
        this.manager = manager;
    }

    private ILifangCarPort api(String parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    @ApiOperation(value = "车位查询")
    @GetMapping
    public CarportResult getCarport(@PathVariable String parkingLotId) {
        return api(parkingLotId).getCarPortInfo();
    }

    @ApiOperation(value = "获取临时车缴纳金额")
    @GetMapping("/fee")
    public CarFeeResult getCarFee(@PathVariable String parkingLotId, String carNo) {
        return api(parkingLotId).getCarFeeInfo(carNo);
    }

    @ApiOperation(value = "临停缴费支付")
    @PostMapping("/fee")
    public LifangBaseResp payCarFee(@PathVariable String parkingLotId, @RequestBody CarFeePayRequest request) {
        return api(parkingLotId).payCarFeeAccess(request.getCarNo(), request.getPayTime(), request.getTotalAmount(), request.getDisAmount(),
                "协商收费", 11, request.getCouponAmount());
    }


}
