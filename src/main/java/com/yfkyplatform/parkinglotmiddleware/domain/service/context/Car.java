package com.yfkyplatform.parkinglotmiddleware.domain.service.context;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 车辆基础信息
 *
 * @author Suhuyuan
 */
@Data
public class Car {
    private String carNo;

    /**
     * 车辆月租类型
     */
    private Integer typeId = -1;

    /**
     * 月租开始时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime typeStartTime;

    /**
     * 月租结束时间 yyyy-MM-dd HH:mm:ss
     */
    private LocalDateTime typeEndTime;

    /**
     * 车主姓名
     */
    private String contactName;

    /**
     * 车主电话号码
     */
    private String contactPhone;

    /**
     * 入场照片
     */
    private String inPic;

    /**
     * 车场唯一入场记录ID
     */
    private String inId;

    /**
     * 当前订单信息
     */
    private PayMessage order;

    /**
     * 历史欠费订单信息
     */
    private List<PayMessage> arrearOrder;
}
