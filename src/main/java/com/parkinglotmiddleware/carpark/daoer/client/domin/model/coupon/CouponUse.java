package com.parkinglotmiddleware.carpark.daoer.client.domin.model.coupon;

import cn.hutool.core.util.StrUtil;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

/**
 * 使用优惠券
 *
 * @author Suhuyuan
 */
@Data
public class CouponUse extends DaoerBase {

    private String objectId;

    public void setObjectId(String objectId) {
        if(!StrUtil.isBlank(objectId)) {
            this.objectId = objectId;
        }
    }
}
