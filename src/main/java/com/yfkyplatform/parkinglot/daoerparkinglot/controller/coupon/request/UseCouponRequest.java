package com.yfkyplatform.parkinglot.daoerparkinglot.controller.coupon.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 使用优惠券请求
 *
 * @author Suhuyuan
 */
@Data
public class UseCouponRequest {
    @ApiModelProperty(value = "优惠券领用后唯一ID",required = true)
    private String objectId;
    @ApiModelProperty(value = "使用车牌号码",required = true)
    private String carNo;
}
