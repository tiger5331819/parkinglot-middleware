package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model.coupon;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model.DaoerBase;
import lombok.Getter;

/**
 * 使用优惠券
 *
 * @author Suhuyuan
 */
@Getter
public class CouponUse extends DaoerBase {

    private String objectId;

    public CouponUse(String uri) {
        super(uri);
    }

    public void setObjectId(String objectId) {
        if(!StrUtil.isBlank(objectId)) {
            this.objectId = objectId;
        }
    }
}
