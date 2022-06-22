package com.yfkyplatform.parkinglotmiddleware.api.web.carinorout;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.web.carinorout.daoer.DaoerParkingLotPostResp;
import com.yfkyplatform.parkinglotmiddleware.configuartion.redis.RedisTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"停车场车辆入场控制器"})
@RequestMapping(value = "api/carinmessage")
@IgnoreCommonResponse
@RestController
public class CarInController {

    private final RedisTool redis;

    public CarInController(RedisTool redis) {
        this.redis = redis;
    }

    @ApiOperation(value = "道尔车辆入场通知")
    @PostMapping("/daoer/{ttt}")
    public DaoerParkingLotPostResp daoerCarInMessage(@RequestBody Object message, @PathVariable int ttt) {
        log.debug("message test");
        redis.set("carIn:" + message.hashCode(), message);
        return new DaoerParkingLotPostResp();
    }

}
