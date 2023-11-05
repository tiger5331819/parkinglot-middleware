package com.parkinglotmiddleware.api.web.bussiness;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.parkinglotmiddleware.api.web.bussiness.req.CleanCarReq;
import com.parkinglotmiddleware.api.web.bussiness.resp.CleanCarListResp;
import com.parkinglotmiddleware.api.web.bussiness.resp.CleanCarResp;
import com.parkinglotmiddleware.carpark.daoer.controller.tools.resp.CarCheckResultResp;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.parkinglotmiddleware.universal.testbox.TestBox;
import com.parkinglotmiddleware.universal.web.SaaSWebClient;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Tag(name = "测试控制器")
@RequestMapping(value= "api/test")
@IgnoreCommonResponse
@RestController
public class TestController {

    private final TestBox testBox;

    private final ParkingLotManagerFactory factory;

    public TestController(TestBox testBox, ParkingLotManagerFactory factory) {
        this.testBox = testBox;
        this.factory = factory;
    }

    private ParkingLotPod findByDescription(Integer parkingLotManager, String parkingLotDescription) {
        return factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManager).getName()).findParkingLotByDescription(parkingLotDescription).stream().findFirst().get();
    }

    @Operation(summary =  "批量人工清场")
    @PostMapping("/cleanCar")
    public CleanCarListResp cleanCar(@RequestBody CleanCarReq req) throws JsonProcessingException {

        SaaSWebClient saaSWebClient = testBox.saasClient("prod");
        Map<String, Object> resultJson = saaSWebClient.post("{\"parkinglotId\":\"" + req.getParkingLotId() + "\",\"orderPayState\":1000,\"chargeTimeStart\":\"" + req.getChargeTimeStart() + "\",\"chargeTimeClose\":\"" + req.getChargeTimeClose() + "\",\"plateNumberNotNull\":true,\"current\":1,\"size\":" + req.getSize() + "}", "mgntpc/pc/order/page", req.getToken());

        List<Map<String, Object>> records = (List<Map<String, Object>>) resultJson.get("records");
        List<Long> orderId = records.stream().map(item -> (Long) item.get("orderId")).collect(Collectors.toList());

        List<CleanCarResp> cleanCarRespList = orderId.stream().map(item -> {

            try {
                saaSWebClient.post("{\"explain\":\"人工清场，取消订单\",\"orderId\":" + item + "}", "mgntpc/pc/order/outsideClean", req.getToken());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            CleanCarResp resp = new CleanCarResp();
            resp.setSuccess(true);
            resp.setOrderId(item);
            return resp;
        }).collect(Collectors.toList());

        CleanCarListResp resp = new CleanCarListResp();
        resp.setData(cleanCarRespList);
        resp.setTotal(cleanCarRespList.size());

        return resp;
    }

    @Operation(summary =  "检查车辆是否在场")
    @PostMapping("/{parkingLotManager}/{parkingLotDescription}/checkCar")
    public List<CarCheckResultResp> checkCar(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotDescription, @RequestBody String data) {
        String[] carNos = data.split("\r\n");
        ParkingLotPod parkingLot = findByDescription(parkingLotManager, parkingLotDescription);
        List<CarCheckResultResp> resultResps = new LinkedList<>();

        for (String carNo : carNos) {

            Car car = parkingLot.carPort().getCar(carNo);
            CarCheckResultResp resp = new CarCheckResultResp();
            resp.setCarNo(carNo);
            resp.setIn(StrUtil.isNotBlank(car.getInId()));

            resultResps.add(resp);
        }

        return resultResps;
    }
}
