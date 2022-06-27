package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.ability;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.PageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.*;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 道尔车场能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class DaoerCarPortAbility implements ICarPortAblitity {

    private final IDaoerCarPort api;

    private final RedisTool redis;

    public DaoerCarPortAbility(IDaoerCarPort daoerClient, RedisTool redis) {
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
        List<CarportResult>results= api.getCarPortInfo().block().getBody();
        CarportResult carportResult= results.get(0);

        CarPortSpaceResult carPortSpaceResult=new CarPortSpaceResult();
        carPortSpaceResult.setTotal(carportResult.getTotal().getTotal());
        carPortSpaceResult.setRest(carportResult.getIdle().getTotal());

        return carPortSpaceResult;
    }

    /**
     * 获取临时车缴纳金额
     *
     * @param carNo 车牌号码
     * @return
     */
    @Override
    public CarOrderResult getCarFeeInfo(String carNo) {
        CarFeeResult result = api.getCarFeeInfo(carNo).block().getBody();
        if (ObjectUtil.isNull(result)) {
            result = new CarFeeResult();
        }
        return CarFeeToCarOrder(result);
    }

    /**
     * 临停缴费支付完成
     *
     * @param payMessage 订单支付信息
     * @return
     */
    @Override
    public Boolean payCarFeeAccess(CarOrderPayMessage payMessage) {
        String key = "order:daoer:" + payMessage.getCarNo();
        if (!redis.check(key)) {
            return false;
        }
        Mono<DaoerBaseResp<CarFeeResult>> mono = api.getCarFeeInfo(payMessage.getCarNo());
        String channelId = redis.get(key);

        int payType;
        switch (payMessage.getPayType()) {
            case 2000:
            case 2001:
                payType = 1;
                break;
            case 3000:
            case 3001:
                payType = 2;
                break;
            default:
                payType = 0;
        }

        CarFeeResult fee = mono.block().getBody();

        int payState = api.payCarFeeAccess(payMessage.getCarNo(),
                payMessage.getPayTime(),
                fee.getChargeDuration(),
                fee.getPayCharge(),
                fee.getDiscountAmount(),
                0,
                payType,
                payMessage.getPaymentTransactionId(),
                payMessage.getPayFee(),
                channelId).block().getHead().getStatus();
        return payState == 1;
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
        CarFeeResult result = api.getChannelCarFee(channelId, carNo, openId).block().getBody();
        redis.set("order:daoer:" + result.getCarNo(), channelId);

        if (ObjectUtil.isNull(result)) {
            result = new CarFeeResult();
        }
        return CarFeeToCarOrder(result);
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
        BlankCarInResult result= api.blankCarIn(openId, scanType, channelId).block().getBody();

        BlankCarScanInResult scanInResult=new BlankCarScanInResult();
        scanInResult.setCarNo(result.getCarNo());
        scanInResult.setStartTime(result.getInTime());

        return scanInResult;
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
        BlankCarOutResult result= api.blankCarOut(openId, scanType, channelId).block().getBody();

        BlankCarScanOutResult scanOutResult=new BlankCarScanOutResult();
        scanOutResult.setCarNo(result.getCarNo());

        return scanOutResult;
    }

    /**
     * 获取通道列表
     *
     * @return
     */
    @Override
    public List<ChannelInfoResult> getChannelsInfo() {
        Mono<DaoerBaseResp<List<ChannelResult>>> channelMono=api.getChannelsInfo();
        Mono<DaoerBaseResp<List<ChannelStateResult>>> channelStatesMono=api.getChannelStates();

        List<ChannelInfoResult> result = Mono.zip(channelMono, channelStatesMono, (channelsInfo, channelsStates) -> {
            List<ChannelResult> channelsInfoBody = channelsInfo.getBody();
            List<ChannelStateResult> channelsStatesBody = channelsStates.getBody();

            return channelsInfoBody.stream().map(channelResult -> {
                ChannelStateResult channelStateResult = channelsStatesBody.stream()
                        .filter(item -> item.getChannelId().contains(channelResult.getChannelId()))
                        .findFirst()
                        .get();
                if (ObjectUtil.isNotNull(channelStateResult)) {
                    ChannelInfoResult data = new ChannelInfoResult();
                    data.setChannelId(channelResult.getChannelId());
                    data.setChannelName(channelResult.getChannelName());
                    data.setType(channelResult.getType());
                    data.setBoard(channelStateResult.getBoard());
                    data.setCamera(channelStateResult.getCamera());
                    data.setDoor(channelStateResult.getDoor());
                    data.setSense(channelStateResult.getSense());
                    return data;
                        } else {
                            return null;
                        }
                    }).collect(Collectors.toList());
        }).block();

        return result;
    }

    /**
     * 控制道闸开、关。
     *
     * @param channelId
     * @param channelStatus
     * @return
     */
    @Override
    public ChannelDoorStateResult controlChannel(String channelId, boolean channelStatus) {
        Mono<DaoerBaseResp<List<ChannelStatusResult>>> request;
        if(channelStatus){
            request=api.controlChannel(channelId,1);
        } else{
            request=api.controlChannel(channelId, 2);
        }

        ChannelStatusResult data=request.block().getBody().get(0);
        ChannelDoorStateResult result=new ChannelDoorStateResult();
        result.setChannelId(data.getChannelId());
        result.setChannelStatus(data.getChannelStatus()==1);

        return result;
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
        CarInOrOutResult<CarInData> carInData=api.getCarInInfo(carNo, startTime, endTime, pageNum, pageSize).block().getBody();
        List<CarInResult> dataList=carInData.getList().stream().map(carIn->{
            CarInResult data=new CarInResult();
            data.setCarNo(carIn.getCarNo());
            data.setCardTypeId(carIn.getCardTypeId());
            data.setCardTypeName(carIn.getCardTypeName());
            data.setInPic(carIn.getInPic());
            data.setInTime(carIn.getInTime());
            return data;
        }).collect(Collectors.toList());

        return new PageResult(carInData.getPageNum(),carInData.getPageSize(),carInData.getTotal(),dataList);
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
        CarInOrOutResult<CarOutData> carOutData=api.getCarOutInfo(carNo, startTime, endTime, pageNum, pageSize).block().getBody();
        List<CarOutResult> dataList=carOutData.getList().stream().map(carOut->{
            CarOutResult data=new CarOutResult();
            data.setCarNo(carOut.getCarNo());
            data.setCardTypeId(carOut.getCardTypeId());
            data.setCardTypeName(carOut.getCardTypeName());
            data.setInPic(carOut.getInPic());
            data.setInTime(carOut.getInTime());
            data.setOutPic(carOut.getOutPic());
            data.setOutTime(carOut.getOutTime());
            return data;
        }).collect(Collectors.toList());

        return new PageResult(carOutData.getPageNum(),carOutData.getPageSize(),carOutData.getTotal(),dataList);
    }

    private CarOrderResult CarFeeToCarOrder(CarFeeResult carFeeResult){
        CarOrderResult orderResult=new CarOrderResult();

        orderResult.setCarNo(carFeeResult.getCarNo());
        orderResult.setDiscountFee(carFeeResult.getDiscountAmount());
        orderResult.setPayFee(carFeeResult.getAmount());
        orderResult.setTotalFee(carFeeResult.getPayCharge());
        orderResult.setCreateTime(carFeeResult.getChargeTime());
        orderResult.setStartTime(carFeeResult.getInTime());
        orderResult.setServiceTime(carFeeResult.getChargeDuration());

        return orderResult;
    }
}
