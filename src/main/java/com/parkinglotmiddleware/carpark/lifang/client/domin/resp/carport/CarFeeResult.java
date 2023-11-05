package com.parkinglotmiddleware.carpark.lifang.client.domin.resp.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.parkinglotmiddleware.carpark.lifang.client.domin.resp.LifangBaseResp;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 临时车缴费金额结果
 *
 * @author Suhuyuan
 */
@Data
public class CarFeeResult extends LifangBaseResp {
    /**
     * 车牌号码
     */
    private String carCode;
    /**
     * 该车入场时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime inTime;
    /**
     * 支付时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime payTime;
    /**
     * 总金额（单位：分）=（实付金额+减免金额）
     */
    private BigDecimal chargeMoney;
    /**
     * 实付金额（单位：分）
     */
    private BigDecimal paidMoney;
    /**
     * 减免金额（单位：分）
     */
    @JsonProperty("JMMoney")
    private BigDecimal JMMoney;
    /**
     * 图片格式暂定
     */
    private String carImage;
    /**
     * 是否允许减免 0 允许，1 不允许
     */
    private int isDiscount;
    /**
     * 备注
     */
    private String note;
    /**
     * 剩余出场时间（单位：分钟）
     */
    private int remainLeaveTime;

    private String serial;
    private String parkingArea;
    private String parkingNo;
    private String parkingSeconds;
}
