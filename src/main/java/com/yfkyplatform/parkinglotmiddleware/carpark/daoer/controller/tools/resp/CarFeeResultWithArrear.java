package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.tools.resp;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrearByCharge;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ChannelInfoResult;
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
