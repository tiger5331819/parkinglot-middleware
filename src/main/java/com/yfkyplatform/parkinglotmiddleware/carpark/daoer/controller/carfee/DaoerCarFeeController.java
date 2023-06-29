package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carfee;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarFee;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrear;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.BlankCarRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.CarFeePayRequest;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 车场费用控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"车场费用"})
@RequestMapping(value = "/Daoer/api/{parkingLotId}/carfee")
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

    @ApiOperation(value = "获取临时车缴纳金额")
    @GetMapping("/fee")
    public DaoerBaseResp<CarFeeResult> getCarFee(@PathVariable String parkingLotId, String carNo) {
        return api(parkingLotId).getCarFeeInfo(carNo).block();
    }

    @ApiOperation(value = "获取临时车缴纳金额（欠费）")
    @GetMapping("/fee/arrear")
    public DaoerBaseResp<CarFeeResultWithArrear> getCarFeeWithArrear(@PathVariable String parkingLotId, String carNo) {
        return api(parkingLotId).getCarFeeInfoWithArrear(carNo).block();
    }

    @ApiOperation(value = "根据通道号获取缴纳金额")
    @GetMapping("/fee/channel")
    public DaoerBaseResp<CarFeeResult> getChannelCarFee(@PathVariable String parkingLotId, String channelId, String carNo, String openId) {
        return api(parkingLotId).getChannelCarFee(channelId, carNo, openId).block();
    }

    @ApiOperation(value = "根据通道号获取缴纳金额（欠费）")
    @GetMapping("/fee/arrear/channel")
    public DaoerBaseResp<CarFeeResultWithArrear> getChannelCarFeeWithArrea(@PathVariable String parkingLotId, String channelId) {
        return api(parkingLotId).getChannelCarFeeWithArrear(channelId).block();
    }

    @ApiOperation(value = "临停缴费支付")
    @PostMapping("/fee")
    public DaoerBaseResp payCarFee(@PathVariable String parkingLotId, @RequestBody CarFeePayRequest request) {
        return api(parkingLotId).payCarFeeAccess(request.getCarNo(), request.getEntryTime(), request.getPayTime(), request.getDuration(), request.getTotalAmount(), request.getDisAmount(),
                request.getPaymentType(), request.getPayType(), request.getPaymentTnx(), request.getCouponAmount(), request.getChannelId()).block();
    }

    @ApiOperation(value = "临停缴费支付（欠费）")
    @PostMapping("/fee/arrear")
    public DaoerBaseResp payCarFeeWithArrear(@PathVariable String parkingLotId, @RequestBody CarFeePayWithArrearRequest request) {
        return api(parkingLotId).payCarFeeAccessWithArrear(request.getCarNo(), request.getEntryTime(), request.getPayTime(),
                request.getDuration(), request.getTotalAmount(), request.getDisAmount(),
                request.getPaymentType(), request.getPayType(), request.getPaymentTnx(), request.getCouponAmount(),
                request.getChannelId(), request.getInId()).block();
    }

    @ApiOperation(value = "无牌车出场（欠费）")
    @PostMapping("/blankcar/out/arrear")
    public DaoerBaseResp<CarFeeResultWithArrear> blankCarOutWithArrear(@PathVariable String parkingLotId, @RequestBody BlankCarRequest request) {
        return api(parkingLotId).blankCarOutWithArrear(request.getOpenId(), request.getScanType(), request.getChannelId()).block();
    }

}
