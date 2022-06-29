package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.carport;

import com.yfkyplatform.ordercenter.api.resp.OrderParkingRecordRpcResp;
import com.yfkyplatform.ordercenter.api.resp.OrderPayDetailRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.BlankCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarPortSpaceRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.ChannelInfoResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.ThirdIdProxy;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.*;
import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 车场服务
 *
 * @author Suhuyuan
 */
@DubboService
@Component
public class CarportServiceExposer implements ICarPortService {

    private final ParkingLotManagerFactory factory;

    private final ThirdIdProxy thirdIdProxy;

    public CarportServiceExposer(ParkingLotManagerFactory factory, ThirdIdProxy thirdIdProxy) {
        this.factory = factory;
        this.thirdIdProxy = thirdIdProxy;
    }

    private CarOrderResultRpcResp makeCarOrderResultRpcResp(CarOrderResult data) {

        CarOrderResultRpcResp result = new CarOrderResultRpcResp();
        result.setCarNo(data.getCarNo());
        result.setStartTime(data.getStartTime());
        result.setCreateTime(data.getCreateTime());
        result.setServiceTime(data.getServiceTime());
        result.setTotalFee(data.getTotalFee());
        result.setPayFee(data.getPayFee());
        result.setDiscountFee(data.getDiscountFee());

        return result;
    }

    /**
     * 车辆缴费
     *
     * @param operatorId            租户ID
     * @param parkingLotManagerCode 停车场管理名称
     * @param orderParkingRecord    订单信息
     * @param payMessage            缴费信息
     * @return
     */
    @Override
    public Boolean payAccess(Integer operatorId, Integer parkingLotManagerCode, OrderParkingRecordRpcResp orderParkingRecord, OrderPayDetailRpcResp payMessage) {
        String thirdId = thirdIdProxy.getThirdId(orderParkingRecord.getParkinglotId(), operatorId);

        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).getName())
                .parkingLot(thirdId).carport();

        CarOrderPayMessage message = new CarOrderPayMessage();
        BigDecimal payFee = new BigDecimal(payMessage.getPaidAmount());
        message.setCarNo(orderParkingRecord.getPlateNumber());
        message.setPayFee(payFee.divide(BigDecimal.valueOf(100)));
        message.setPayTime(payMessage.getPaidTime().toString());
        message.setPayType(payMessage.getPaidModeId());
        message.setPaymentTransactionId(payMessage.getPayOrderId().toString());

        return carPortService.payCarFeeAccess(message);
    }

    /**
     * 临时车出场（获取车辆费用）
     *
     * @param operatorId            租户ID
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @return
     */
    @Override
    public CarOrderResultRpcResp getCarFee(Integer operatorId, Integer parkingLotManagerCode, Long parkingLotId, String carNo) {
        String thirdId = thirdIdProxy.getThirdId(parkingLotId, operatorId);
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).getName()).parkingLot(thirdId).carport();

        return makeCarOrderResultRpcResp(carPortService.getCarFeeInfo(carNo));
    }

    /**
     * 无牌车入场
     *
     * @param operatorId            租户ID
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param blankCar              无牌车请求信息
     * @return
     */
    @Override
    public String blankCarIn(Integer operatorId, Integer parkingLotManagerCode, Long parkingLotId, BlankCarRpcReq blankCar) {
        String thirdId = thirdIdProxy.getThirdId(parkingLotId, operatorId);
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).getName()).parkingLot(thirdId).carport();

        return carPortService.blankCarIn(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
    }

    /**
     * 无牌车出场
     *
     * @param operatorId            租户ID
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param blankCar              无牌车请求信息
     * @return
     */
    @Override
    public CarOrderResultRpcResp blankCarOut(Integer operatorId, Integer parkingLotManagerCode, Long parkingLotId, BlankCarRpcReq blankCar) {
        String thirdId = thirdIdProxy.getThirdId(parkingLotId, operatorId);
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).getName()).parkingLot(thirdId).carport();

        String carNo = carPortService.blankCarOut(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
        return makeCarOrderResultRpcResp(carPortService.getCarFeeInfo(carNo));
    }

    /**
     * 车场余位
     *
     * @param operatorId            租户ID
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public CarPortSpaceRpcResp getCarPortSpace(Integer operatorId, Integer parkingLotManagerCode, Long parkingLotId) {
        String thirdId = thirdIdProxy.getThirdId(parkingLotId, operatorId);
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).getName()).parkingLot(thirdId).carport();

        CarPortSpaceResult carPortSpace = carPortService.getCarPortSpace();

        CarPortSpaceRpcResp result = new CarPortSpaceRpcResp();
        result.setRest(carPortSpace.getRest());
        result.setTotal(carPortSpace.getTotal());

        return result;
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param operatorId            租户ID
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param channelId             通道Id
     * @param carNo                 车牌号
     * @param openId                微信/支付宝openId
     * @return
     */
    @Override
    public CarOrderResultRpcResp getChannelCarFee(Integer operatorId, Integer parkingLotManagerCode, Long parkingLotId, String channelId, @Nullable String carNo, @Nullable String openId) {
        String thirdId = thirdIdProxy.getThirdId(parkingLotId, operatorId);
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).getName()).parkingLot(thirdId).carport();

        return makeCarOrderResultRpcResp(carPortService.getChannelCarFee(channelId, carNo, openId));
    }

    /**
     * 获取通道列表
     *
     * @param operatorId            租户ID
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public List<ChannelInfoResultRpcResp> getChannelsInfo(Integer operatorId, Integer parkingLotManagerCode, Long parkingLotId) {
        String thirdId = thirdIdProxy.getThirdId(parkingLotId, operatorId);
        ICarPortAblitity carPortService = factory.manager(ParkingLotManagerEnum.ValueOf(parkingLotManagerCode).getName()).parkingLot(thirdId).carport();

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
