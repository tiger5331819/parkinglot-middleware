package com.yfkyplatform.parkinglot.api.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RestController
public class TestController {

    public TestController(){
    }

    @ApiOperation(value = "路径参数测试")
    @GetMapping("/test/{txt}")
    public String testPathVariable(@PathVariable String txt){
        return txt;
    }

    @ApiOperation(value = "查询字符串测试")
    @GetMapping("/test")
    public String stringTest(String txt){
        return txt;
    }
}
