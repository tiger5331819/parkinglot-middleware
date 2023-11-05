package com.parkinglotmiddleware.carpark.hongmen.client.domin.model.monthlycar;


import lombok.Data;

/**
 * 会员车辆注册
 *
 * @author Suhuyuan
 */
@Data
public class CreateMonthlyCar {

    /**
     * 停车场编号
     */
    private String parkingId;
    /**
     * 车牌号码
     */
    private String vehiclePlate;
    /**
     * 车主名称
     */
    private String ownerName;
    /**
     * 车主手机号
     */
    private String ownerTel;

    /**
     * 车主地址
     */
    private String ownerAddress;

    /**
     * 车主编码/工号
     */
    private String ownerCode;
}
