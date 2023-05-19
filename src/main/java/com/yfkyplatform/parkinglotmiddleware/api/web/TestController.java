package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.manager.IManagerService;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.IMonthlyCarService;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.response.MonthlyCarMessageResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.universal.extension.IExtensionFuction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private final IMonthlyCarService monthlyCarService;

    public TestController(IManagerService managerService, IExtensionFuction extensionFuction, IMonthlyCarService monthlyCarService) {
        this.managerService = managerService;
        this.extensionFuction = extensionFuction;
        this.monthlyCarService = monthlyCarService;
    }

    @ApiOperation(value = "test1")
    @GetMapping("/test1")
    public MonthlyCarMessageResultRpcResp test1() {
        return monthlyCarService.monthlyCarInfo(4, "2003001200000002", "桂JJJJJJ");
    }

    @ApiOperation(value = "test2")
    @GetMapping("/test2")
    public MonthlyCarMessageResultRpcResp test2() {
        extensionFuction.setToken();
        return monthlyCarService.monthlyCarInfo(4, "2006001120010000", "桂JJJJJJ");
    }
}
