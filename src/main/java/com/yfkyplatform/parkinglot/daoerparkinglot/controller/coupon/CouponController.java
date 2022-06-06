package com.yfkyplatform.parkinglot.daoerparkinglot.controller.coupon;

import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerCoupon;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.coupon.CouponResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.coupon.CouponUseResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglot.daoerparkinglot.controller.coupon.request.UseCouponRequest;
import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManager;
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
@RequestMapping(value = "/api/coupon")
@RestController
public class CouponController {

    private IDaoerCoupon api;

    public CouponController(ParkingLotManager manager){
        this.api=(IDaoerCoupon)manager.parkingLot("DaoerTest");
    }

    @ApiOperation(value = "查询优惠券")
    @GetMapping
    public DaoerBaseResp<List<CouponResult>> getCoupon(@ApiParam(value = "微信openId") String openId){
        return api.getCoupon(openId).block();
    }

    @ApiOperation(value = "使用优惠券")
    @PostMapping
    public DaoerBaseResp<List<CouponUseResult>> useCoupon(@RequestBody UseCouponRequest request){
        return api.useCoupon(request.getObjectId(),request.getCarNo()).block();
    }
}
