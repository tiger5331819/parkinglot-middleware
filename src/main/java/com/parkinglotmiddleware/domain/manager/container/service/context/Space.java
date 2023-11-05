package com.parkinglotmiddleware.domain.manager.container.service.context;

import lombok.Data;

/**
 * 车所处的空间
 *
 * @author Suhuyuan
 */
@Data
public class Space {

    /**
     * 通道Id
     */
    private String channelId;

    /**
     * 位置 1：入口 2：出口
     */
    private Integer location;
}
