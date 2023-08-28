package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport;

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
     * 已停车位数
     */
    private int use;

}
