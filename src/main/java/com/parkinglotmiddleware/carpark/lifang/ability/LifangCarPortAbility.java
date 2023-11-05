package com.parkinglotmiddleware.carpark.lifang.ability;

import com.parkinglotmiddleware.carpark.lifang.client.domin.api.ILifangCarPort;
import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport.CarportResult;
import com.parkinglotmiddleware.domain.manager.container.service.ability.PageResult;
import com.parkinglotmiddleware.domain.manager.container.service.ability.carport.*;
import com.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
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
        CarportResult carport = api.getCarPortInfo();
        CarPortSpaceResult result = new CarPortSpaceResult();
        result.setTotal(carport.getTotalNum());
        result.setRest(carport.getTotalRemainNum());

        return result;
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
     * 获取通道列表与状态
     *
     * @return
     */
    @Override
    public List<ChannelInfoWithStateResult> getChannelsInfoWithState() {
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

    /**
     * 补缴成功通知
     *
     * @param channelId 通道Id
     * @param carNo     车牌号码
     * @return
     */
    @Override
    public void dueCarAccess(String channelId, String carNo) {

    }

    /**
     * 补缴成功通知
     *
     * @param notIn     是否不可进
     * @param notOut    是否不可出
     * @param startTime 生效开始时间
     * @param closeTime 生效结束时间
     * @return
     */
    @Override
    public void configDueCar(Integer notIn, Integer notOut, LocalTime startTime, LocalTime closeTime) {

    }
}
