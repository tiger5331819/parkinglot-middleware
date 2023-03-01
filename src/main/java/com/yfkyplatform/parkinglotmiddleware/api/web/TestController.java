package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"测试控制器"})
@RequestMapping(value = "api/test")
@IgnoreCommonResponse
@RestController
public class TestController {

    private final Environment env;

    public TestController(ICarPortService carPortService, Environment env) {
        this.env = env;
    }

    @ApiOperation(value = "获取版本信息")
    @GetMapping("/version")
    public String getVersion() {
        return env.getProperty("app.version");
    }
}
