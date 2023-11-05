package com.parkinglotmiddleware.carpark.hongmen.client.domin.model.carport;


import lombok.Data;

/**
 * 临停车费用
 *
 * @author Suhuyuan
 */
@Data
public class CarFee {

    /**
     * 停车场编号
     */
    private String parkingId;
    /**
     * 车牌号码
     * <p>
     * 与 cardNo 二选一
     */
    private String carPlateNo;
    /**
     * 停车卡号
     * <p>
     * 与 carPlateNo 二选一，若两者都提供，则 cardNo 优先
     */
    private String cardNo;
    /**
     * 查费方式
     * <p>
     * 1 普通查费，2,etc 查费（etc 查费时必填)
     */
    private String chargingMethod;
}
