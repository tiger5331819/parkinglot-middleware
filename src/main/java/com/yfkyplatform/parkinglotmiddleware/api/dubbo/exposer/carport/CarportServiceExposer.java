package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.carport;

import cn.hutool.core.util.StrUtil;
import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.BlankCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageWithArrearRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultByListRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarPortSpaceRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.ChannelInfoResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLot;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.DaoerParkingLotConfiguration;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carfee.*;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.CarPortSpaceResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ChannelInfoResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ICarPortAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import com.yfkyplatform.parkinglotmiddleware.universal.AssertTool;
import com.yfkyplatform.parkinglotmiddleware.universal.testbox.TestBox;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    private CarOrderResultByListRpcResp makeCarOrderResultByListRpcResp(CarOrderWithArrearResultByList data) {

        CarOrderResultByListRpcResp result = makeCarOrderResultRpcResp(data);

        if (testBox.changeFee().enable()) {
            List<CarOrderWithArrearResult> mockList = new LinkedList<>();


            CarOrderWithArrearResult mockResult = new CarOrderWithArrearResult();
            mockResult.setOutTime(data.getOutTime().plusMinutes(-50));
            mockResult.setOverTime(0);
            mockResult.setPaymentType(1);
            mockResult.setParkingNo(data.getParkingNo());
            mockResult.setInId("Mock" + data.getInId() + 1);
            mockResult.setCarNo(data.getCarNo());
            mockResult.setStartTime(data.getStartTime().plusMinutes(-60));
            mockResult.setCreateTime(mockResult.getOutTime());
            mockResult.setServiceTime(new Long(Duration.between(mockResult.getStartTime(), mockResult.getOutTime()).toMinutes()).intValue());
            mockResult.setPayFee(new BigDecimal(100));
            mockResult.setDiscountFee(new BigDecimal(0));
            mockResult.setTotalFee(mockResult.getPayFee().add(mockResult.getDiscountFee()));

            mockList.add(mockResult);

            data.setArrearList(mockList);
        }

        if (AssertTool.checkCollectionNotNull(data.getArrearList())) {
            result.setArrearList(data.getArrearList().stream().map(this::makeCarOrderResultRpcResp).collect(Collectors.toList()));
        }

        return result;
    }

    private CarOrderResultByListRpcResp makeCarOrderResultByListRpcResp(CarOrderResult data) {

        CarOrderResultByListRpcResp result = makeCarOrderResultRpcResp(data);

        result.setArrearList(null);

        return result;
    }

    private CarOrderResultByListRpcResp makeCarOrderResultRpcResp(CarOrderResult data) {
        CarOrderResultByListRpcResp result = new CarOrderResultByListRpcResp();

        result.setCarNo(data.getCarNo());
        result.setStartTime(data.getStartTime());
        result.setCreateTime(data.getCreateTime());
        result.setServiceTime(data.getServiceTime());
        result.setTotalFee(data.getTotalFee());
        result.setPayFee(data.getPayFee());
        result.setDiscountFee(data.getDiscountFee());
        log.info(data.toString());
        if (data instanceof CarOrderWithArrearResult) {
            CarOrderWithArrearResult carOrderWithArrearResult = (CarOrderWithArrearResult) data;
            result.setOverTime(carOrderWithArrearResult.getOverTime());
            result.setPaymentType(carOrderWithArrearResult.getPaymentType());
            result.setParkingNo(carOrderWithArrearResult.getParkingNo());
            result.setInId(carOrderWithArrearResult.getInId());
            result.setOutTime(carOrderWithArrearResult.getOutTime());
        }

        testBox.changeFee().ifCanChange(result::setFee);


        return result;
    }

    /**
     * 车辆缴费
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @param payMessage            缴费信息
     * @return
     */
    @Override
    public Boolean payAccess(Integer parkingLotManagerCode, String parkingLotId, String carNo, OrderPayMessageRpcReq payMessage) {

        ICarFeeAblitity carFeeService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName())
                .parkingLot(parkingLotId).fee();

        CarOrderPayMessage message = new CarOrderPayMessage();
        message.setCarNo(carNo);
        message.setPayFee(payMessage.getPayFee());
        message.setPayTime(payMessage.getPayTime());
        message.setPayType(payMessage.getPayType());
        message.setPaymentTransactionId(payMessage.getPaymentTransactionId());
        message.setDiscountFee(payMessage.getDiscountFee());

        return carFeeService.payCarFeeAccess(message);
    }

    /**
     * 车辆缴费(支持欠费)
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @param payMessage            缴费信息
     * @return
     */
    @Override
    public Boolean payAccess(Integer parkingLotManagerCode, String parkingLotId, String carNo, OrderPayMessageWithArrearRpcReq payMessage) {

        ICarFeeAblitity carFeeService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName())
                .parkingLot(parkingLotId).fee();

        CarOrderPayMessageWithArrear message = new CarOrderPayMessageWithArrear();

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

        return carFeeService.payCarFeeAccessWithArrear(message);
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
        DaoerParkingLotConfiguration cfg = parkingLot.configuration();
        ICarFeeAblitity carFeeService = parkingLot.fee();

        return cfg.getBackTrack() ? makeCarOrderResultByListRpcResp(carFeeService.getCarFeeInfoWithArrear(carNo)) :
                makeCarOrderResultByListRpcResp(carFeeService.getCarFeeInfo(carNo));
    }

    /**
     * 无牌车入场
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param blankCar              无牌车请求信息
     * @return
     */
    @Override
    public String blankCarIn(Integer parkingLotManagerCode, String parkingLotId, BlankCarRpcReq blankCar) {
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).carport();

        return carPortService.blankCarIn(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
    }

    /**
     * 无牌车出场
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param blankCar              无牌车请求信息
     * @return
     */
    @Override
    public CarOrderResultByListRpcResp blankCarOut(Integer parkingLotManagerCode, String parkingLotId, BlankCarRpcReq blankCar) {
        DaoerParkingLot parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId);

        parkingLot.fee().blankCarOutWithArrear(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId());
        String carNo = parkingLot.carport().blankCarOut(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
        return makeCarOrderResultByListRpcResp(parkingLot.fee().getCarFeeInfoWithArrear(carNo));
    }

    /**
     * 车场余位
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public CarPortSpaceRpcResp getCarPortSpace(Integer parkingLotManagerCode, String parkingLotId) {
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).carport();

        CarPortSpaceResult carPortSpace = carPortService.getCarPortSpace();

        CarPortSpaceRpcResp result = new CarPortSpaceRpcResp();
        result.setRest(carPortSpace.getRest());


        result.setTotal(carPortSpace.getTotal());

        return result;
    }

    /**
     * 根据通道号获取车辆费用信息(新)
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param channelId             通道Id
     * @return
     */
    @Override
    public CarOrderResultByListRpcResp getChannelCarFee(Integer parkingLotManagerCode, String parkingLotId, String channelId, @Nullable BlankCarRpcReq blankCar) throws ExposerException {
        DaoerParkingLot parkingLot = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId);
        DaoerParkingLotConfiguration cfg = parkingLot.configuration();
        ICarFeeAblitity carFeeService = parkingLot.fee();

        if (cfg.getBackTrack()) {
            CarOrderWithArrearResultByList result = carFeeService.getCarFeeInfoByChannelWithArrear(channelId);

            if (StrUtil.isBlank(result.getCarNo())) {
                result = carFeeService.blankCarOutWithArrear(blankCar.getOpenId(), blankCar.getScanType(), channelId);
            }

            return makeCarOrderResultByListRpcResp(result);
        } else {
            return makeCarOrderResultByListRpcResp(carFeeService.getCarFeeInfo(channelId, null, blankCar.getOpenId()));
        }
    }

    /**
     * 获取通道列表
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public List<ChannelInfoResultRpcResp> getChannelsInfo(Integer parkingLotManagerCode, String parkingLotId) {
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).carport();

        List<ChannelInfoResult> channelInfoResultList = carPortService.getChannelsInfo();
        List<ChannelInfoResultRpcResp> resultList = new ArrayList<>();

        channelInfoResultList.forEach(item -> {
            ChannelInfoResultRpcResp result = new ChannelInfoResultRpcResp();
            result.setChannelId(item.getChannelId());
            result.setChannelName(item.getChannelName());
            result.setType(item.getType());
            result.setDoor(item.getDoor());
            result.setSense(item.getSense());
            result.setCamera(item.getCamera());
            result.setBoard(item.getBoard());

            resultList.add(result);
        });

        return resultList;
    }
}
