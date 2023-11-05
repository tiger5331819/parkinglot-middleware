package com.parkinglotmiddleware.carpark.daoer.controller.carfee;

import com.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarFee;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrear;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.parkinglotmiddleware.carpark.daoer.controller.carport.request.BlankCarRequest;
import com.parkinglotmiddleware.carpark.daoer.controller.carport.request.CarFeePayRequest;
import com.parkinglotmiddleware.domain.manager.ParkingLotManager;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
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
@Tag(name = "车场费用")
@RequestMapping(value= "/Daoer/api/{parkingLotId}/carfee")
@IgnoreCommonResponse
@RestController
public class DaoerCarFeeController {

    private final ParkingLotManager manager;

    public DaoerCarFeeController(DaoerParkingLotManager manager) {
        this.manager = manager;
    }

    private IDaoerCarFee api(String parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    @Operation(summary =  "获取临时车缴纳金额")
    @GetMapping("")
    public DaoerBaseResp<CarFeeResult> getCarFee(@PathVariable String parkingLotId, String carNo) {
        return api(parkingLotId).getCarFeeInfo(carNo).block();
    }

    @Operation(summary =  "获取临时车缴纳金额（欠费）")
    @GetMapping("/arrear")
    public DaoerBaseResp<CarFeeResultWithArrear> getCarFeeWithArrear(@PathVariable String parkingLotId, String carNo) {
        return api(parkingLotId).getCarFeeInfoWithArrear(carNo).block();
    }

    @Operation(summary =  "根据通道号获取缴纳金额")
    @GetMapping("/channel")
    public DaoerBaseResp<CarFeeResult> getChannelCarFee(@PathVariable String parkingLotId, String channelId, String carNo, String openId) {
        return api(parkingLotId).getChannelCarFee(channelId, carNo, openId).block();
    }

    @Operation(summary =  "根据通道号获取缴纳金额（欠费）")
    @GetMapping("/arrear/channel")
    public DaoerBaseResp<CarFeeResultWithArrear> getChannelCarFeeWithArrea(@PathVariable String parkingLotId, String channelId) {
        return api(parkingLotId).getChannelCarFeeWithArrear(channelId).block();
    }

    @Operation(summary =  "临停缴费支付")
    @PostMapping("")
    public DaoerBaseResp payCarFee(@PathVariable String parkingLotId, @RequestBody CarFeePayRequest request) {
        return api(parkingLotId).payCarFee(request.getCarNo(), request.getEntryTime(), request.getPayTime(), request.getDuration(), request.getTotalAmount(), request.getDisAmount(),
                request.getPaymentType(), request.getPayType(), request.getPaymentTnx(), request.getCouponAmount(), request.getChannelId()).block();
    }

    @Operation(summary =  "临停缴费支付（欠费）")
    @PostMapping("/arrear")
    public DaoerBaseResp payCarFeeWithArrear(@PathVariable String parkingLotId, @RequestBody CarFeePayWithArrearRequest request) {
        return api(parkingLotId).payCarFeeWithArrear(request.getCarNo(), request.getEntryTime(), request.getPayTime(),
                request.getDuration(), request.getTotalAmount(), request.getDisAmount(),
                request.getPaymentType(), request.getPayType(), request.getPaymentTnx(), request.getCouponAmount(),
                request.getChannelId(), request.getInId(), request.getParkNo()).block();
    }

    @Operation(summary =  "无牌车出场（欠费）")
    @PostMapping("/blankcar/out/arrear")
    public DaoerBaseResp<CarFeeResultWithArrear> blankCarOutWithArrear(@PathVariable String parkingLotId, @RequestBody BlankCarRequest request) {
        return api(parkingLotId).blankCarOutWithArrear(request.getOpenId(), request.getScanType(), request.getChannelId()).block();
    }

}
