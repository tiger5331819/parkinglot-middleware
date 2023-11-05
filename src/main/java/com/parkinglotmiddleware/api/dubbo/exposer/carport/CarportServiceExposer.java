package com.parkinglotmiddleware.api.dubbo.exposer.carport;

import cn.hutool.core.bean.BeanUtil;
import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.ICarPortService;
import com.parkinglotmiddleware.api.dubbo.service.carport.request.BlankCarRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.request.CarInfoRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carport.response.CarMessageRpcResp;
import com.parkinglotmiddleware.api.dubbo.service.carport.response.CarPortSpaceRpcResp;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.parkinglotmiddleware.domain.manager.container.service.ability.carport.ICarPortAblitity;
import com.parkinglotmiddleware.domain.manager.container.service.carport.CarPortMessage;
import com.parkinglotmiddleware.domain.manager.container.service.carport.CarPortService;
import com.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyframework.util.context.AccountRpcContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * 车场服务
 *
 * @author Suhuyuan
 */
@DubboService(timeout = 3000)
@Component
@Slf4j
public class CarportServiceExposer implements ICarPortService {

    private final ParkingLotManagerFactory factory;

    public CarportServiceExposer(ParkingLotManagerFactory factory) {
        this.factory = factory;
    }

    /**
     * 无牌车入场
     *
     * @param blankCarRpcReq 无牌车入场请求
     * @return
     */
    @Override
    public String blankCarIn(BlankCarRpcReq blankCarRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(blankCarRpcReq.getOperatorId());

        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(blankCarRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(blankCarRpcReq.getParkingLotId()).ability().carport();

        return carPortService.blankCarIn(blankCarRpcReq.getOpenId(), blankCarRpcReq.getScanType(), blankCarRpcReq.getChannelId()).getCarNo();
    }

    /**
     * 车场余位
     *
     * @param parkingLotRpcReq 停车场信息
     * @return
     */
    @Override
    public CarPortSpaceRpcResp getCarPortSpace(ParkingLotRpcReq parkingLotRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(parkingLotRpcReq.getOperatorId());

        CarPortService carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(parkingLotRpcReq.getParkingLotId()).carPort();

        CarPortMessage carPortMessage = carPortService.parkingLotMessage();

        CarPortSpaceRpcResp result = new CarPortSpaceRpcResp();
        result.setRest(carPortMessage.getRest());
        result.setUse(carPortMessage.getUse());
        result.setTotal(carPortMessage.getTotal());

        return result;
    }

    /**
     * 获取车辆信息
     *
     * @param carInfoRpcReq 获取车辆信息请求
     * @return
     */
    @Override
    public CarMessageRpcResp getCarInfo(CarInfoRpcReq carInfoRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(carInfoRpcReq.getOperatorId());

        CarPortService carPortService = factory.manager(ParkingLotManagerEnum.fromCode(carInfoRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(carInfoRpcReq.getParkingLotId()).carPort();
        return BeanUtil.copyProperties(carPortService.getCar(carInfoRpcReq.getCarNo()), CarMessageRpcResp.class);
    }
}
