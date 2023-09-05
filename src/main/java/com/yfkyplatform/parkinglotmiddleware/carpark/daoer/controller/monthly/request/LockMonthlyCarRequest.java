package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 月租车锁车/解锁请求
 *
 * @author Suhuyuan
 */
@Data
public class LockMonthlyCarRequest {

    @Schema(description =  "默认为1, 1锁定，0解锁", required = false)
    private Integer status;
}
