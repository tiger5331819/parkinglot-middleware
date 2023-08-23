package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.guest;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 访客返回结果
 *
 * @author Suhuyuan
 */
@Data
public class GuestMessage {
    /**
     * 访客名称
     */
    private String guestName;
    /**
     * 预计入场时间【需在预计入场时间前入场】
     */
    private LocalDateTime visitTime;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 访问理由
     */
    private String description;
    /**
     * 车牌
     */
    private String carNo;
}
