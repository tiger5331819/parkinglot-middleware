package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.carport;

import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.BlankCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarPortSpaceRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.ChannelInfoResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.*;
import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import com.yfkyplatform.parkinglotmiddleware.universal.TestBox;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车场服务
 *
 * @author Suhuyuan
 */
@DubboService(timeout = 3000)
@Component
public class CarportServiceExposer implements ICarPortService {

    private final ParkingLotManagerFactory factory;

    private final TestBox testBox;

    public CarportServiceExposer(ParkingLotManagerFactory factory, TestBox testBox) {
        this.factory = factory;
        this.testBox = testBox;
    }

    private CarOrderResultRpcResp makeCarOrderResultRpcResp(CarOrderResult data) {

        CarOrderResultRpcResp result = new CarOrderResultRpcResp();
        result.setCarNo(data.getCarNo());
        result.setStartTime(data.getStartTime());
        result.setCreateTime(data.getCreateTime());
        result.setServiceTime(data.getServiceTime());
        result.setTotalFee(data.getTotalFee());
        result.setPayFee(testBox.changeFee(data.getPayFee()));
        result.setDiscountFee(data.getDiscountFee());

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

        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName())
                .parkingLot(parkingLotId).carport();

        CarOrderPayMessage message = new CarOrderPayMessage();
        message.setCarNo(carNo);
        message.setPayFee(payMessage.getPayFee());
        message.setPayTime(payMessage.getPayTime());
        message.setPayType(payMessage.getPayType());
        message.setPaymentTransactionId(payMessage.getPaymentTransactionId());
        message.setDiscountFee(payMessage.getDiscountFee());

        return carPortService.payCarFeeAccess(message);
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
    public CarOrderResultRpcResp getCarFee(Integer parkingLotManagerCode, String parkingLotId, String carNo) {
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).carport();

        return makeCarOrderResultRpcResp(carPortService.getCarFeeInfo(carNo));
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
    public CarOrderResultRpcResp blankCarOut(Integer parkingLotManagerCode, String parkingLotId, BlankCarRpcReq blankCar) {
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).carport();

        String carNo = carPortService.blankCarOut(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
        return makeCarOrderResultRpcResp(carPortService.getCarFeeInfo(carNo));
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
     * 根据通道号获取车辆费用信息
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param channelId             通道Id
     * @param carNo                 车牌号
     * @param openId                微信/支付宝openId
     * @return
     */
    @Override
    public CarOrderResultRpcResp getChannelCarFee(Integer parkingLotManagerCode, String parkingLotId, String channelId, @Nullable String carNo, @Nullable String openId) {
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).carport();

        return makeCarOrderResultRpcResp(carPortService.getCarFeeInfo(channelId, carNo, openId));
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
