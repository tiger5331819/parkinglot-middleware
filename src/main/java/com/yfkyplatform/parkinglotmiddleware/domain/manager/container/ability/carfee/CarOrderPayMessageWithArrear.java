package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee;

import lombok.Data;

/**
 * 车辆订单缴费信息（欠费）
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderPayMessageWithArrear extends CarOrderPayMessage {

    /**
     * 入场记录ID
     */
    String inId;
}
