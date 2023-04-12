package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.tool;

import lombok.Data;

import java.util.List;

/**
 * 各类地址生成结果
 *
 * @author Suhuyuan
 */
@Data
public class URLResult {
    /**
     * 无牌车入场通知
     */
    private List<BlankCarURL> blankCarURLList;
    /**
     * 通道出场缴费信息
     */
    private List<CarOutPayURL> carOutPayURLList;
}
