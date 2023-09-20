package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.carport;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyframework.util.context.AccountRpcContext;
import com.yfkyplatform.parkinglotmiddleware.api.ParkingLotRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.BlankCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.CarInfoRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.ChannelCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarMessageRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultByListRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarPortSpaceRpcResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLot;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderPayMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport.CarPortMessage;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.carport.CarPortService;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.context.PayMessage;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

    private final TestBox testBox;

    public CarportServiceExposer(ParkingLotManagerFactory factory, TestBox testBox) {
        this.factory = factory;
        this.testBox = testBox;
    }

    private CarOrderResultByListRpcResp makeCarOrderResultByListRpcResp(Car car) {

        CarOrderResultByListRpcResp result = makeCarOrderResultRpcResp(car.getCarNo(), car.getOrder());

        if (AssertTool.checkCollectionNotNull(car.getArrearOrder())) {
            result.setArrearList(car.getArrearOrder().stream().map(item -> makeCarOrderResultRpcResp(car.getCarNo(), item)).collect(Collectors.toList()));
        }

        return result;
    }

    private CarOrderResultByListRpcResp makeCarOrderResultRpcResp(String carNo, PayMessage payMessage) {

        if (ObjectUtil.isNull(payMessage)) {
            payMessage = new PayMessage();
        }
        CarOrderResultByListRpcResp result = new CarOrderResultByListRpcResp();

        result.setCarNo(carNo);
        result.setStartTime(payMessage.getInTime());
        result.setCreateTime(payMessage.getCreateTime());
        result.setServiceTime(payMessage.serviceTime());
        result.setTotalFee(payMessage.getTotalFee());
        result.setPayFee(payMessage.getPayFee());
        result.setDiscountFee(payMessage.getDiscountFee());
        result.setOverTime(payMessage.getOverTime());
        result.setInId(payMessage.getInId());

        testBox.changeFee().ifCanChange(result::setFee);

        return result;
    }

    /**
     * 临时车出场（获取车辆费用）
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @return
     */
    @Override
    public CarOrderResultByListRpcResp getCarFee(Integer parkingLotManagerCode, String parkingLotId, String carNo) {

        DaoerParkingLot parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId);

        return makeCarOrderResultByListRpcResp(parkingLot.carPort().calculatePayMessage(carNo));
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param channelCarRpcReq      通道车辆信息
     * @return
     */
    @Override
    public CarOrderResultByListRpcResp getChannelCarFee(Integer parkingLotManagerCode, String parkingLotId, ChannelCarRpcReq channelCarRpcReq) throws ExposerException {
        DaoerParkingLot parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId);
        return makeCarOrderResultByListRpcResp(parkingLot.carPort()
                .calculatePayMessage(channelCarRpcReq.getChannelId(), channelCarRpcReq.getScanType(), channelCarRpcReq.getOpenId()));

    }

    /**
     * 车辆缴费
     * (支持欠费)
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @param payMessage            缴费信息
     * @return
     */
    @Override
    public Boolean payAccess(Integer parkingLotManagerCode, String parkingLotId, String carNo, OrderPayMessageRpcReq payMessage) throws ExposerException {
        CarPortService carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName())
                .parkingLot(parkingLotId).carPort();

        CarOrderPayMessage message = new CarOrderPayMessage();

        message.setCarNo(carNo);
        message.setPayFee(payMessage.getPayFee());
        message.setPayTime(payMessage.getPayTime());
        message.setPayType(payMessage.getPayType());
        message.setPaymentTransactionId(payMessage.getPaymentTransactionId());
        message.setDiscountFee(payMessage.getDiscountFee());
        message.setInId(payMessage.getInId());

        if (testBox.changeFee().enable() && StrUtil.contains(payMessage.getInId(), "Mock")) {
            return true;
        }

        return carPortService.payFee(message);
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
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @return
     */
    @Override
    public CarMessageRpcResp getCarInfo(Integer parkingLotManagerCode, String parkingLotId, String carNo) throws ExposerException {
        return null;
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
