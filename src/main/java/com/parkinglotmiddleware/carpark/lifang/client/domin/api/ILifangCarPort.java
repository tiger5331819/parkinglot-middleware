package com.parkinglotmiddleware.carpark.lifang.client.domin.api;

import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;

import java.math.BigDecimal;


/**
 * 车场功能接口
 *
 * @author Suhuyuan
 */
public interface ILifangCarPort {
    /**
     * 获取车位使用情况
     *
     * @return
     */
    CarportResult getCarPortInfo();

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    CarFeeResult getCarFeeInfo(String carNo);

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    LifangBaseResp payCarFeeAccess(String carNo, String payTime, BigDecimal totalAmount, BigDecimal disAmount,
                                   String paySource, int payType, BigDecimal couponAmount);

}
