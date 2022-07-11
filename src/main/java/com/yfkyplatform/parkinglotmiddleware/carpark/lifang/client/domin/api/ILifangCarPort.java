package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.daoerbase.DaoerBaseResp;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;


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
    Mono<DaoerBaseResp<List<CarportResult>>> getCarPortInfo();

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResult>> getCarFeeInfo(String carNo);

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp> payCarFeeAccess(String carNo, String entryTime, String payTime, int duration, BigDecimal totalAmount, BigDecimal disAmount,
                                        int paymentType, int payType, String paymentTnx, BigDecimal couponAmount, String channelId);

}
