package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.resp.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

/**
 * 临时车缴费结果
 *
 * @author Suhuyuan
 */
@Data
public class SearchCarResult {
    /**
     * 记录 ID
     */
    private String id;

    /**
     * 区域 ID
     */
    private String areaId;

    /**
     * 卡编号
     */
    private String cardNo;

    /**
     * 卡类型
     * <p>
     * 1. 临时车辆，2. 月卡车辆，3. 贵宾车辆，4.储值车辆，0. 其它未知
     */
    private String cardType;

    /**
     * 入场设备序列号
     */
    private String enterDeviceNo;

    /**
     * 入场前端流水号
     */
    private String enterSerialNo;

    /**
     * 入场岗亭
     */
    private String enterStation;

    /**
     * 入场岗亭 id
     */
    private String enterStationId;

    /**
     * 入场时间
     * <p>
     * yyyy-MM-dd HH:mm:ss
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String enterTime;

    /**
     * 入场值班人员 ID
     */
    private String enterUserId;

    /**
     * 停车场会员ID
     */
    private String memberId;

    /**
     * 卡编号
     */
    private String parkingId;

    /**
     * 车辆号牌
     */
    private String vehiclePlate;

    /**
     * 车辆类型
     * <p>
     * 1：小车，2：大车，3：超大车
     */
    private String vehicleType;
}
