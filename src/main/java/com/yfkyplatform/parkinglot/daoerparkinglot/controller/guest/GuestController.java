package com.yfkyplatform.parkinglot.daoerparkinglot.controller.guest;

import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerTool;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.guest.GuestResult;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.api.IDaoerGuest;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglot.daoerparkinglot.controller.guest.request.ChangeGuestRequest;
import com.yfkyplatform.parkinglot.daoerparkinglot.controller.guest.request.CreateGuestRequest;
import com.yfkyplatform.parkinglot.domain.manager.ParkingLotManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 访客控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"访客"})
@RequestMapping(value = "/api/guest")
@RestController
public class GuestController {

    private final IDaoerGuest api;

    public GuestController(ParkingLotManager manager){
        this.api=(IDaoerGuest)manager.parkingLot("DaoerTest");
    }

    @ApiOperation(value = "创建访客")
    @PostMapping(value = "/guest")
    public DaoerBaseResp<GuestResult> createGuest(@RequestBody CreateGuestRequest request){
        return api.createGuest(request.getGuestName(), request.getCarNo(), request.getVisitTime(), request.getPhone(), request.getDescription()).block();
    }

    @ApiOperation(value = "更新访客")
    @PatchMapping(value = "/guest")
    public DaoerBaseResp changeGuest(@RequestBody ChangeGuestRequest request){
        return api.changeGuestMessage(request.getObjectId(), request.getGuestName(),request.getVisitTime(), request.getPhone(), request.getDescription()).block();
    }

    @ApiOperation(value = "取消访客")
    @DeleteMapping(value = "/guest")
    public DaoerBaseResp removeGuest(@ApiParam(value = "唯一记录标识", required = true)@NotNull(message = "唯一记录标识不能为空") String objectId){
        return api.removeGuestMessage(objectId).block();
    }
}
