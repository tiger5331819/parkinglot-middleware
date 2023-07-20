package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.carfee;

import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;

/**
 * 通道号车辆费用
 *
 * @author Suhuyuan
 */
@Data
public class ChannelCarFee extends JieShunBase {
    private String dsn;
    private String openId;
}
