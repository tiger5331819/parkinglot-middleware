package com.yfkyplatform.parkinglotmiddleware.api.web;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyframework.common.mvc.advice.commonresponsebody.IgnoreCommonResponse;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.ChannelCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultByListRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.web.req.PayAccessReq;
import com.yfkyplatform.parkinglotmiddleware.api.web.resp.CarResp;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.ChannelInfoResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport.CarPortMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.yfkyplatform.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具控制器
 *
 * @author Suhuyuan
 */
@Slf4j
@Api(tags = {"工具控制器"})
@RequestMapping(value = "api/tool")
@IgnoreCommonResponse
@RestController
public class ToolController {

    private final ICarPortService carPortService;

    private final ParkingLotManagerFactory factory;

    public ToolController(ICarPortService carPortService, ParkingLotManagerFactory factory) {
        this.carPortService = carPortService;
        this.factory = factory;
    }

    private ParkingLotPod findByDescription(Integer parkingLotManager, String parkingLotDescription) {
        return factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManager).getName()).findParkingLotByDescription(parkingLotDescription).stream().findFirst().get();
    }

    @ApiOperation(value = "获取车场信息")
    @GetMapping("/{parkingLotManager}/{parkingLotDescription}/message")
    public List<CarPortMessage> getParkingLotMessage(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotDescription) {

        List<ParkingLotPod> parkingLotList = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManager).getName()).findParkingLotByDescription(parkingLotDescription);
        return parkingLotList.stream().map(item -> item.carPort().parkingLotMessage()).collect(Collectors.toList());
    }

    @ApiOperation(value = "获取车辆缴纳金额")
    @GetMapping("/{parkingLotManager}/{parkingLotDescription}/fee")
    public List<CarOrderResultRpcResp> getCarFee(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotDescription, String carNo, String openId) {
        List<CarOrderResultRpcResp> respList = new LinkedList<>();
        ParkingLotPod parkingLot = findByDescription(parkingLotManager, parkingLotDescription);
        CarPortMessage carPortMessage = parkingLot.carPort().parkingLotMessage();
        if (StrUtil.isBlank(carNo)) {
            for (ChannelInfoResult channelInfo : carPortMessage.getChannelList().stream().filter(item -> item.getType() == 1).collect(Collectors.toList())) {
                ChannelCarRpcReq channelCarRpcReq = new ChannelCarRpcReq();
                channelCarRpcReq.setOpenId(openId);
                channelCarRpcReq.setScanType(1);
                channelCarRpcReq.setChannelId(channelInfo.getChannelId());

                CarOrderResultByListRpcResp resp = carPortService.getChannelCarFee(parkingLotManager, carPortMessage.getConfiguration().getId(), channelCarRpcReq);
                if (StrUtil.isNotBlank(resp.getCarNo())) {
                    respList.add(resp);
                }
            }
        } else {
            respList.add(carPortService.getCarFee(parkingLotManager, carPortMessage.getConfiguration().getId(), carNo));
        }
        return respList;
    }

    @ApiOperation(value = "直接支付金额")
    @GetMapping("/{parkingLotManager}/{parkingLotDescription}/carport/FeeTest")
    public Boolean payAccessTest(@PathVariable Integer parkingLotManager, @PathVariable String parkingLotDescription, PayAccessReq payAccess) {
        CarOrderResultRpcResp rpcResp = null;
        ParkingLotPod parkingLot = findByDescription(parkingLotManager, parkingLotDescription);
        CarPortMessage carPortMessage = parkingLot.carPort().parkingLotMessage();
        if (StrUtil.isBlank(payAccess.getCarNo())) {

            for (ChannelInfoResult channelInfo : carPortMessage.getChannelList().stream().filter(item -> item.getType() == 1).collect(Collectors.toList())) {
                ChannelCarRpcReq channelCarRpcReq = new ChannelCarRpcReq();
                channelCarRpcReq.setOpenId(payAccess.getOpenId());
                channelCarRpcReq.setScanType(payAccess.getScanType());
                channelCarRpcReq.setChannelId(channelInfo.getChannelId());

                CarOrderResultByListRpcResp resp = carPortService.getChannelCarFee(parkingLotManager, carPortMessage.getConfiguration().getId(), channelCarRpcReq);
                if (StrUtil.isNotBlank(resp.getCarNo())) {
                    rpcResp = resp;
                    break;
                }
            }
        } else {
            rpcResp = carPortService.getCarFee(parkingLotManager, carPortMessage.getConfiguration().getId(), payAccess.getCarNo());
        }


        if (ObjectUtil.isNull(rpcResp)) {
            return false;
        }

        OrderPayMessageRpcReq orderPayMessageRpcReq = new OrderPayMessageRpcReq();
        orderPayMessageRpcReq.setPayTime(rpcResp.getCreateTime());
        orderPayMessageRpcReq.setDiscountFee(rpcResp.getDiscountFee());
        orderPayMessageRpcReq.setPayType(2000);
        orderPayMessageRpcReq.setPaymentTransactionId(String.valueOf(IdUtil.getSnowflake().nextId()));
        orderPayMessageRpcReq.setPayFee(ObjectUtil.isNull(payAccess.getPayFee()) ? rpcResp.getPayFee() : payAccess.getPayFee().movePointRight(2));
        orderPayMessageRpcReq.setInId(rpcResp.getInId());

        return carPortService.payAccess(parkingLotManager, carPortMessage.getConfiguration().getId(), rpcResp.getCarNo(), orderPayMessageRpcReq);
    }

    @ApiOperation(value = "获取车辆信息")
    @GetMapping("/car/{carNo}")
    public List<CarResp> getCar(@PathVariable String carNo) {
        List<ParkingLotConfiguration> configurationList = factory.getParkingLotConfiguration(null, null);

        List<CarResp> respList = new LinkedList<>();
        for (ParkingLotConfiguration configuration : configurationList) {
            ParkingLotPod parkingLot = factory.manager(configuration.getManagerType()).parkingLot(configuration.getId());
            Car car = parkingLot.carPort().getCar(carNo);
            if (StrUtil.isNotBlank(car.getInId())) {
                CarResp carResp = BeanUtil.copyProperties(car, CarResp.class);
                carResp.setParkingLotDescription(parkingLot.configuration().getDescription());
                respList.add(carResp);
            }

        }

        return respList;
    }
}
