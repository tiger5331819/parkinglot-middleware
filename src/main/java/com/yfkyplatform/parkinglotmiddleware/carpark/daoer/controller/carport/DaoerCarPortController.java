package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.PageModel;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.BlankCarRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.ControlChannelRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request.LockMonthlyCarRequest;
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
@IgnoreCommonResponse
@RestController
public class DaoerCarPortController {

    private final ParkingLotManager manager;

    private IDaoerCarPort api(String parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    public DaoerCarPortController(DaoerParkingLotManager manager){
        this.manager=manager;
    }

    @ApiOperation(value = "车位查询")
    @GetMapping
    public DaoerBaseResp<List<CarportResult>> getCarport(@PathVariable String parkingLotId) {
        return api(parkingLotId).getCarPortInfo().block();
    }

    @ApiOperation(value = "无牌车入场")
    @PostMapping("/blankcar/in")
    public DaoerBaseResp<BlankCarInResult> blankCarIn(@PathVariable String parkingLotId, @RequestBody BlankCarRequest request) {
        return api(parkingLotId).blankCarIn(request.getOpenId(), request.getScanType(), request.getChannelId()).block();
    }

    @ApiOperation(value = "无牌车出场")
    @PostMapping("/blankcar/out")
    public DaoerBaseResp<BlankCarOutResult> blankCarOut(@PathVariable String parkingLotId, @RequestBody BlankCarRequest request) {
        return api(parkingLotId).blankCarOut(request.getOpenId(), request.getScanType(), request.getChannelId()).block();
    }

    @ApiOperation(value = "获取入场记录")
    @GetMapping("/in")
    public DaoerBaseResp<PageModel<CarInData>> getCarIn(@PathVariable String parkingLotId,
                                                        @ApiParam(value = "车牌") String carNo,
                                                        @ApiParam(value = "开始时间 yyyy-MM-dd HH:mm:ss") String startTime,
                                                        @ApiParam(value = "结束时间 yyyy-MM-dd HH:mm:ss") String endTime,
                                                        @ApiParam(value = "页码", required = true) int pageNum,
                                                        @ApiParam(value = "页大小", required = true) int pageSize) {
        return api(parkingLotId).getCarInInfo(carNo, startTime, endTime, pageNum, pageSize).block();
    }

    @ApiOperation(value = "获取出场记录")
    @GetMapping("/out")
    public DaoerBaseResp<PageModel<CarOutData>> getCarOut(@PathVariable String parkingLotId, String carNo, String startTime, String endTime, int pageNum, int pageSize) {
        return api(parkingLotId).getCarOutInfo(carNo, startTime, endTime, pageNum, pageSize).block();
    }

    @ApiOperation(value = "获取通道列表")
    @GetMapping("/channels")
    public DaoerBaseResp<List<ChannelResult>> getChannels(@PathVariable String parkingLotId) {
        return api(parkingLotId).getChannelsInfo().block();
    }

    @ApiOperation(value = "获取通道状态")
    @GetMapping("/channels/State")
    public DaoerBaseResp<List<ChannelStateResult>> getChannelStates(@PathVariable String parkingLotId) {
        return api(parkingLotId).getChannelStates().block();
    }

    @ApiOperation(value = "道闸开关操作")
    @PostMapping("/channels")
    public DaoerBaseResp<List<ChannelStatusResult>> controlChannel(@PathVariable String parkingLotId, @RequestBody ControlChannelRequest request) {
        return api(parkingLotId).controlChannel(request.getChannelId(), request.getChannelIdStatus()).block();
    }

    @ApiOperation(value = "锁车/解锁")
    @PatchMapping(value = "/{carNo}/lock")
    public DaoerBaseResp<CarLockResult> lockCar(@PathVariable String parkingLotId, @ApiParam(value = "车牌号码") @PathVariable String carNo, @RequestBody LockMonthlyCarRequest request) {
        return api(parkingLotId).lockMonthlyCar(carNo, request.getStatus()).block();
    }

    @ApiOperation(value = "锁车状态")
    @GetMapping(value = "/{carNo}/lock")
    public DaoerBaseResp<CarLockResult> carLockInfo(@PathVariable String parkingLotId, @ApiParam(value = "车牌号码") @PathVariable String carNo) {
        return api(parkingLotId).monthlyCarLockInfo(carNo).block();
    }
}
