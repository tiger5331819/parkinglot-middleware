package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 月租车锁车/解锁请求
 *
 * @author Suhuyuan
 */
@Data
public class LockMonthlyCarRequest {

    @ApiModelProperty(value = "默认为1, 1锁定，0解锁", required = false)
    private Integer status;
}
