package com.parkinglotmiddleware.carpark.lifang.client.domin.model;


import lombok.Data;

/**
 * 临停车缴费
 *
 * @author Suhuyuan
 */
@Data
public class CarNo {

    /**
     * 车牌号码
     */
    private String carCode;
    /**
     * 卡号(若不为空,则认为是刷卡查询,不查询车牌号码)
     */
    private String serial;
}
