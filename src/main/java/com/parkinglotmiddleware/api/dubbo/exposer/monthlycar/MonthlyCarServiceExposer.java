package com.parkinglotmiddleware.api.dubbo.exposer.monthlycar;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.IMonthlyCarService;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.request.*;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.response.*;
import com.parkinglotmiddleware.domain.manager.ParkingLotManagerFactory;
import com.parkinglotmiddleware.domain.manager.container.service.ability.monthly.CreateMonthlyCar;
import com.parkinglotmiddleware.domain.manager.container.service.ability.monthly.IMonthlyAblitity;
import com.parkinglotmiddleware.domain.manager.container.service.ability.monthly.MonthlyCarMessageResult;
import com.parkinglotmiddleware.domain.manager.container.service.ability.monthly.MonthlyCarRenewal;
import com.parkinglotmiddleware.universal.ParkingLotManagerEnum;
import com.parkinglotmiddleware.universal.testbox.TestBox;
import com.yfkyframework.common.core.exception.ExposerException;
import com.yfkyframework.util.context.AccountRpcContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
     * @param parkingLotRpcReq 停车场信息
     * @return
     */
    @Override
    public List<MonthlyCarRateResultRpcResp> monthlyCarLongRentalRate(ParkingLotRpcReq parkingLotRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(parkingLotRpcReq.getOperatorId());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(parkingLotRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(parkingLotRpcReq.getParkingLotId()).ability().monthly();
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
     * @param monthlyCarFeeRpcReq 获取月租车续费请求
     * @return
     */
    @Override
    public MonthlyCarFeeResultRpcResp monthlyCarFee(MonthlyCarFeeRpcReq monthlyCarFeeRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(monthlyCarFeeRpcReq.getOperatorId());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(monthlyCarFeeRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(monthlyCarFeeRpcReq.getParkingLotId()).ability().monthly();
        MonthlyCarMessageResult carInfo = monthlyAblitity.getMonthlyCarInfo(monthlyCarFeeRpcReq.getCarNo());
        List<MonthlyCarRateMessage> monthlyCarRateList = monthlyAblitity.getMonthlyCarLongRentalRate()
                .stream().filter(item -> item.getPackageType() == carInfo.getCardTypeId()).map(item -> {
                    MonthlyCarRateMessage data = new MonthlyCarRateMessage();
                    data.setDuration(item.getPackageDuration());
                    data.setRemark(item.getRemark());
                    data.setPackageCharge(item.getPackageCharge());
                    data.setDurationMessage(item.getPackageDurationMessage());

                    testBox.changeFee().ifCanChange((changeFee, discountFee) -> {
                        if (changeFee.compareTo(new BigDecimal(0)) > 0) {
                            data.setPackageCharge(changeFee);
                        }
                    });
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
     * @param monthlyCarRpcReq 获取月租车基本信息请求
     * @return
     */
    @Override
    public MonthlyCarMessageResultRpcResp monthlyCarInfo(MonthlyCarRpcReq monthlyCarRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(monthlyCarRpcReq.getOperatorId());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(monthlyCarRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(monthlyCarRpcReq.getParkingLotId()).ability().monthly();
        MonthlyCarMessageResult monthlyCar = monthlyAblitity.getMonthlyCarInfo(monthlyCarRpcReq.getCarNo());

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
     * @param monthlyCarHistoryMessageRpcReq 获取月租车缴费历史请求
     * @return
     */
    @Override
    public List<MonthlyCarHistoryMessageResultRpcResp> monthlyCarHistory(MonthlyCarHistoryMessageRpcReq monthlyCarHistoryMessageRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(monthlyCarHistoryMessageRpcReq.getOperatorId());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(monthlyCarHistoryMessageRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(monthlyCarHistoryMessageRpcReq.getParkingLotId()).ability().monthly();
        List<MonthlyCarHistoryMessageResultRpcResp> result = new ArrayList<>();
        monthlyAblitity.getMonthlyCarHistory(monthlyCarHistoryMessageRpcReq.getCarNo()).forEach(item -> {
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
     * @param createMonthlyCarRpcReq 月租车创建请求
     * @return
     */
    @Override
    public Boolean createMonthlyCar(CreateMonthlyCarRpcReq createMonthlyCarRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(createMonthlyCarRpcReq.getOperatorId());
        MonthlyCarAssert.startTimeLessThanEndTimeCheck(createMonthlyCarRpcReq.getStartTime(), createMonthlyCarRpcReq.getEndTime());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(createMonthlyCarRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(createMonthlyCarRpcReq.getParkingLotId()).ability().monthly();
        CreateMonthlyCar create = new CreateMonthlyCar();
        create.setCarNo(createMonthlyCarRpcReq.getCarNo());
        create.setStartTime(createMonthlyCarRpcReq.getStartTime());
        create.setEndTime(createMonthlyCarRpcReq.getEndTime());
        create.setCardTypeId(createMonthlyCarRpcReq.getCardTypeId());
        create.setMoney(createMonthlyCarRpcReq.getMoney());
        create.setPayType(createMonthlyCarRpcReq.getPayType());
        create.setContactName(createMonthlyCarRpcReq.getContactName());
        create.setContactPhone(createMonthlyCarRpcReq.getContactPhone());

        monthlyAblitity.createMonthlyCar(create);
        return true;
    }

    /**
     * 月租车续期
     *
     * @param monthlyCarRenewalRpcReq 月租车续期请求
     * @return
     */
    @Override
    public Boolean renewalMonthlyCar(MonthlyCarRenewalRpcReq monthlyCarRenewalRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(monthlyCarRenewalRpcReq.getOperatorId());
        MonthlyCarAssert.startTimeLessThanEndTimeCheck(monthlyCarRenewalRpcReq.getNewStartTime(), monthlyCarRenewalRpcReq.getNewEndTime());

        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(monthlyCarRenewalRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(monthlyCarRenewalRpcReq.getParkingLotId()).ability().monthly();
        MonthlyCarRenewal renewal = new MonthlyCarRenewal();
        renewal.setCarNo(monthlyCarRenewalRpcReq.getCarNo());
        renewal.setNewStartTime(monthlyCarRenewalRpcReq.getNewStartTime());
        renewal.setNewEndTime(monthlyCarRenewalRpcReq.getNewEndTime());
        renewal.setMoney(monthlyCarRenewalRpcReq.getMoney());
        renewal.setPayType(monthlyCarRenewalRpcReq.getPayType());

        monthlyAblitity.renewalMonthlyCar(renewal);
        return true;
    }

    /**
     * 月租车销户
     *
     * @param removeMonthlyCarRpcReq 月租车销户请求
     * @return
     */
    @Override
    public Boolean removeMonthlyCar(RemoveMonthlyCarRpcReq removeMonthlyCarRpcReq) throws ExposerException {
        AccountRpcContext.setOperatorId(removeMonthlyCarRpcReq.getOperatorId());
        IMonthlyAblitity monthlyAblitity = factory.manager(ParkingLotManagerEnum.fromCode(removeMonthlyCarRpcReq.getParkingLotManagerCode()).getName())
                .parkingLot(removeMonthlyCarRpcReq.getParkingLotId()).ability().monthly();

        monthlyAblitity.removeMonthlyCar(removeMonthlyCarRpcReq.getCarNo());
        return true;
    }
}
