package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.monthlycar;

import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;

/**
 * 月租车锁车/解锁
 *
 * @author Suhuyuan
 */
@Data
public class LockMonthlyCar extends JieShunBase {
    /**
     * 默认为1, 1锁定，0解锁
     */
    private Integer status;

}
