package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


/**
 * 车辆入场数据
 *
 * @author Suhuyuan
 */
@Data
public class CarInData {
    /**
     * 入场通道ID
     */
    private String entranceId;
    /**
     * 入场通道名称
     */
    private String entranceName;
    /**
     * 入场操作员
     */
    private String entranceUserName;

    /**
     * 车牌号
     */
    private String carNo;
    /**
     * 名称
     */
    private String contactName;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private LocalDateTime inTime;

   /* public LocalDateTime getInTime() {
        return LocalDateTime.ofInstant(DateUtil.parse(inTime).toInstant(), ZoneId.systemDefault());
    }*/

    /**
     * 入场图片
     */
    private String inPic;

    /**
     * 入场方式代码
     */
    private int inWay;
    /**
     * 入场方式，手工入场、校正入场
     */
    private String inWayName;

    /**
     * 车场ID
     */
    private String parkingNo;
    /**
     * 车场名称
     */
    private String parkingName;
    /**
     * 是否小车场 0 否 1是
     */
    private int small;
}
