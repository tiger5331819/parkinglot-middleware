package com.yfkyplatform.parkinglot.domain.manager.container.ability.carport;

import java.util.List;


/**
 * 车场功能接口
 *
 * @author Suhuyuan
 */
public interface ICarPortAblitity {
    /**
     * 获取车位使用情况
     *
     * @return
     */
    CarPortSpaceResult getCarPortSpace();
    /**
     * 获取临时车缴纳金额
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
    CarOrderResult getChannelCarFee(String channelId,String carNo, String openId);
    /**
     * 无牌车入场
     *
     * @return
     */
    BlankCarInResult blankCarIn(String openId, int scanType, String channelId);
    /**
     * 无牌车出场
     *
     * @return
     */
    BlankCarOutResult blankCarOut(String openId, int scanType, String channelId);
    /**
     * 获取通道列表
     *
     * @return
     */
    List<ChannelResult> getChannelsInfo();

    /**
     * 获取设备状态
     *
     * @return
     */
     List<ChannelStateResult> getChannelStates();

    /**
     * 控制道闸开、关。
     *
     * @return
     */
    List<ChannelStatusResult> controlChannel(String channelId, int channelIdStatus);
}
