package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carfee.CarFeeResultWithArrear;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


/**
 * 车场费用功能接口
 *
 * @author Suhuyuan
 */
public interface IDaoerCarFee {
    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResult>> getCarFeeInfo(String carNo);

    /**
     * 获取临时车缴纳金额（支持欠费）
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResultWithArrear>> getCarFeeInfoWithArrear(String carNo);

    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp> payCarFeeAccess(String carNo, String entryTime, String payTime, int duration, BigDecimal totalAmount, BigDecimal disAmount,
                                        int paymentType, int payType, String paymentTnx, BigDecimal couponAmount, String channelId);

    /**
     * 临停缴费支付完成（支持欠费）
     *
     * @return
     */
    Mono<DaoerBaseResp> payCarFeeAccessWithArrear(String carNo, String entryTime, String payTime, int duration, BigDecimal totalAmount, BigDecimal disAmount,
                                                  int paymentType, int payType, String paymentTnx, BigDecimal couponAmount, String channelId, String inId);

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResult>> getChannelCarFee(String channelId, String carNo, String openId);

    /**
     * 根据通道号获取车辆费用信息（支持欠费）
     *
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResultWithArrear>> getChannelCarFeeWithArrear(String channelId);

    /**
     * 无牌车出场（支持欠费）
     *
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResultWithArrear>> blankCarOutWithArrear(String openId, int scanType, String channelId);
}
