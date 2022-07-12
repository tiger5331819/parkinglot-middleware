package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import reactor.core.publisher.Mono;

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
    Mono<CarportResult> getCarPortInfo();

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<CarFeeResult> getCarFeeInfo(String carNo);

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<LifangBaseResp> payCarFeeAccess(String carNo, String payTime, BigDecimal totalAmount, BigDecimal disAmount,
                                         String paySource, int payType, BigDecimal couponAmount);

}
