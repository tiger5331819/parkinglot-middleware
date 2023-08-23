package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.ability;

import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.api.IJieShunCarFee;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderPayMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.ICarFeeAblitity;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;

/**
 * 捷顺费用能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class JieShunCarFeeAbility implements ICarFeeAblitity {

    private final IJieShunCarFee api;

    private final RedisTool redis;

    public JieShunCarFeeAbility(IJieShunCarFee daoerClient, RedisTool redis) {
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
        return null;
    }

    /**
     * 临停缴费支付完成
     *
     * @param payMessage 订单支付信息
     * @return
     */
    @Override
    public Boolean payCarFeeAccess(CarOrderPayMessage payMessage) {
        return null;
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
