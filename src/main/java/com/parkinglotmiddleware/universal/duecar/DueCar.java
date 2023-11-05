package com.parkinglotmiddleware.universal.duecar;

import lombok.Data;

/**
 * @author Suhuyuan
 */
@Data
public class DueCar {

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 车牌颜色
     */
    private Integer plateColor;

    /**
     * 车辆类型
     */
    private Integer vehicleType;
}
