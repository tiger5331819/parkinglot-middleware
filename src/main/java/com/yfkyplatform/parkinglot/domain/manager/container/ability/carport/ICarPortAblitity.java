package com.yfkyplatform.parkinglot.domain.manager.container.ability.carport;

import com.yfkyplatform.parkinglot.domain.manager.container.ability.PageResult;

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
    BlankCarScanInResult blankCarIn(String openId, int scanType, String channelId);
    /**
     * 无牌车出场
     *
     * @return
     */
    BlankCarScanOutResult blankCarOut(String openId, int scanType, String channelId);
    /**
     * 获取通道列表
     *
     * @return
     */
    List<ChannelInfoResult> getChannelsInfo();

    /**
     * 控制道闸开、关。
     *
     * @return
     */
    ChannelDoorStateResult controlChannel(String channelId, boolean channelIdStatus);

    /**
     * 获取入场记录
     * @param carNo 车牌号码
     * @return
     */
    PageResult<CarInResult> getCarInInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize);
    /**
     * 获取出场记录
     *
     * @param carNo 车牌号码
     * @return
     */
    PageResult<CarOutResult> getCarOutInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize);
}
