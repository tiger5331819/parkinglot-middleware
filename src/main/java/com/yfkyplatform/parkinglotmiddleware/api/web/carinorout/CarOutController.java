package com.yfkyplatform.parkinglotmiddleware.api.web.carinorout;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.web.carinorout.daoer.DaoerParkingLotPostResp;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"停车场车辆出场控制器"})
@RequestMapping(value = "api/caroutmessage")
@IgnoreCommonResponse
@RestController
public class CarOutController {

    private final RedisTool redis;

    public CarOutController(RedisTool redis) {
        this.redis = redis;
    }

/*    @ApiOperation(value = "道尔车辆出场通知")
    @PostMapping("/daoer")
    public DaoerParkingLotPostResp daoerCarInMessage(@RequestBody DaoerCarOutMessage message) {
        redis.set("carOut:" + message.hashCode(), message);
        return new DaoerParkingLotPostResp();
    }*/

    @ApiOperation(value = "道尔车辆出场通知")
    @PostMapping("/daoer")
    public DaoerParkingLotPostResp daoerCarInMessage(@RequestBody String message) {
        redis.set("carOut:" + message.hashCode(), message);
        return new DaoerParkingLotPostResp();
    }

}
