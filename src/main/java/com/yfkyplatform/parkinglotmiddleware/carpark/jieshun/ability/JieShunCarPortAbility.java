package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.ability;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.api.IJieShunCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.resp.PageModel;
import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.PageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.*;
import com.yfkyplatform.parkinglotmiddleware.universal.RedisTool;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 道尔车场能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class JieShunCarPortAbility implements ICarPortAblitity {

    private final IJieShunCarPort api;

    private final RedisTool redis;

    public JieShunCarPortAbility(IJieShunCarPort daoerClient, RedisTool redis) {
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
        List<CarportResult> results = api.getCarPortInfo().block().getBody();
        CarportResult carportResult = results.get(0);

        CarPortSpaceResult carPortSpaceResult = new CarPortSpaceResult();
        carPortSpaceResult.setTotal(carportResult.getTotal().getTotal());
        carPortSpaceResult.setRest(carportResult.getIdle().getTotal());

        return carPortSpaceResult;
    }

    /**
     * 无牌车入场
     * 道尔无牌车接口没有响应——504 GateWay TimeOut 无法获取无牌车车牌
     * 无牌车车牌获取途径：出入场通知、通道
     *
     * @param openId
     * @param scanType
     * @param channelId
     * @return
     */
    @Override
    public BlankCarScanInResult blankCarIn(String openId, int scanType, String channelId) {
        String carNo = null;
        try {
            BlankCarInResult result = api.blankCarIn(openId, scanType, channelId).block(Duration.ofSeconds(2)).getBody();
            carNo = ObjectUtil.isNull(result) ? "" : result.getCarNo();
        } catch (IllegalStateException ex) {

        }

        BlankCarScanInResult scanInResult = new BlankCarScanInResult();
        scanInResult.setCarNo(carNo);
        scanInResult.setStartTime(LocalDateTime.now());

        return scanInResult;
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
     * 获取通道列表与状态
     *
     * @return
     */
    @Override
    public List<ChannelInfoWithStateResult> getChannelsInfoWithState() {
        Mono<DaoerBaseResp<List<ChannelResult>>> channelMono = api.getChannelsInfo();
        Mono<DaoerBaseResp<List<ChannelStateResult>>> channelStatesMono = api.getChannelStates();

        return Mono.zip(channelMono, channelStatesMono, (channelsInfo, channelsStates) -> {
            List<ChannelResult> channelsInfoBody = channelsInfo.getBody();
            List<ChannelStateResult> channelsStatesBody = channelsStates.getBody();

            return channelsInfoBody.stream().map(channelResult -> {
                ChannelStateResult channelStateResult = channelsStatesBody.stream()
                        .filter(item -> item.getChannelId().contains(channelResult.getChannelId()))
                        .findFirst()
                        .get();
                if (ObjectUtil.isNotNull(channelStateResult)) {
                    ChannelInfoWithStateResult data = new ChannelInfoWithStateResult();
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
    }

    /**
     * 控制道闸开、关。
     *
     * @param channelId
     * @param channelStatus
     * @return
     */
    @Override
    public ChannelDoorStateResult controlChannel(String channelId, Boolean channelStatus) {
        Mono<DaoerBaseResp<List<ChannelStatusResult>>> request;
        if (channelStatus) {
            request = api.controlChannel(channelId, 1);
        } else {
            request = api.controlChannel(channelId, 2);
        }

        ChannelStatusResult data = request.block().getBody().get(0);
        ChannelDoorStateResult result = new ChannelDoorStateResult();
        result.setChannelId(data.getChannelId());
        result.setChannelStatus(data.getChannelStatus() == 1);

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
        PageModel<CarInData> carInData = api.getCarInInfo(carNo, startTime, endTime, pageNum, pageSize).block().getBody();
        if (ObjectUtil.isNull(carInData.getList())) {

        }
        List<CarInResult> dataList = ObjectUtil.isNull(carInData.getList()) ? new LinkedList<>() : carInData.getList().stream().map(carIn -> {
            CarInResult data = new CarInResult();
            data.setCarNo(carIn.getCarNo());
            data.setCardTypeId(carIn.getCardTypeId());
            data.setCardTypeName(carIn.getCardTypeName());
            data.setInPic(carIn.getInPic());
            data.setInTime(carIn.getInTime());
            return data;
        }).collect(Collectors.toList());

        return new PageResult(carInData.getPageNum(), carInData.getPageSize(), carInData.getTotal(), dataList);
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
        PageModel<CarOutData> carOutData = api.getCarOutInfo(carNo, startTime, endTime, pageNum, pageSize).block().getBody();
        List<CarOutResult> dataList = carOutData.getList().stream().map(carOut -> {
            CarOutResult data = new CarOutResult();
            data.setCarNo(carOut.getCarNo());
            data.setCardTypeId(carOut.getCardTypeId());
            data.setCardTypeName(carOut.getCardTypeName());
            data.setInPic(carOut.getInPic());
            data.setInTime(carOut.getInTime());
            data.setOutPic(carOut.getOutPic());
            data.setOutTime(carOut.getOutTime());
            return data;
        }).collect(Collectors.toList());

        return new PageResult(carOutData.getPageNum(), carOutData.getPageSize(), carOutData.getTotal(), dataList);
    }
}
