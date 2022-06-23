package com.yfkyplatform.parkinglotmiddleware.domain.manager;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 停车场基本信息
 *
 * @author Suhuyuan
 */
@Getter
@AllArgsConstructor
public class ParkingLotConfiguration {
    /**
     * 配置信息唯一标识
     */
    private Long id;
    /**
     * 描述
     */
    private String description;
    /**
     * 所属管理器类型
     */
    private String managerType;
}
