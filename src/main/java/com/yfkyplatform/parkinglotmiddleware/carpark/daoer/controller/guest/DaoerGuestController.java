package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.guest;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api.IDaoerGuest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.guest.GuestResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.guest.request.ChangeGuestRequest;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.controller.guest.request.CreateGuestRequest;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManager;
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
@RequestMapping(value = "/Daoer/api/{parkingLotId}/guest")
@IgnoreCommonResponse
@RestController
public class DaoerGuestController {
    private final ParkingLotManager manager;

    private IDaoerGuest api(Long parkingLotId) {
        return manager.parkingLot(parkingLotId).client();
    }

    public DaoerGuestController(ParkingLotManager manager){
        this.manager=manager;
    }

    @ApiOperation(value = "创建访客")
    @PostMapping(value = "/guest")
    public DaoerBaseResp<GuestResult> createGuest(@PathVariable Long parkingLotId, @RequestBody CreateGuestRequest request) {
        return api(parkingLotId).createGuest(request.getGuestName(), request.getCarNo(), request.getVisitTime(), request.getPhone(), request.getDescription()).block();
    }

    @ApiOperation(value = "更新访客")
    @PatchMapping(value = "/guest")
    public DaoerBaseResp changeGuest(@PathVariable Long parkingLotId, @RequestBody ChangeGuestRequest request) {
        return api(parkingLotId).changeGuestMessage(request.getObjectId(), request.getGuestName(), request.getVisitTime(), request.getPhone(), request.getDescription()).block();
    }

    @ApiOperation(value = "取消访客")
    @DeleteMapping(value = "/guest")
    public DaoerBaseResp removeGuest(@PathVariable Long parkingLotId, @ApiParam(value = "唯一记录标识", required = true) @NotNull(message = "唯一记录标识不能为空") String objectId) {
        return api(parkingLotId).removeGuestMessage(objectId).block();
    }
}
