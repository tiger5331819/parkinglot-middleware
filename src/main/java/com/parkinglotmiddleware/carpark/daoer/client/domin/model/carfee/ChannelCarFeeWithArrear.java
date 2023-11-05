package com.parkinglotmiddleware.carpark.daoer.client.domin.model.carfee;

import com.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

/**
 * 通道号车辆费用(欠费)
 *
 * @author Suhuyuan
 */
@Data
public class ChannelCarFeeWithArrear extends DaoerBase {
    private String dsn;
}
