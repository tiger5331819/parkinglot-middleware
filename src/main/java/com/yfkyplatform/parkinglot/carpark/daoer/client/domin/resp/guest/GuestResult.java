package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.guest;

import lombok.Data;

/**
 * 访客返回结果
 *
 * @author Suhuyuan
 */
@Data
public class GuestResult {
    /**
     * 车牌
     */
    private String carNo;
    /**
     * 唯一记录标识
     */
    private String objectId;
}
