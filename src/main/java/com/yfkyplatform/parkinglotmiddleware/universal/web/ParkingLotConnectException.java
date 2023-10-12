package com.yfkyplatform.parkinglotmiddleware.universal.web;

/**
 * 远端车场链接异常
 *
 * @author Suhuyuan
 */

public class ParkingLotConnectException extends RuntimeException{
    private static final String message="远端车场链接异常";
    public ParkingLotConnectException() {
        super(message);
    }

    public ParkingLotConnectException(Throwable cause) {
        super(message,cause);
    }
}
