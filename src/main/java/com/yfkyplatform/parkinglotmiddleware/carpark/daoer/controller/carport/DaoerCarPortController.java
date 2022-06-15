package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.BlankCarRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.CarFeePayRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.ControlChannelRequest;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车场控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"车场"})
@RequestMapping(value = "/Daoer/api/{parkingLotId}/carport")
@RestController
public class DaoerCarPortController {

    private ParkingLotManager manager;

    private IDaoerCarPort api(String parkingLotId){
        return manager.parkingLot(parkingLotId).client();
    }

    public DaoerCarPortController(DaoerParkingLotManager manager){
        this.manager=manager;
    }

    @ApiOperation(value = "车位查询")
    @GetMapping
    public DaoerBaseResp<List<CarportResult>> getCarport(@PathVariable String parkingLotId){
        return api(parkingLotId).getCarPortInfo().block();
    }

    @ApiOperation(value = "获取临时车缴纳金额")
    @GetMapping("/fee")
    public DaoerBaseResp<CarFeeResult> getCarFee(@PathVariable String parkingLotId,String carNo){
        return api(parkingLotId).getCarFeeInfo(carNo).block();
    }

    @ApiOperation(value = "根据通道号获取缴纳金额")
    @GetMapping("/fee/channel")
    public DaoerBaseResp<CarFeeResult> getChannelCarFee(@PathVariable String parkingLotId,String channelId,String carNo,String openId){
        return api(parkingLotId).getChannelCarFee(channelId,carNo,openId).block();
    }

    @ApiOperation(value = "临停缴费支付")
    @PostMapping("/fee")
    public DaoerBaseResp payCarFee(@PathVariable String parkingLotId,@RequestBody CarFeePayRequest request){
        return api(parkingLotId).payCarFeeAccess(request.getCarNo(),request.getPayTime(), request.getDuration(), request.getTotalAmount(),request.getDisAmount(),
                request.getPaymentType(),request.getPayType(),request.getPaymentTnx(),request.getCouponAmount(),request.getChannelId()).block();
    }

    @ApiOperation(value = "无牌车入场")
    @PostMapping("/blankcar/in")
    public DaoerBaseResp<BlankCarInResult> blankCarIn(@PathVariable String parkingLotId,@RequestBody BlankCarRequest request){
        return api(parkingLotId).blankCarIn(request.getOpenId(),request.getScanType(), request.getChannelId()).block();
    }

    @ApiOperation(value = "无牌车出场")
    @PostMapping("/blankcar/out")
    public DaoerBaseResp<BlankCarOutResult> blankCarOut(@PathVariable String parkingLotId,@RequestBody BlankCarRequest request){
        return api(parkingLotId).blankCarOut(request.getOpenId(),request.getScanType(), request.getChannelId()).block();
    }

    @ApiOperation(value = "获取入场记录")
    @GetMapping("/in")
    public DaoerBaseResp<CarInOrOutResult<CarInData>> getCarIn(@PathVariable String parkingLotId,
                                                               @ApiParam(value = "车牌")String carNo,
                                                               @ApiParam(value = "开始时间 yyyy-MM-dd HH:mm:ss")String startTime,
                                                               @ApiParam(value = "结束时间 yyyy-MM-dd HH:mm:ss")String endTime,
                                                               @ApiParam(value = "页码", required = true)int pageNum,
                                                               @ApiParam(value = "页大小", required = true)int pageSize){
        return api(parkingLotId).getCarInInfo(carNo,startTime,endTime,pageNum,pageSize).block();
    }

    @ApiOperation(value = "获取出场记录")
    @GetMapping("/out")
    public DaoerBaseResp<CarInOrOutResult<CarOutData>> getCarOut(@PathVariable String parkingLotId,String carNo, String startTime, String endTime, int pageNum, int pageSize){
        return api(parkingLotId).getCarOutInfo(carNo,startTime,endTime,pageNum,pageSize).block();
    }

    @ApiOperation(value = "获取通道列表")
    @GetMapping("/channels")
    public DaoerBaseResp<List<ChannelResult>> getChannels(@PathVariable String parkingLotId){
        return api(parkingLotId).getChannelsInfo().block();
    }

    @ApiOperation(value = "获取通道状态")
    @GetMapping("/channels/State")
    public DaoerBaseResp<List<ChannelStateResult>> getChannelStates(@PathVariable String parkingLotId){
        return api(parkingLotId).getChannelStates().block();
    }

    @ApiOperation(value = "道闸开关操作")
    @PostMapping("/channels")
    public DaoerBaseResp<List<ChannelStatusResult>> controlChannel(@PathVariable String parkingLotId,@RequestBody ControlChannelRequest request){
        return api(parkingLotId).controlChannel(request.getChannelId(), request.getChannelIdStatus()).block();
    }
}
