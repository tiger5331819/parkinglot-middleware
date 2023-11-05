package com.parkinglotmiddleware.carpark.daoer.client.domin.api;

import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.PageModel;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.List;


/**
 * 车场功能接口
 *
 * @author Suhuyuan
 */
public interface IDaoerCarPort {
    /**
     * 获取车位使用情况
     *
     * @return
     */
    Mono<DaoerBaseResp<List<CarportResult>>> getCarPortInfo();

    /**
     * 无牌车入场
     *
     * @return
     */
    Mono<DaoerBaseResp<BlankCarInResult>> blankCarIn(String openId, int scanType, String channelId);

    /**
     * 无牌车出场
     *
     * @return
     */
    Mono<DaoerBaseResp<BlankCarOutResult>> blankCarOut(String openId, int scanType, String channelId);

    /**
     * 获取入场记录
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<PageModel<CarInData>>> getCarInInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize);

    /**
     * 获取出场记录
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<PageModel<CarOutData>>> getCarOutInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize);

    /**
     * 获取通道列表
     *
     * @return
     */
    Mono<DaoerBaseResp<List<ChannelResult>>> getChannelsInfo();

    /**
     * 获取设备状态
     *
     * @return
     */
    Mono<DaoerBaseResp<List<ChannelStateResult>>> getChannelStates();

    /**
     * 控制道闸开、关。
     *
     * @return
     */
    Mono<DaoerBaseResp<List<ChannelStatusResult>>> controlChannel(String channelId, int channelIdStatus);

    /**
     * 月租车锁车/解锁
     *
     * @param carNo
     * @param status
     * @return
     */
    Mono<DaoerBaseResp<CarLockResult>> lockMonthlyCar(String carNo, Integer status);

    /**
     * 月租车锁车状态
     *
     * @param carNo
     * @return
     */
    Mono<DaoerBaseResp<CarLockResult>> monthlyCarLockInfo(String carNo);

    /**
     * 补缴回调
     *
     * @return
     */
    Mono<DaoerBaseResp> dueCarSuccess(String channelId,String carNo);

    /**
     * 同步补缴配置信息
     *
     * @param notIn 是否不可进
     * @param notOut 是否不可出
     * @param startTime 生效开始时间
     * @param closeTime 生效结束时间
     * @return
     */
    Mono<DaoerBaseResp> configDueCar(Integer notIn, Integer notOut, LocalTime startTime, LocalTime closeTime);
}
