package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee;

/**
 * 车场费用功能接口
 *
 * @author Suhuyuan
 */
public interface ICarFeeAblitity {
    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    CarOrderResult getCarFeeInfo(String carNo);

    /**
     * 临停缴费支付完成
     *
     * @param payMessage 订单支付信息
     * @return
     */
    Boolean payCarFeeAccess(CarOrderPayMessage payMessage);

    /**
     * 根据通道号获取车辆费用信息
     * 支持无牌车出场
     *
     * @return
     */
    CarOrderResult getCarFeeInfoByChannel(String channelId, int scanType, String openId);

}
