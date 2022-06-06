package com.yfkyplatform.parkinglot.domain.manager.container.ability.monthly;

import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.daoerbase.DaoerBaseResp;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarHistoryResult;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarLongRentalRateResult;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.monthlycar.MonthlyCarResult;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 月卡接口
 *
 * @author Suhuyuan
 */
public interface IMonthlyAblitity {
    /**
     * 获取停车场下的月卡费率
     * @return
     */
    Mono<DaoerBaseResp<List<MonthlyCarLongRentalRateResult>>> getMonthlyCarLongRentalRate();
    /**
     * 获取月租车基本信息
     *
     * @return
     */
    Mono<DaoerBaseResp<MonthlyCarResult>> getMonthlyCarInfo(String carNo);

    /**
     * 获取月租车缴费历史
     *
     * @return
     */
    Mono<DaoerBaseResp<List<MonthlyCarHistoryResult>>> getMonthlyCarHistory(String carNo);
    /**
     * 月租车续期
     *
     * @return
     */
    Mono<DaoerBaseResp<MonthlyCarResult>> renewalMonthlyCar(String carNo,String newStartTime,String newEndTime,String balanceMoney,int payType);
    /**
     * 月租车销户
     *
     * @return
     */
    Mono<DaoerBaseResp<MonthlyCarResult>> removeMonthlyCar(String carNo);
}
