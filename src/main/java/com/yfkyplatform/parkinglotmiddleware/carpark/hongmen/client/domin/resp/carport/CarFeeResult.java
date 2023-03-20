package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.resp.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 临时车缴费金额结果
 *
 * @author Suhuyuan
 */
@Data
public class CarFeeResult {
    /**
     * 停车场编号
     */
    private String parkingId;

    /**
     * 车牌号
     */
    private String carPlateNo;

    /**
     * 停车卡号
     */
    private String cardNo;

    /**
     * 车辆类型
     * <p>
     * 1 小车
     * 2 大车
     * 3 超大车
     */
    private String carType;

    /**
     * 该车入场时间 yyyyMMddHHmmss
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
    private LocalDateTime enterTime;

    /**
     * 停车时长（分钟数)
     */
    private Integer parkingMinute;

    /**
     * 应支付金额 单位：分
     */
    private BigDecimal payValue;

    /**
     * 停车流水号
     * <p>
     * 标识某次具体停车事件，同一停车场唯一
     */
    private String parkingSerial;

    /**
     * 停车支付订单号
     * <p>
     * 同一停车场唯一
     */
    private String parkingOrder;

}
