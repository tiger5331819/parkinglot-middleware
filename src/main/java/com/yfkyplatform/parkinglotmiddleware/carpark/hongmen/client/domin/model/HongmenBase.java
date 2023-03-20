package com.yfkyplatform.parkinglotmiddleware.carpark.hongmen.client.domin.model;


import lombok.Data;

/**
 * 红门请求基础模型
 *
 * @author Suhuyuan
 */
@Data
public class HongmenBase<TRequestData> {

    /**
     * 请求类型
     */
    protected String type;

    /**
     * 应用ID （需向红门申请）
     */
    private String appId;

    /**
     * 请求 ID，由请求者生成并保证其唯一性，主要用于日志查询，推荐用 UUID
     */
    private String requestId;

    /**
     * 时间戳，格式：yyyyMMddHHmmss
     */
    private String timestamp;

    /**
     * 请求签名，32 位小写字母，其中的 appId 和 appSecret 由红门提供
     */
    private String sign;

    /**
     * 主体
     */
    private TRequestData body;
}
