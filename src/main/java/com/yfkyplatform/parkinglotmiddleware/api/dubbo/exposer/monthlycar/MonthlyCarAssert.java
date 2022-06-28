package com.yfkyplatform.parkinglotmiddleware.api.dubbo.exposer.monthlycar;

import com.yfkyplatform.parkinglotmiddleware.api.monthlycar.request.MonthlyCarRenewalRpcReq;

/**
 * 月卡车断言
 *
 * @author Suhuyuan
 */

public class MonthlyCarAssert {
    public static void newStartTimeLessThanEndTime(MonthlyCarRenewalRpcReq monthlyCarRenewal) {
        if (monthlyCarRenewal.getNewStartTime().isAfter(monthlyCarRenewal.getNewEndTime())) {
            throw new IllegalArgumentException("月租车新的开始时间不能低于新的结束时间");
        }
    }
}
