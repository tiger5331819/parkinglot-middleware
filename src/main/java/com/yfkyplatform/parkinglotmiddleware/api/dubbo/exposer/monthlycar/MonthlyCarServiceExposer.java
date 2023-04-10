package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.monthlycar;

import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.IMonthlyCarService;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.request.CreateMonthlyCarRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.request.MonthlyCarRenewalRpcReq;
import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.response.*;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.CreateMonthlyCar;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.IMonthlyAblitity;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.MonthlyCarMessageResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly.MonthlyCarRenewal;
import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import com.yfkyplatform.parkinglotmiddleware.universal.TestBox;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 月卡车辆服务
 *
 * @author Suhuyuan
 */
@DubboService
@Component
public class MonthlyCarServiceExposer implements IMonthlyCarService {

    private final ParkingLotManagerFactory factory;
    private final TestBox testBox;

    public MonthlyCarServiceExposer(ParkingLotManagerFactory factory, TestBox testBox) {
        this.factory = factory;
        this.testBox = testBox;
    }


    /**
     * 获取停车场下的月卡费率
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @return
     */
    @Override
    public List<MonthlyCarRateResultRpcResp> monthlyCarLongRentalRate(Integer parkingLotManagerCode, String parkingLotId) {

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).monthly();
        List<MonthlyCarRateResultRpcResp> result = new ArrayList<>();
        monthlyAblitity.getMonthlyCarLongRentalRate().forEach(item -> {
            MonthlyCarRateResultRpcResp data = new MonthlyCarRateResultRpcResp();
            data.setPackageType(item.getPackageType());
            data.setPackageName(item.getPackageName());
            data.setPackageCharge(item.getPackageCharge());
            data.setPackageDuration(item.getPackageDuration());
            data.setRemark(item.getRemark());
            data.setPackageDurationMessage(item.getPackageDurationMessage());
            result.add(data);
        });
        return result;
    }

    /**
     * 获取月租车续费信息
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @return
     */
    @Override
    public MonthlyCarFeeResultRpcResp monthlyCarFee(Integer parkingLotManagerCode, String parkingLotId, String carNo) {

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).monthly();
        MonthlyCarMessageResult carInfo = monthlyAblitity.getMonthlyCarInfo(carNo);
        List<MonthlyCarRateMessage> monthlyCarRateList = monthlyAblitity.getMonthlyCarLongRentalRate()
                .stream().filter(item -> item.getPackageType() == carInfo.getCardTypeId()).map(item -> {
                    MonthlyCarRateMessage data = new MonthlyCarRateMessage();
                    data.setDuration(item.getPackageDuration());
                    data.setRemark(item.getRemark());
                    data.setPackageCharge(testBox.changeFee(item.getPackageCharge()));
                    data.setDurationMessage(item.getPackageDurationMessage());
                    return data;
                }).collect(Collectors.toList());

        MonthlyCarFeeResultRpcResp result = new MonthlyCarFeeResultRpcResp();
        result.setCarNo(carInfo.getCarNo());
        result.setCardTypeId(carInfo.getCardTypeId());
        result.setStartTime(carInfo.getStartTime());
        result.setEndTime(carInfo.getEndTime());
        result.setContactName(carInfo.getContactName());
        result.setContactPhone(carInfo.getContactPhone());
        result.setStatus(carInfo.getStatus());
        result.setLastUpdateTime(carInfo.getLastUpdateTime());
        result.setRateMessageList(monthlyCarRateList);

        return result;
    }

    /**
     * 获取月租车基本信息
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @return
     */
    @Override
    public MonthlyCarMessageResultRpcResp monthlyCarInfo(Integer parkingLotManagerCode, String parkingLotId, String carNo) {

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).monthly();
        MonthlyCarMessageResult monthlyCar = monthlyAblitity.getMonthlyCarInfo(carNo);

        MonthlyCarMessageResultRpcResp result = new MonthlyCarMessageResultRpcResp();
        result.setCarNo(monthlyCar.getCarNo());
        result.setCardTypeId(monthlyCar.getCardTypeId());
        result.setStartTime(monthlyCar.getStartTime());
        result.setEndTime(monthlyCar.getEndTime());
        result.setContactName(monthlyCar.getContactName());
        result.setContactPhone(monthlyCar.getContactPhone());
        result.setStatus(monthlyCar.getStatus());
        result.setLastUpdateTime(monthlyCar.getLastUpdateTime());

        return result;
    }

    /**
     * 获取月租车缴费历史
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @return
     */
    @Override
    public List<MonthlyCarHistoryMessageResultRpcResp> monthlyCarHistory(Integer parkingLotManagerCode, String parkingLotId, String carNo) {

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).monthly();
        List<MonthlyCarHistoryMessageResultRpcResp> result = new ArrayList<>();
        monthlyAblitity.getMonthlyCarHistory(carNo).forEach(item -> {
            MonthlyCarHistoryMessageResultRpcResp data = new MonthlyCarHistoryMessageResultRpcResp();
            data.setCarNo(item.getCarNo());
            data.setAmount(item.getAmount());
            data.setPayType(item.getPayType());
            data.setStartTime(item.getStartTime());
            data.setEndTime(item.getEndTime());
            data.setConcatName(item.getConcatName());
            data.setOperatorType(item.getOperatorType());

            result.add(data);
        });
        return result;
    }

    /**
     * 创建月租车
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param createMonthlyCar      月租车创建信息
     * @return
     */
    @Override
    public Boolean createMonthlyCar(Integer parkingLotManagerCode, String parkingLotId, CreateMonthlyCarRpcReq createMonthlyCar) {
        MonthlyCarAssert.startTimeLessThanEndTimeCheck(createMonthlyCar.getStartTime(), createMonthlyCar.getEndTime());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).monthly();
        CreateMonthlyCar create = new CreateMonthlyCar();
        create.setCarNo(createMonthlyCar.getCarNo());
        create.setStartTime(createMonthlyCar.getStartTime());
        create.setEndTime(createMonthlyCar.getEndTime());
        create.setCardTypeId(createMonthlyCar.getCardTypeId());
        create.setMoney(createMonthlyCar.getMoney());
        create.setPayType(createMonthlyCar.getPayType());
        create.setContactName(createMonthlyCar.getContactName());
        create.setContactPhone(createMonthlyCar.getContactPhone());

        return monthlyAblitity.createMonthlyCar(create);
    }

    /**
     * 月租车续期
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param monthlyCarRenewal     月租车续期信息
     * @return
     */
    @Override
    public Boolean renewalMonthlyCar(Integer parkingLotManagerCode, String parkingLotId, MonthlyCarRenewalRpcReq monthlyCarRenewal) {
        MonthlyCarAssert.startTimeLessThanEndTimeCheck(monthlyCarRenewal.getNewStartTime(), monthlyCarRenewal.getNewEndTime());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).monthly();
        MonthlyCarRenewal renewal = new MonthlyCarRenewal();
        renewal.setCarNo(monthlyCarRenewal.getCarNo());
        renewal.setNewStartTime(monthlyCarRenewal.getNewStartTime());
        renewal.setNewEndTime(monthlyCarRenewal.getNewEndTime());
        renewal.setMoney(monthlyCarRenewal.getMoney());
        renewal.setPayType(monthlyCarRenewal.getPayType());


        return monthlyAblitity.renewalMonthlyCar(renewal);
    }

    /**
     * 月租车销户
     *
     * @param parkingLotManagerCode 停车场管理名称
     * @param parkingLotId          停车场Id
     * @param carNo                 车牌号
     * @return
     */
    @Override
    public Boolean removeMonthlyCar(Integer parkingLotManagerCode, String parkingLotId, String carNo) {
        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotManagerCode).getName()).parkingLot(parkingLotId).monthly();

        return monthlyAblitity.removeMonthlyCar(carNo);
    }
}
