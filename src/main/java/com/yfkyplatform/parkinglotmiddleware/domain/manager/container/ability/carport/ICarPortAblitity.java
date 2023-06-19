package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport;

import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.PageResult;

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
    ChannelDoorStateResult controlChannel(String channelId, Boolean channelIdStatus);

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
