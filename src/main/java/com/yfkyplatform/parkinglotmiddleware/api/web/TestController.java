package com.yfkyplatform.parkinglotmiddleware.api.web;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.ChannelResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.ChannelStateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    public TestController(){
    }

    @ApiOperation(value = "路径参数测试")
    @GetMapping("text/{txt}")
    public String testPathVariable(@PathVariable String txt) {
        return txt;
    }

    @ApiOperation(value = "查询字符串测试")
    @GetMapping("text")
    public String stringTest(String txt) {
        return txt;
    }

    @ApiOperation(value = "获取通道列表")
    @GetMapping("/channels")
    public List<ChannelResult> getChannels() {
        List<ChannelResult> result = new ArrayList<>();
        ChannelResult data = new ChannelResult();
        data.setChannelId("123");
        data.setChannelName("test");
        data.setType(1);

        result.add(data);
        ChannelResult data2 = new ChannelResult();
        data2.setChannelId("090");
        data2.setChannelName("test2");
        data.setType(1);
        result.add(data2);

        return result;
    }

    @ApiOperation(value = "获取通道状态")
    @GetMapping("/channels/State")
    public List<ChannelStateResult> getChannelStates() {
        List<ChannelStateResult> result = new ArrayList<>();
        ChannelStateResult data = new ChannelStateResult();
        data.setChannelId("123");
        data.setChannelName("test");

        result.add(data);
        ChannelStateResult data2 = new ChannelStateResult();
        data2.setChannelId("090");
        data2.setChannelName("test2");
        result.add(data2);

        return result;
    }
}
