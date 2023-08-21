package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport;

import lombok.Data;

/**
 * 停车场车位结果
 *
 * @author Suhuyuan
 */
@Data
public class CarPortSpaceResult {

    /**
     * 总车位数
     */
    private int total;

    /**
     * 空余车位数
     */
    private int rest;

    /**
     * 剩余空车位
     */
    private int remainder;

}
