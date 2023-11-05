package com.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark;

import com.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

/**
 * 通道信息
 *
 * @author Suhuyuan
 */
@Data
public class DueCarConfiguration extends DaoerBase {

    /**
     * 是否不可进 0不可进，1可进
     */
    private Integer urgepayNotIn;

    /**
     * 是否不可出 0不可出，1可出
     */
    private Integer urgepayNotOut;

    /**
     * 生效开始时间 HH:mm:ss
     */
    private String startTime;

    /**·
     * 生效结束时间 HH:mm:ss
     */
    private String closeTime;

}
