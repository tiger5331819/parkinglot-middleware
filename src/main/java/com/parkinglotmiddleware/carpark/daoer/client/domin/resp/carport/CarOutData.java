package com.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 车辆出场数据
 *
 * @author Suhuyuan
 */
@Data
public class CarOutData {
    /**
     * 车牌号
     */
    private String carNo;
    /**
     * 车类型ID
     */
    private int cardTypeId;
    /**
     * 车类型名称，如临时车A
     */
    private String cardTypeName;

    /**
     * 入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime inTime;
    /**
     * 入场图片
     */
    private String inPic;
    /**
     * 出场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime outTime;
    /**
     * 出场图片
     */
    private String outPic;

    /**
     * 支付信息
     */
    private List<PayData> payment;

    /**
     * 入场方式，手工入场、校正入场
     */
    private String inWayName;
    /**
     * 出场方式，手工出场、校正出场
     */
    private String outWayName;
    /**
     * 入场通道名称
     */
    private String entranceName;
    /**
     * 入场操作员
     */
    private String entranceUserName;
    /**
     * 出场通道名称
     */
    private String appearancesName;
    /**
     * 出场操作员
     */
    private String appearancesUserName;
    /**
     * 是否小车场 0 否 1是
     */
    private int small;
}
