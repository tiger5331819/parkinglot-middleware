package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee;

import lombok.Data;

import java.util.List;

/**
 * 临时车缴费订单结果（欠费）
 *
 * @author Suhuyuan
 */
@Data
public class CarOrderWithArrearResultByList extends CarOrderWithArrearResult {

    /**
     * 欠费订单
     */
    private List<CarOrderWithArrearResult> arrearList;
}
