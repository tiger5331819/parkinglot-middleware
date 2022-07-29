package com.yfkyplatform.parkinglotmiddleware.api.web.carinorout;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.web.carinorout.daoer.DaoerParkingLotPostResp;
import com.yfkyplatform.parkinglotmiddleware.configuration.redis.RedisTool;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

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
    @PostMapping("/daoer")
    public DaoerParkingLotPostResp daoerCarInMessage(@RequestBody Object message) {
        log.debug("message test");
        redis.set("carIn:" + message.hashCode(), message, Duration.ofHours(12));
        return new DaoerParkingLotPostResp();
    }

}
