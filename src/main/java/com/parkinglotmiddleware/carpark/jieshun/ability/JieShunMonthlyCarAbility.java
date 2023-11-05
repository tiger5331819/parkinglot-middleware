package com.parkinglotmiddleware.carpark.jieshun.ability;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.api.IJieShunMonthlyCar;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.daoerbase.DaoerBaseRespHead;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.parkinglotmiddleware.carpark.jieshun.client.domin.resp.monthlycar.MonthlyCarResult;
import com.parkinglotmiddleware.domain.manager.container.service.ability.monthly.*;
import com.yfkyframework.common.core.constant.responsecode.MemberResponseCode;
import com.yfkyframework.common.core.exception.ExposerException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 道尔工具能力
 *
 * @author Suhuyuan
 */
@Slf4j
public class JieShunMonthlyCarAbility implements IMonthlyAblitity {

    private final IJieShunMonthlyCar api;

    public JieShunMonthlyCarAbility(IJieShunMonthlyCar daoerClient) {
        api = daoerClient;
    }

    /**
     * 获取停车场下的月卡费率
     * 道尔月卡费率将时效描述为中文
     * 道尔月卡费率金额由元转为 SaaS 使用的分
     *
     * @return
     */
    @Override
    public List<MonthlyCarRateResult> getMonthlyCarLongRentalRate() {
        List<MonthlyCarLongRentalRateResult> data = api.getMonthlyCarLongRentalRate().block().getBody();
        List<MonthlyCarRateResult> results = new ArrayList<>();
        data.forEach(item -> {
            MonthlyCarRateResult result = new MonthlyCarRateResult();
            result.setPackageType(item.getPackageType());
            result.setPackageName(item.getPackageName());
            result.setPackageCharge(item.getPackageCharge().movePointRight(2));
            result.setPackageDuration(item.getPackageDuration());

            switch (item.getPackageDuration()) {
                case "1":
                    result.setPackageDurationMessage("月租");
                    break;
                case "2":
                    result.setPackageDurationMessage("季租");
                    break;
                case "3":
                    result.setPackageDurationMessage("半年租");
                    break;
                case "4":
                    result.setPackageDurationMessage("年租");
                    break;
                default:
                    result.setPackageDurationMessage(null);
                    break;
            }

            result.setRemark(item.getRemark());

            results.add(result);
        });
        return results;
    }

    /**
     * 获取月租车基本信息
     *
     * @param carNo
     * @return
     */
    @Override
    public MonthlyCarMessageResult getMonthlyCarInfo(String carNo) {
        MonthlyCarResult monthlyCarResult = api.getMonthlyCarInfo(carNo).block().getBody();

        MonthlyCarMessageResult result = new MonthlyCarMessageResult();
        if (ObjectUtil.isNull(monthlyCarResult)) {
            return result;
        }

        result.setCarNo(monthlyCarResult.getCarNo());
        result.setCardTypeId(monthlyCarResult.getCardTypeId());
        result.setStartTime(monthlyCarResult.getStartTime());
        result.setEndTime(monthlyCarResult.getEndTime());
        result.setContactName(monthlyCarResult.getContactName());
        result.setContactPhone(monthlyCarResult.getContactPhone());
        result.setStatus(monthlyCarResult.getStatus());
        result.setLastUpdateTime(monthlyCarResult.getLastUpdateTime());

        return result;
    }

    /**
     * 获取月租车缴费历史
     *
     * @param carNo
     * @return
     */
    @Override
    public List<MonthlyCarHistoryMessageResult> getMonthlyCarHistory(String carNo) {
        List<MonthlyCarHistoryResult> historyResults = api.getMonthlyCarHistory(carNo).block().getBody();

        List<MonthlyCarHistoryMessageResult> results = new ArrayList<>();
        historyResults.forEach(item -> {
            MonthlyCarHistoryMessageResult result = new MonthlyCarHistoryMessageResult();
            result.setCarNo(item.getCarNo());
            result.setAmount(item.getAmount());
            result.setPayType(item.getPayType());
            result.setStartTime(item.getStartTime());
            result.setEndTime(item.getEndTime());
            result.setConcatName(item.getConcatName());
            result.setOperatorType(item.getOperatorType());

            results.add(result);
        });
        return results;
    }

    /**
     * 月租车续期
     *
     * @param monthlyCarRenewal
     * @return
     */
    @Override
    public void renewalMonthlyCar(MonthlyCarRenewal monthlyCarRenewal) {
        DaoerBaseRespHead result = api.renewalMonthlyCar(monthlyCarRenewal.getCarNo(),
                        LocalDateTimeUtil.format(monthlyCarRenewal.getNewStartTime(), DatePattern.NORM_DATETIME_PATTERN),
                        LocalDateTimeUtil.format(monthlyCarRenewal.getNewEndTime(), DatePattern.NORM_DATETIME_PATTERN),
                        monthlyCarRenewal.getMoney().movePointLeft(2).toString(), monthlyCarRenewal.getPayType())
                .block().getHead();

        if (result.getStatus() != 1) {
            throw new ExposerException(MemberResponseCode.DEFAULT,result.getMessage());
        }
    }

    /**
     * 月租车销户
     *
     * @param carNo
     * @return
     */
    @Override
    public void removeMonthlyCar(String carNo) {
        DaoerBaseRespHead result = api.removeMonthlyCar(carNo).block().getHead();
        if (result.getStatus() != 1) {
            throw new ExposerException(MemberResponseCode.DEFAULT,result.getMessage());
        }
    }

    /**
     * 月租车开户
     *
     * @param createMonthlyCar
     * @return
     */
    @Override
    public void createMonthlyCar(CreateMonthlyCar createMonthlyCar) {
        DaoerBaseRespHead result = api.createMonthlyCar(createMonthlyCar.getCarNo(), createMonthlyCar.getCardTypeId(),
                        LocalDateTimeUtil.format(createMonthlyCar.getStartTime(), DatePattern.NORM_DATETIME_PATTERN),
                        LocalDateTimeUtil.format(createMonthlyCar.getEndTime(), DatePattern.NORM_DATETIME_PATTERN),
                        createMonthlyCar.getMoney().movePointLeft(2).toString(), createMonthlyCar.getPayType(), createMonthlyCar.getContactName(), createMonthlyCar.getContactPhone())
                .block().getHead();

        if (result.getStatus() != 1) {
            throw new ExposerException(MemberResponseCode.DEFAULT,result.getMessage());
        }
    }

}
