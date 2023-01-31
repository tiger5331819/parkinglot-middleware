package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.api;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarResult;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 月卡接口
 *
 * @author Suhuyuan
 */
public interface IDaoerMonthlyCar {
    /**
     * 获取停车场下的月卡费率
     * @return
     */
    Mono<DaoerBaseResp<List<MonthlyCarLongRentalRateResult>>> getMonthlyCarLongRentalRate();

    /**
     * 获取月租车基本信息
     *
     * @param carNo
     * @return
     */
    Mono<DaoerBaseResp<MonthlyCarResult>> getMonthlyCarInfo(String carNo);

    /**
     * 月租车开户
     *
     * @param carNo
     * @return
     */
    Mono<DaoerBaseResp<MonthlyCarResult>> createMonthlyCar(String carNo, Integer cardType,
                                                           String startTime, String endTime, String balanceMoney, int payType,
                                                           String concatName, String concatPhone);

    /**
     * 获取月租车缴费历史
     *
     * @param carNo
     * @return
     */
    Mono<DaoerBaseResp<List<MonthlyCarHistoryResult>>> getMonthlyCarHistory(String carNo);

    /**
     * 月租车续期
     *
     * @param carNo
     * @param newStartTime
     * @param newEndTime
     * @param balanceMoney
     * @param payType
     * @return
     */
    Mono<DaoerBaseResp<MonthlyCarResult>> renewalMonthlyCar(String carNo,String newStartTime,String newEndTime,String balanceMoney,int payType);

    /**
     * 月租车销户
     *
     * @param carNo
     * @return
     */
    Mono<DaoerBaseResp<MonthlyCarResult>> removeMonthlyCar(String carNo);
}
