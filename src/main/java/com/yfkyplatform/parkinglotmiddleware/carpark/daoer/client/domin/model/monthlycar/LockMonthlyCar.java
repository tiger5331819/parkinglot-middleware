package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.monthlycar;

import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 月租车锁车/解锁
 *
 * @author Suhuyuan
 */
@Getter
@Setter
public class LockMonthlyCar extends DaoerBase {
    /**
     * 默认为1, 1锁定，0解锁
     */
    private Integer status;

    public LockMonthlyCar(String uri) {
        super(uri);
    }
}
