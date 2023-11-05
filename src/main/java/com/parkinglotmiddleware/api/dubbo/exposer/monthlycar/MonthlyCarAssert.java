package com.parkinglotmiddleware.api.dubbo.exposer.monthlycar;

import java.time.LocalDateTime;

/**
 * 月卡车断言
 *
 * @author Suhuyuan
 */

public class MonthlyCarAssert {
    /**
     * 断言：检查开始时间是否低于结束时间
     *
     * @param startTime
     * @param endTime
     */
    public static void startTimeLessThanEndTimeCheck(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("开始时间不能低于结束时间");
        }
    }
}
