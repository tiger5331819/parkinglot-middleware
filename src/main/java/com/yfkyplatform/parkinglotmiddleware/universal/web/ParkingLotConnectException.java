package com.yfkyplatform.parkinglotmiddleware.universal.web;

/**
 * 远端车场链接异常
 *
 * @author Suhuyuan
 */

public class ParkingLotConnectException extends RuntimeException{
    private static final String MESSAGE ="远端车场链接异常";
    ParkingLotConnectException() {
        super(MESSAGE);
    }

    public ParkingLotConnectException(Throwable cause) {
        super(MESSAGE,cause);
    }
}
