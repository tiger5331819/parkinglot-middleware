package com.parkinglotmiddleware.carpark.lifang.client.controller.carport;

import com.parkinglotmiddleware.carpark.lifang.LifangParkingLotManager;
import com.parkinglotmiddleware.carpark.lifang.client.controller.carport.request.CarFeePayRequest;
import com.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import com.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 车场控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "车场")
@RequestMapping(value= "/Lifang/api/{parkingLotId}/carport")
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

    @Operation(summary =  "车位查询")
    @GetMapping
    public CarportResult getCarport(@PathVariable String parkingLotId) {
        return api(parkingLotId).getCarPortInfo();
    }

    @Operation(summary =  "获取临时车缴纳金额")
    @GetMapping("/fee")
    public CarFeeResult getCarFee(@PathVariable String parkingLotId, String carNo) {
        return api(parkingLotId).getCarFeeInfo(carNo);
    }

    @Operation(summary =  "临停缴费支付")
    @PostMapping("/fee")
    public LifangBaseResp payCarFee(@PathVariable String parkingLotId, @RequestBody CarFeePayRequest request) {
        return api(parkingLotId).payCarFeeAccess(request.getCarNo(), request.getPayTime(), request.getTotalAmount(), request.getDisAmount(),
                "协商收费", 11, request.getCouponAmount());
    }


}
