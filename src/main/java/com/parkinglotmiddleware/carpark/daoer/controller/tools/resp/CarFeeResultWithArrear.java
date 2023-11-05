package com.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrearByCharge;
import com.parkinglotmiddleware.domain.manager.container.service.ability.carport.ChannelInfoResult;
import lombok.Data;

import java.util.List;

/**
 * 临时车缴费金额欠费结果
 *
 * @author Suhuyuan
 */
@Data
public class CarFeeResultWithArrear {
    private ChannelInfoResult channel;

    /**
     * 当前缴费订单信息
     */
    private CarFeeResultWithArrearByCharge charge;

    /**
     * 欠费订单信息
     */
    private List<CarFeeResultWithArrearByCharge> arrears;

}
