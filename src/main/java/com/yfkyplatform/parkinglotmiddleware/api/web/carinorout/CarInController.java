package com.yfkyplatform.parkinglotmiddleware.api.web.carinorout;

import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.mq.CarInNotice;
import com.yfkyplatform.parkinglotmiddleware.api.web.carinorout.daoer.DaoerCarInMessage;
import com.yfkyplatform.parkinglotmiddleware.api.web.carinorout.daoer.DaoerParkingLotPostResp;
import com.yfkyplatform.parkinglotmiddleware.domain.service.order.OrderExtensionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
@Api(tags = {"停车场车辆入场控制器"})
@RequestMapping(value = "api/carinmessage")
@IgnoreCommonResponse
@RestController
public class CarInController {

    private OrderExtensionService orderExtensionService;
    private RocketMQTemplate rocketMQTemplate;

    public CarInController(OrderExtensionService orderExtensionService,RocketMQTemplate rocketMQTemplate){
        this.orderExtensionService=orderExtensionService;
        this.rocketMQTemplate=rocketMQTemplate;
    }

    @ApiOperation(value = "道尔车辆入场通知")
    @PostMapping("/daoer")
    public DaoerParkingLotPostResp daoerCarInMessage(@RequestBody DaoerCarInMessage message){
        CarInNotice carInNotice=orderExtensionService.makeCarInNotice("Daoer", message.getParkingNo(), message.getCarNo(), message.getCardTypeId(), message.getInTime(),message.getInPic());
        rocketMQTemplate.asyncSend("parkingLot-carIn", carInNotice, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                log.error("send CarIn fail; {}", throwable.getMessage());
            }
        });
        return new DaoerParkingLotPostResp();
    }

}
