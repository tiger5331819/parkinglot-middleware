package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.coupon;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 优惠券查询
 *
 * @author Suhuyuan
 */
@Setter
@Getter
public class CouponQuery extends DaoerBase {

    private String openId;

    public CouponQuery(String uri) {
        super(uri);
    }

    public void setOpenId(String openId) {
        if(!StrUtil.isBlank(openId)) {
            this.openId = openId;
        }
    }
}
