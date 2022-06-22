package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.carport;

import com.yfkyplatform.parkinglotmiddleware.api.carport.ICarPortService;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.BlankCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.request.OrderPayMessageRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarOrderResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.CarPortSpaceRpcResp;
import com.yfkyplatform.parkinglotmiddleware.api.carport.response.ChannelInfoResultRpcResp;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.*;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

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

    public CarportServiceExposer(ParkingLotManagerFactory factory) {
        this.factory = factory;
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
     * @param parkingLotManager 停车场管理名称
     * @param parkingLotId      停车场Id
     * @param carNo             车牌号
     * @param payMessage        缴费信息
     * @return
     */
    @Override
    public Boolean payAccess(String parkingLotManager, String parkingLotId, String carNo, OrderPayMessageRpcReq payMessage) {
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        CarOrderPayMessage message=new CarOrderPayMessage();
        message.setCarNo(carNo);
        message.setPayFee(payMessage.getPayFee());
        message.setChannelId(payMessage.getChannelId());
        message.setPayTime(payMessage.getPayTime());
        message.setPayType(payMessage.getPayType());
        message.setDiscountFee(payMessage.getDiscountFee());
        message.setPaymentTransactionId(payMessage.getPaymentTransactionId());
        message.setServiceTime(payMessage.getServiceTime());
        message.setTotalFee(payMessage.getTotalFee());
        message.setPaymentType(payMessage.getPaymentType());

        return carPortService.payCarFeeAccess(message);
    }

    /**
     * 临时车出场（获取车辆费用）
     *
     * @param parkingLotManager 停车场管理名称
     * @param parkingLotId      停车场Id
     * @param carNo             车牌号
     * @return
     */
    @Override
    public CarOrderResultRpcResp getCarFee(String parkingLotManager, String parkingLotId, String carNo) {
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        return makeCarOrderResultRpcResp(carPortService.getCarFeeInfo(carNo));
    }

    /**
     * 无牌车入场
     *
     * @param parkingLotManager 停车场管理名称
     * @param parkingLotId      停车场Id
     * @param blankCar          无牌车请求信息
     * @return
     */
    @Override
    public String blankCarIn(String parkingLotManager, String parkingLotId, BlankCarRpcReq blankCar) {
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        return carPortService.blankCarIn(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
    }

    /**
     * 无牌车出场
     *
     * @param parkingLotManager 停车场管理名称
     * @param parkingLotId      停车场Id
     * @param blankCar          无牌车请求信息
     * @return
     */
    @Override
    public CarOrderResultRpcResp blankCarOut(String parkingLotManager, String parkingLotId, BlankCarRpcReq blankCar) {
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        String carNo=carPortService.blankCarOut(blankCar.getOpenId(), blankCar.getScanType(), blankCar.getChannelId()).getCarNo();
        return makeCarOrderResultRpcResp(carPortService.getCarFeeInfo(carNo));
    }

    /**
     * 车场余位
     *
     * @param parkingLotManager 停车场管理名称
     * @param parkingLotId      停车场Id
     * @return
     */
    @Override
    public CarPortSpaceRpcResp getCarPortSpace(String parkingLotManager, String parkingLotId) {
        ICarPortAblitity carPortService=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        CarPortSpaceResult carPortSpace= carPortService.getCarPortSpace();

        CarPortSpaceRpcResp result=new CarPortSpaceRpcResp();
        result.setRest(carPortSpace.getRest());
        result.setTotal(carPortSpace.getTotal());

        return result;
    }

    /**
     * 根据通道号获取车辆费用信息
     *
     * @param channelId
     * @param carNo     车牌号码
     * @param openId
     * @return
     */
    @Override
    public CarOrderResultRpcResp getChannelCarFee(String parkingLotManager, String parkingLotId, String channelId, @Nullable String carNo, @Nullable String openId) {
        ICarPortAblitity carPortService = factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        return makeCarOrderResultRpcResp(carPortService.getChannelCarFee(channelId, carNo, openId));
    }

    /**
     * 获取通道列表
     *
     * @return
     */
    @Override
    public List<ChannelInfoResultRpcResp> getChannelsInfo(String parkingLotManager, String parkingLotId) {
        ICarPortAblitity carPortAblitity=factory.manager(parkingLotManager).parkingLot(parkingLotId).carport();
        List<ChannelInfoResult> channelInfoResultList=carPortAblitity.getChannelsInfo();
        List<ChannelInfoResultRpcResp> resultList=new ArrayList<>();

        channelInfoResultList.forEach(item->{
            ChannelInfoResultRpcResp result=new ChannelInfoResultRpcResp();
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
