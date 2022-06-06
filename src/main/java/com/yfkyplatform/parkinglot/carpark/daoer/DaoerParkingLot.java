package com.yfkyplatform.parkinglot.carpark.daoer;

import com.yfkyplatform.parkinglot.carpark.daoer.client.DaoerClient;
import com.yfkyplatform.parkinglot.configuartion.redis.RedisTool;
import com.yfkyplatform.parkinglot.domain.manager.container.ParkingLotPod;
import com.yfkyplatform.parkinglot.domain.manager.container.ability.carport.*;

import java.util.List;

/**
 * 道尔停车场容器
 *
 * @author Suhuyuan
 */

public class DaoerParkingLot extends ParkingLotPod  implements ICarPortAblitity {
    private DaoerClient client;

    private DaoerParkingLotConfiguration cfg;

    public DaoerParkingLot(DaoerParkingLotConfiguration daoerParkingLotInfo, RedisTool redis){
        super(daoerParkingLotInfo);
        client=new DaoerClient(daoerParkingLotInfo.getAppName(),daoerParkingLotInfo.getParkId(),daoerParkingLotInfo.getBaseUrl(),redis);
    }

    @Override
    public <T> T client() {
        return (T) client;
    }

    @Override
    public boolean healthCheck() {
        return false;
    }

    @Override
    public byte[] getImage(String imgPath) {
        return new byte[0];
    }

    @Override
    public CarPortSpaceResult getCarPortSpace() {
        return null;
    }

    @Override
    public CarOrderResult getCarFeeInfo(String carNo) {
        return null;
    }

    @Override
    public Boolean payCarFeeAccess(CarOrderPayMessage payMessage) {
        return null;
    }

    @Override
    public CarOrderResult getChannelCarFee(String channelId, String carNo, String openId) {
        return null;
    }

    @Override
    public BlankCarInResult blankCarIn(String openId, int scanType, String channelId) {
        return null;
    }

    @Override
    public BlankCarOutResult blankCarOut(String openId, int scanType, String channelId) {
        return null;
    }

    @Override
    public List<ChannelResult> getChannelsInfo() {
        return null;
    }

    @Override
    public List<ChannelStateResult> getChannelStates() {
        return null;
    }

    @Override
    public List<ChannelStatusResult> controlChannel(String channelId, int channelIdStatus) {
        return null;
    }
}
