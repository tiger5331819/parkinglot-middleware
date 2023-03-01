package com.yfkyplatform.parkinglotmiddleware.domain.service;

import cn.hutool.core.util.ObjectUtil;

/**
 * 停车场管理映射
 *
 * @author tiger
 */

public enum ParkingLotManagerEnum {
    /**
     * 道尔 code:4
     */
    Daoer(4, "Daoer"),
    /**
     * 立方 code:3
     */
    Lifang(3, "Lifang");

    private final int code;

    private final String message;

    ParkingLotManagerEnum(int value, String message) {
        this.code = value;
        this.message = message;
    }

    public static ParkingLotManagerEnum fromCode(Integer parkingLotManagerCode) {
        if (ObjectUtil.isNull(parkingLotManagerCode)) {
            return null;
        }

        for (ParkingLotManagerEnum enums : ParkingLotManagerEnum.values()) {
            if (enums.getCode() == parkingLotManagerCode) {
                return enums;
            }
        }
        return null;
    }

    public static ParkingLotManagerEnum fromMessage(String parkingLotManagerMessage) {
        if (ObjectUtil.isNull(parkingLotManagerMessage)) {
            return null;
        }

        for (ParkingLotManagerEnum enums : ParkingLotManagerEnum.values()) {
            if (enums.getName() == parkingLotManagerMessage) {
                return enums;
            }
        }
        return null;

    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.message;
    }
}
