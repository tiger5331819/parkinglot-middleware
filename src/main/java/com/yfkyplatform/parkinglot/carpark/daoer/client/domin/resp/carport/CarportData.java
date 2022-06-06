package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.resp.carport;

import lombok.Data;

/**
 * 车位使用情况数据
 *
 * @author Suhuyuan
 */
@Data
public class CarportData {
    /**
     * vip车位 (混合车位时,舍弃该值)
     */
    private int fixed;
    /**
     * 临时车位 (混合车位时,舍弃该值)
     */
    private int temporaty;
    /**
     * 总车位
     */
    private int total;
}
