package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee;

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
     *
     * @param carNo 车牌号码
     * @return
     */
    CarOrderResult getCarFeeInfo(String channelId, String carNo, String openId);

    /**
     * 根据通道号获取车辆费用信息（支持欠费）
     *
     * @param carNo 车牌号码
     * @return
     */
    CarOrderWithArrearResult getCarFeeInfoWithArrear(String channelId, String carNo);

    /**
     * 无牌车出场（支持欠费）
     *
     * @return
     */
    CarOrderWithArrearResult blankCarOutWithArrear(String openId, int scanType, String channelId);

    /**
     * 获取临时车缴纳金额（支持欠费）
     *
     * @param carNo 车牌号码
     * @return
     */
    CarOrderWithArrearResult getCarFeeInfoWithArrear(String carNo);

    /**
     * 临停缴费支付完成（支持欠费）
     *
     * @param payMessage 订单支付信息
     * @return
     */
    Boolean payCarFeeAccessWithArrear(CarOrderPayMessageWithArrear payMessage);

}
