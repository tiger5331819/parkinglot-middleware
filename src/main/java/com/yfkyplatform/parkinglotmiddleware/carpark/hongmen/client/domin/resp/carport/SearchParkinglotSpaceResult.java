package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.resp.carport;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

/**
 * 车位信息查询
 *
 * @author Suhuyuan
 */
@Data
public class SearchParkinglotSpaceResult {
    /**
     * 停车场编号
     */
    private String parkingId;

    /**
     * 停车场名称
     */
    private String parkingName;

    /**
     * 记录更新时间  yyyyMMddHHmmss
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss", timezone = "GMT+8")
    private String updateTime;

    /**
     * 车位总数
     */
    private Integer total;

    /**
     * 剩余车位数
     */
    private Integer available;

    /**
     * 剩余临时车位数
     */
    private Integer tempValid;

    /**
     * 固定车位总数
     */
    private Integer fixedTotal;

    /**
     * 剩余固定车位数
     */
    private Integer fixedValid;

    /**
     * 预定车位总数
     */
    private Integer orderTotal;

    /**
     * 已使用预定车位数
     */
    private Integer orderOccupy;

}
