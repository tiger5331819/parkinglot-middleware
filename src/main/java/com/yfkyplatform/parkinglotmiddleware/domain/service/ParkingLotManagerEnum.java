package com.yfkyplatform.parkinglotmiddleware.domain.service;

/**
 * 停车场管理映射
 *
 * @author tiger
 */

public enum ParkingLotManagerEnum {
    /**
     * 道尔 code:4
     */
    Daoer(4, "Daoer");

    private final int value;

    private final String message;

    ParkingLotManagerEnum(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public static ParkingLotManagerEnum ValueOf(int parkingLotManagerCode) {
        switch (parkingLotManagerCode) {
            case 4:
                return Daoer;
            default:
                return null;
        }
    }

    public static ParkingLotManagerEnum ValueOf(String parkingLotManagerMessage) {
        switch (parkingLotManagerMessage) {
            case "Daoer":
                return Daoer;
            default:
                return null;
        }
    }

    public int value() {
        return this.value;
    }

    public String message() {
        return this.message;
    }
}
