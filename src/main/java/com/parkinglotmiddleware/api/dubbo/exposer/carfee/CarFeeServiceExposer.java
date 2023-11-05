package com.parkinglotmiddleware.api.dubbo.exposer.carfee;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.parkinglotmiddleware.api.dubbo.service.carfee.ICarFeeService;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.CarFeeRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.ChannelCarRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.request.OrderPayMessageRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.carfee.response.CarOrderResultByListRpcResp;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.parkinglotmiddleware.domain.manager.container.ParkingLotPod;
import com.parkinglotmiddleware.domain.manager.container.service.ability.carfee.CarOrderPayMessage;
import com.parkinglotmiddleware.domain.manager.container.service.carport.CarPortService;
import com.parkinglotmiddleware.domain.manager.container.service.context.Car;
import com.parkinglotmiddleware.domain.manager.container.service.context.PayMessage;
import com.parkinglotmiddleware.universal.AssertTool;
import com.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.parkinglotmiddleware.universal.testbox.TestBox;
import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyframework.util.context.AccountRpcContext;
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
public class CarFeeServiceExposer implements ICarFeeService {

    private final ParkingLotManagerFactory factory;

    private final TestBox testBox;

    public CarFeeServiceExposer(ParkingLotManagerFactory factory, TestBox testBox) {
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
     * 车辆缴费
     * (支持欠费)
     *
     * @param orderPayMessageRpcReq 车辆订单缴费信息
     * @return
     */
    @Override
    public void payAccess(OrderPayMessageRpcReq orderPayMessageRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(orderPayMessageRpcReq.getOperatorId());

        CarPortService carPortService = factory.manager(ParkingLotManagerEnum.fromCode(orderPayMessageRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(orderPayMessageRpcReq.getParkingLotId()).carPort();

        CarOrderPayMessage message = new CarOrderPayMessage();

        message.setCarNo(orderPayMessageRpcReq.getCarNo());
        message.setPayFee(orderPayMessageRpcReq.getPayFee());
        message.setPayTime(orderPayMessageRpcReq.getPayTime());
        message.setPayType(orderPayMessageRpcReq.getPayType());
        message.setPaymentTransactionId(orderPayMessageRpcReq.getPaymentTransactionId());
        message.setDiscountFee(orderPayMessageRpcReq.getDiscountFee());
        message.setInId(orderPayMessageRpcReq.getInId());

        if (testBox.changeFee().enable() && StrUtil.contains(orderPayMessageRpcReq.getInId(), "Mock")) {
            return;
        }

        carPortService.payFee(message);
    }

    /**
     * 临时车出场（获取车辆费用）
     *
     * @param carFeeRpcReq 获取车辆费用请求
     * @return
     */
    @Override
    public CarOrderResultByListRpcResp getCarFee(CarFeeRpcReq carFeeRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(carFeeRpcReq.getOperatorId());

        ParkingLotPod parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(carFeeRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(carFeeRpcReq.getParkingLotId());

        return makeCarOrderResultByListRpcResp(parkingLot.carPort().calculatePayMessage(carFeeRpcReq.getCarNo()));
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param channelCarRpcReq 通道车辆信息请求
     * @return
     */
    @Override
    public CarOrderResultByListRpcResp getChannelCarFee(ChannelCarRpcReq channelCarRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(channelCarRpcReq.getOperatorId());

        ParkingLotPod parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(channelCarRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(channelCarRpcReq.getParkingLotId());
        return makeCarOrderResultByListRpcResp(parkingLot.carPort()
                .calculatePayMessage(channelCarRpcReq.getChannelId(), channelCarRpcReq.getScanType(), channelCarRpcReq.getOpenId()));
    }
}
