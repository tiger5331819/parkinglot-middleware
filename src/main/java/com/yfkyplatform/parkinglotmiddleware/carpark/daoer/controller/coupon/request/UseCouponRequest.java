package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.coupon.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 使用优惠券请求
 *
 * @author Suhuyuan
 */
@Data
public class UseCouponRequest {
    @Schema(name =  "优惠券领用后唯一ID",required = true)
    private String objectId;
    @Schema(name =  "使用车牌号码",required = true)
    private String carNo;
}
