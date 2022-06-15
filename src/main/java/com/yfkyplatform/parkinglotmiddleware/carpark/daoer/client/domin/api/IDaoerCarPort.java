package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import reactor.core.publisher.Mono;

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
     * 获取临时车缴纳金额
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResult>> getCarFeeInfo(String carNo);
    /**
     * 临停缴费支付完成
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp> payCarFeeAccess(String carNo, String payTime, int duration, double totalAmount, double disAmount,
                                               int paymentType,int payType,String paymentTnx,double couponAmount,String channelId);
    /**
     * 根据通道号获取车辆费用信息
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarFeeResult>> getChannelCarFee(String channelId,String carNo, String openId);
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
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarInOrOutResult<CarInData>>> getCarInInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize);
    /**
     * 获取出场记录
     *
     * @param carNo 车牌号码
     * @return
     */
    Mono<DaoerBaseResp<CarInOrOutResult<CarOutData>>> getCarOutInfo(String carNo, String startTime, String endTime, int pageNum, int pageSize);

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
}
