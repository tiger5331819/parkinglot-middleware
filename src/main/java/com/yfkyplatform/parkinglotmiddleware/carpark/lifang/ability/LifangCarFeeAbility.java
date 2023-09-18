package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.ability;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarFeeResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderPayMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.ICarFeeAblitity;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

/**
 * 立方车场支付能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class LifangCarFeeAbility implements ICarFeeAblitity {

    private final ILifangCarPort api;

    private final RedisTool redis;

    public LifangCarFeeAbility(ILifangCarPort daoerClient, RedisTool redis) {
        api = daoerClient;
        this.redis = redis;
    }


    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfo(String carNo) {
        CarFeeResult carFee = api.getCarFeeInfo(carNo);

        CarOrderResult result = new CarOrderResult();
        result.setCarNo(carNo);
        result.setStartTime(carFee.getInTime());
        result.setCreateTime(carFee.getPayTime());
        result.setServiceTime(Math.toIntExact(LocalDateTimeUtil.between(carFee.getInTime(), carFee.getPayTime()).toMinutes()));
        result.setTotalFee(carFee.getChargeMoney());
        result.setPayFee(carFee.getPaidMoney());
        result.setDiscountFee(carFee.getJMMoney());

        return result;
    }

    /**
     * 临停缴费支付完成
     *
     * @param payMessage 订单支付信息
     * @return
     */
    @Override
    public Boolean payCarFee(CarOrderPayMessage payMessage) {
        CarFeeResult carFee = api.getCarFeeInfo(payMessage.getCarNo());

        int payState = api.payCarFeeAccess(payMessage.getCarNo(), payMessage.getPayTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        carFee.getChargeMoney(), payMessage.getPayFee(), "协商收费", 11, carFee.getJMMoney())
                .getResCode();
        return payState == 0;
    }

    /**
     * 根据通道号获取车辆费用信息
     * 支持无牌车出场
     *
     * @param channelId
     * @param scanType
     * @param openId
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfoByChannel(String channelId, int scanType, String openId) {
        return null;
    }


}
