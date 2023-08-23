package com.yfkyplatform.parkinglotmiddleware.domain.manager.container.service.ability.guest;

import lombok.Data;

/**
 * 访客返回结果
 *
 * @author Suhuyuan
 */
@Data
public class GuestUserResult {
    /**
     * 访客Id
     */
    private String guestUserId;
    /**
     * 车牌
     */
    private String carNo;
}
