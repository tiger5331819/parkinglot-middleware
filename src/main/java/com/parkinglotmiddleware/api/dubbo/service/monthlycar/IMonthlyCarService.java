package com.parkinglotmiddleware.api.dubbo.service.monthlycar;

import com.parkinglotmiddleware.api.dubbo.service.ParkingLotRpcReq;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.request.*;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.response.MonthlyCarFeeResultRpcResp;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.response.MonthlyCarHistoryMessageResultRpcResp;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.response.MonthlyCarMessageResultRpcResp;
import com.parkinglotmiddleware.api.dubbo.service.monthlycar.response.MonthlyCarRateResultRpcResp;
import com.yfkyframework.common.core.exception.ExposerException;

import java.util.List;

/**
 * 停车场月卡服务
 *
 * @author Suhuyuan
 */
public interface IMonthlyCarService {
    /**
     * 获取停车场下的月卡费率
     *
     * @param parkingLotRpcReq 停车场信息
     * @return
     */
    List<MonthlyCarRateResultRpcResp> monthlyCarLongRentalRate(ParkingLotRpcReq parkingLotRpcReq) throws ExposerException;

     /**
      * 获取月租车续费信息
      *
      * @param monthlyCarFeeRpcReq 获取月租车续费请求
      * @return
      */
     MonthlyCarFeeResultRpcResp monthlyCarFee(MonthlyCarFeeRpcReq monthlyCarFeeRpcReq) throws ExposerException;

    /**
     * 获取月租车基本信息
     *
     * @param monthlyCarRpcReq 获取月租车基本信息请求
     * @return
     */
    MonthlyCarMessageResultRpcResp monthlyCarInfo(MonthlyCarRpcReq monthlyCarRpcReq) throws ExposerException;

    /**
     * 获取月租车缴费历史
     *
     * @param monthlyCarHistoryMessageRpcReq 获取月租车缴费历史请求
     * @return
     */
    List<MonthlyCarHistoryMessageResultRpcResp> monthlyCarHistory(MonthlyCarHistoryMessageRpcReq monthlyCarHistoryMessageRpcReq) throws ExposerException;

    /**
     * 创建月租车
     *
     * @param createMonthlyCarRpcReq 月租车创建请求
     * @return
     */
    Boolean createMonthlyCar(CreateMonthlyCarRpcReq createMonthlyCarRpcReq) throws ExposerException;

    /**
     * 月租车续期
     *
     * @param monthlyCarRenewalRpcReq 月租车续期请求
     * @return
     */
    Boolean renewalMonthlyCar(MonthlyCarRenewalRpcReq monthlyCarRenewalRpcReq) throws ExposerException;

    /**
     * 月租车销户
     *
     * @param removeMonthlyCarRpcReq 月租车销户请求
     * @return
     */
    Boolean removeMonthlyCar(RemoveMonthlyCarRpcReq removeMonthlyCarRpcReq) throws ExposerException;
}
