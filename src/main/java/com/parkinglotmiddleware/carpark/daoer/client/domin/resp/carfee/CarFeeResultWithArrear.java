package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee;

import lombok.Data;

import java.util.List;

/**
 * 临时车缴费金额欠费结果
 *
 * @author Suhuyuan
 */
@Data
public class CarFeeResultWithArrear {

    /**
     * 当前缴费订单信息
     */
    private CarFeeResultWithArrearByCharge charge;

    /**
     * 欠费订单信息
     */
    private List<CarFeeResultWithArrearByCharge> arrears;

}
