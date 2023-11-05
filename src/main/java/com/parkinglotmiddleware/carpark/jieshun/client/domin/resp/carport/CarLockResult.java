package com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.carport;

import lombok.Data;

/**
 * 锁车结果信息
 *
 * @author Suhuyuan
 */
@Data
public class CarLockResult {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 0：未锁定,1：锁定,2未入场,3车牌不合法或不在场内
     */
    private int status;
}
