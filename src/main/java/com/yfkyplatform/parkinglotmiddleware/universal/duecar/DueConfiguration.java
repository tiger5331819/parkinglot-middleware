package com.yfkyplatform.parkinglotmiddleware.universal.duecar;

import lombok.Data;

import java.time.LocalTime;

/**
 * 联动催缴配置
 *
 * @author Suhuyuan
 */
@Data
public class DueConfiguration {

    /**
     * 是否不可进
     */
    private Integer urgepayNotIn;

    /**
     * 是否不可出
     */
    private Integer urgepayNotOut;

    /**
     * 生效开始时间
     */
    private LocalTime startTime;

    /**
     * 生效结束时间
     */
    private LocalTime closeTime;
}
