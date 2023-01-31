package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.monthly;


import java.util.List;

/**
 * 月卡接口
 *
 * @author Suhuyuan
 */
public interface IMonthlyAblitity {
    /**
     * 获取停车场下的月卡费率
     *
     * @return
     */
    List<MonthlyCarRateResult> getMonthlyCarLongRentalRate();

    /**
     * 获取月租车基本信息
     *
     * @param carNo
     * @return
     */
    MonthlyCarMessageResult getMonthlyCarInfo(String carNo);

    /**
     * 获取月租车缴费历史
     *
     * @param carNo
     * @return
     */
    List<MonthlyCarHistoryMessageResult> getMonthlyCarHistory(String carNo);

    /**
     * 月租车续期
     *
     * @param monthlyCarRenewal
     * @return
     */
    Boolean renewalMonthlyCar(MonthlyCarRenewal monthlyCarRenewal);

    /**
     * 月租车销户
     *
     * @param carNo
     * @return
     */
    Boolean removeMonthlyCar(String carNo);

    /**
     * 月租车开户
     *
     * @param createMonthlyCar
     * @return
     */
    Boolean createMonthlyCar(CreateMonthlyCar createMonthlyCar);
}
