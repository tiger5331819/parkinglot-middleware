package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotManager;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerCarPort;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.PageModel;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.*;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.BlankCarRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.ControlChannelRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.DueCarConfigurationRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.carport.request.DueCarSuccessRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.monthly.request.LockMonthlyCarRequest;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 车场控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "车场")
@RequestMapping(value= "/Daoer/api/{parkingLotId}/carport")
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

    @Operation(summary =  "车位查询")
    @GetMapping
    public DaoerBaseResp<List<CarportResult>> getCarport(@PathVariable String parkingLotId) {
        return api(parkingLotId).getCarPortInfo().block();
    }

    @Operation(summary =  "无牌车入场")
    @PostMapping("/blankcar/in")
    public DaoerBaseResp<BlankCarInResult> blankCarIn(@PathVariable String parkingLotId, @RequestBody BlankCarRequest request) {
        return api(parkingLotId).blankCarIn(request.getOpenId(), request.getScanType(), request.getChannelId()).block();
    }

    @Operation(summary =  "无牌车出场")
    @PostMapping("/blankcar/out")
    public DaoerBaseResp<BlankCarOutResult> blankCarOut(@PathVariable String parkingLotId, @RequestBody BlankCarRequest request) {
        return api(parkingLotId).blankCarOut(request.getOpenId(), request.getScanType(), request.getChannelId()).block();
    }

    @Operation(summary =  "获取入场记录")
    @GetMapping("/in")
    public DaoerBaseResp<PageModel<CarInData>> getCarIn(@PathVariable String parkingLotId,
                                                        @Parameter(description =  "车牌") String carNo,
                                                        @Parameter(description =  "开始时间 yyyy-MM-dd HH:mm:ss") String startTime,
                                                        @Parameter(description =  "结束时间 yyyy-MM-dd HH:mm:ss") String endTime,
                                                        @Parameter(description =  "页码", required = true) int pageNum,
                                                        @Parameter(description =  "页大小", required = true) int pageSize) {
        return api(parkingLotId).getCarInInfo(carNo, startTime, endTime, pageNum, pageSize).block();
    }

    @Operation(summary =  "获取出场记录")
    @GetMapping("/out")
    public DaoerBaseResp<PageModel<CarOutData>> getCarOut(@PathVariable String parkingLotId, String carNo, String startTime, String endTime, int pageNum, int pageSize) {
        return api(parkingLotId).getCarOutInfo(carNo, startTime, endTime, pageNum, pageSize).block();
    }

    @Operation(summary =  "获取通道列表")
    @GetMapping("/channels")
    public DaoerBaseResp<List<ChannelResult>> getChannels(@PathVariable String parkingLotId) {
        return api(parkingLotId).getChannelsInfo().block();
    }

    @Operation(summary =  "获取通道状态")
    @GetMapping("/channels/State")
    public DaoerBaseResp<List<ChannelStateResult>> getChannelStates(@PathVariable String parkingLotId) {
        return api(parkingLotId).getChannelStates().block();
    }

    @Operation(summary =  "道闸开关操作")
    @PostMapping("/channels")
    public DaoerBaseResp<List<ChannelStatusResult>> controlChannel(@PathVariable String parkingLotId, @RequestBody ControlChannelRequest request) {
        return api(parkingLotId).controlChannel(request.getChannelId(), request.getChannelIdStatus()).block();
    }

    @Operation(summary =  "锁车/解锁")
    @PatchMapping(value= "/{carNo}/lock")
    public DaoerBaseResp<CarLockResult> lockCar(@PathVariable String parkingLotId, @Parameter(description =  "车牌号码") @PathVariable String carNo, @RequestBody LockMonthlyCarRequest request) {
        return api(parkingLotId).lockMonthlyCar(carNo, request.getStatus()).block();
    }

    @Operation(summary =  "锁车状态")
    @GetMapping(value= "/{carNo}/lock")
    public DaoerBaseResp<CarLockResult> carLockInfo(@PathVariable String parkingLotId, @Parameter(description =  "车牌号码") @PathVariable String carNo) {
        return api(parkingLotId).monthlyCarLockInfo(carNo).block();
    }

    @Operation(summary =  "补缴成功")
    @PostMapping("/duecar/success")
    public DaoerBaseResp dueCarSuccess(@PathVariable String parkingLotId, @RequestBody DueCarSuccessRequest request) {
        return api(parkingLotId).dueCarSuccess(request.getChannelId(), request.getCarNo()).block();
    }

    @Operation(summary =  "同步联动催缴配置信息")
    @PostMapping("/duecar/configuration")
    public DaoerBaseResp configDueCar(@PathVariable String parkingLotId, @RequestBody DueCarConfigurationRequest request) {
        return api(parkingLotId).configDueCar(request.getUrgepayNotIn(), request.getUrgepayNotOut(),request.getStartTime(),request.getCloseTime()).block();
    }
}
