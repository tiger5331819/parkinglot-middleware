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

    private final String name;

    ParkingLotManagerEnum(int value, String message) {
        this.code = value;
        this.name = message;
    }

    public static ParkingLotManagerEnum ValueOf(Integer parkingLotManagerCode) {
        if (ObjectUtil.isNull(parkingLotManagerCode)) {
            return null;
        }
        switch (parkingLotManagerCode) {
            case 4:
                return Daoer;
            case 3:
                return Lifang;
            default:
                return null;
        }
    }

    public static ParkingLotManagerEnum ValueOf(String parkingLotManagerMessage) {
        if (ObjectUtil.isNull(parkingLotManagerMessage)) {
            return null;
        }
        switch (parkingLotManagerMessage) {
            case "Daoer":
                return Daoer;
            case "Lifang":
                return Lifang;
            default:
                return null;
        }
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
