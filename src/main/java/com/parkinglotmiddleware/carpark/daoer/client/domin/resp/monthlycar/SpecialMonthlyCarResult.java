package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar;

import lombok.Data;

/**
 * 特殊车辆
 *
 * @author Suhuyuan
 */
@Data
public class SpecialMonthlyCarResult {
    /**
     * 车牌号码
     */
    private String carNo;
    /**
     * 1黑名单/2特殊车辆
     */
    private int carNoType;
    /**
     * 通行类型 类型(1禁止通行,2通行免费,3自由通行)
     */
    private int isStop;
    /**
     * 备注
     */
    private String description;
    /**
     * 唯一表示
     */
    private String objectId;
}
