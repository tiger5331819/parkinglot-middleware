package com.yfkyplatform.parkinglotmiddleware.carpark.lifang.ability;

import com.yfkyplatform.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.PageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 道尔车场能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class LifangCarPortAbility implements ICarPortAblitity {

    private final ILifangCarPort api;

    private final RedisTool redis;

    public LifangCarPortAbility(ILifangCarPort daoerClient, RedisTool redis) {
        api = daoerClient;
        this.redis = redis;
    }


    /**
     * 获取车位使用情况
     *
     * @return
     */
    @Override
    public CarPortSpaceResult getCarPortSpace() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfo(String carNo) {
        throw new UnsupportedOperationException();
    }

    /**
     * 临停缴费支付完成
     *
     * @param payMessage 订单支付信息
     * @return
     */
    @Override
    public Boolean payCarFeeAccess(CarOrderPayMessage payMessage) {
        throw new UnsupportedOperationException();
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param channelId
     * @param carNo     车牌号码
     * @param openId
     * @return
     */
    @Override
    public CarOrderResult getChannelCarFee(String channelId, String carNo, String openId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 无牌车入场
     *
     * @param openId
     * @param scanType
     * @param channelId
     * @return
     */
    @Override
    public BlankCarScanInResult blankCarIn(String openId, int scanType, String channelId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 无牌车出场
     *
     * @param openId
     * @param scanType
     * @param channelId
     * @return
     */
    @Override
    public BlankCarScanOutResult blankCarOut(String openId, int scanType, String channelId) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取通道列表
     *
     * @return
     */
    @Override
    public List<ChannelInfoResult> getChannelsInfo() {
        throw new UnsupportedOperationException();
    }

    /**
     * 控制道闸开、关。
     *
     * @param channelId
     * @param channelIdStatus
     * @return
     */
    @Override
    public ChannelDoorStateResult controlChannel(String channelId, Boolean channelIdStatus) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取入场记录
     *
     * @param carNo     车牌号码
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<CarInResult> getCarInInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize) {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取出场记录
     *
     * @param carNo     车牌号码
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<CarOutResult> getCarOutInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize) {
        throw new UnsupportedOperationException();
    }
}
