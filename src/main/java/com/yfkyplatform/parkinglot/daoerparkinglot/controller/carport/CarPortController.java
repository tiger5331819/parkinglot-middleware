package com.yfkyplatform.parkinglot.daoerparkinglot.controller.carport;

import com.yfkyplatform.parkinglot.daoerparkinglot.DaoerParkingLotManager;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.DaoerClient;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerCarPort;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglot.daoerparkinglot.controller.carport.request.BlankCarRequest;
import com.yfkyplatform.parkinglot.daoerparkinglot.controller.carport.request.CarFeePayRequest;
import com.yfkyplatform.parkinglot.daoerparkinglot.controller.carport.request.ControlChannelRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车场控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"车场"})
@RequestMapping(value = "/api/carport")
@RestController
@Validated
public class CarPortController {

    private IDaoerCarPort api;

    public CarPortController(DaoerParkingLotManager manager){
        this.api=manager.parkingLot("DaoerTest");
    }

    @ApiOperation(value = "车位查询")
    @GetMapping
    public DaoerBaseResp<List<CarportResult>> getCarport(){
        return api.getCarPortInfo().block();
    }

    @ApiOperation(value = "获取临时车缴纳金额")
    @GetMapping("/fee")
    public DaoerBaseResp<CarFeeResult> getCarFee(String carNo){
        return api.getCarFeeInfo(carNo).block();
    }

    @ApiOperation(value = "根据通道号获取缴纳金额")
    @GetMapping("/fee/channel")
    public DaoerBaseResp<CarFeeResult> getChannelCarFee(String channelId,String carNo,String openId){
        return api.getChannelCarFee(channelId,carNo,openId).block();
    }

    @ApiOperation(value = "临停缴费支付")
    @PostMapping("/fee")
    public DaoerBaseResp payCarFee(@RequestBody CarFeePayRequest request){
        return api.payCarFeeAccess(request.getCarNo(),request.getPayTime(), request.getDuration(), request.getTotalAmount(),request.getDisAmount(),
                request.getPaymentType(),request.getPayType(),request.getPaymentTnx(),request.getCouponAmount(),request.getChannelId()).block();
    }

    @ApiOperation(value = "无牌车入场")
    @PostMapping("/blankcar/in")
    public DaoerBaseResp<BlankCarInResult> blankCarIn(@RequestBody BlankCarRequest request){
        return api.blankCarIn(request.getOpenId(),request.getScanType(), request.getChannelId()).block();
    }

    @ApiOperation(value = "无牌车出场")
    @PostMapping("/blankcar/out")
    public DaoerBaseResp<BlankCarOutResult> blankCarOut(@RequestBody BlankCarRequest request){
        return api.blankCarOut(request.getOpenId(),request.getScanType(), request.getChannelId()).block();
    }

    @ApiOperation(value = "获取入场记录")
    @GetMapping("/in")
    public DaoerBaseResp<CarInOrOutResult<CarInData>> getCarIn(@ApiParam(value = "车牌")String carNo,
                                                               @ApiParam(value = "开始时间 yyyy-MM-dd HH:mm:ss")String startTime,
                                                               @ApiParam(value = "结束时间 yyyy-MM-dd HH:mm:ss")String endTime,
                                                               @ApiParam(value = "页码", required = true)int pageNum,
                                                               @ApiParam(value = "页大小", required = true)int pageSize){
        return api.getCarInInfo(carNo,startTime,endTime,pageNum,pageSize).block();
    }

    @ApiOperation(value = "获取出场记录")
    @GetMapping("/out")
    public DaoerBaseResp<CarInOrOutResult<CarOutData>> getCarOut(String carNo, String startTime, String endTime, int pageNum, int pageSize){
        return api.getCarOutInfo(carNo,startTime,endTime,pageNum,pageSize).block();
    }

    @ApiOperation(value = "获取通道列表")
    @GetMapping("/channels")
    public DaoerBaseResp<List<ChannelResult>> getChannels(){
        return api.getChannelsInfo().block();
    }

    @ApiOperation(value = "获取通道状态")
    @GetMapping("/channels/State")
    public DaoerBaseResp<List<ChannelStateResult>> getChannelStates(){
        return api.getChannelStates().block();
    }

    @ApiOperation(value = "道闸开关操作")
    @PostMapping("/channels")
    public DaoerBaseResp<List<ChannelStatusResult>> controlChannel(@RequestBody ControlChannelRequest request){
        return api.controlChannel(request.getChannelId(), request.getChannelIdStatus()).block();
    }
}
