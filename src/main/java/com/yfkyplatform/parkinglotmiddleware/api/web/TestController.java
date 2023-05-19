package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.manager.IManagerService;
import com.yfkyplatform.parkinglotmiddleware.api.manager.response.ParkingLotCfgRpcResp;
import com.yfkyplatform.parkinglotmiddleware.universal.extension.IExtensionFuction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"测试控制器"})
@RequestMapping(value = "test")
@IgnoreCommonResponse
@RestController
public class TestController {

    private final IManagerService managerService;

    private final IExtensionFuction extensionFuction;

    public TestController(IManagerService managerService, IExtensionFuction extensionFuction) {
        this.managerService = managerService;
        this.extensionFuction = extensionFuction;
    }

    @ApiOperation(value = "test1")
    @GetMapping("/test1")
    public List<ParkingLotCfgRpcResp> test1() {
        return managerService.parkingMangerConfiguration(null, "2003001200000002");
    }

    @ApiOperation(value = "test2")
    @GetMapping("/test2")
    public List<ParkingLotCfgRpcResp> test2() {
        extensionFuction.setToken();
        return managerService.parkingMangerConfiguration(null, "2006001120010000");
    }
}
