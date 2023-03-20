package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.resp.monthlycar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

/**
 * 月租车缴费结果
 *
 * @author Suhuyuan
 */
@Data
public class MonthlyCarPayResult {
    /**
     * 停车场编号
     */
    private String parkingId;

    /**
     * 停车支付订单号
     */
    private String parkingOrder;

    /**
     * 充值前截止日期
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
    private String expireTime;

    /**
     * 充值后截止日期
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
    private String validTime;
}
