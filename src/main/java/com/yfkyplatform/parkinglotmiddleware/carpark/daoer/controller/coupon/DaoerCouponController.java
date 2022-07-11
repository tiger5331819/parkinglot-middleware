package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.coupon;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCoupon;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.coupon.CouponUseResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.coupon.request.UseCouponRequest;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠券控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"优惠券"})
@RequestMapping(value = "/Daoer/api/{parkingLotId}/coupon")
@IgnoreCommonResponse
@RestController
public class DaoerCouponController {

    private final ParkingLotManager manager;

    private IDaoerCoupon api(String parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    public DaoerCouponController(DaoerParkingLotManager manager) {
        this.manager = manager;
    }

    @ApiOperation(value = "查询优惠券")
    @GetMapping
    public DaoerBaseResp<List<CouponResult>> getCoupon(@PathVariable String parkingLotId, @ApiParam(value = "微信openId") String openId) {
        return api(parkingLotId).getCoupon(openId).block();
    }

    @ApiOperation(value = "使用优惠券")
    @PostMapping
    public DaoerBaseResp<CouponUseResult> useCoupon(@PathVariable String parkingLotId, @RequestBody UseCouponRequest request) {
        return api(parkingLotId).useCoupon(request.getObjectId(), request.getCarNo()).block();
    }
}
