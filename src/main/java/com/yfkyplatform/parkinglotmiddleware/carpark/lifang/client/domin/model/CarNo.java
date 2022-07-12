package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 临停车缴费
 *
 * @author Suhuyuan
 */
@Getter
@Setter
@ToString
public class CarNo extends LifangBase {

    /**
     * 车牌号码
     */
    private String carCode;
    /**
     * 卡号(若不为空,则认为是刷卡查询,不查询车牌号码)
     */
    private String serial;


    public CarNo(String uri) {
        super(uri);
    }
}
