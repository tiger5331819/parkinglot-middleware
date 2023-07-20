package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.carfee;

import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;

/**
 * 通道号车辆费用(欠费)
 *
 * @author Suhuyuan
 */
@Data
public class ChannelCarFeeWithArrear extends JieShunBase {
    private String dsn;
}
