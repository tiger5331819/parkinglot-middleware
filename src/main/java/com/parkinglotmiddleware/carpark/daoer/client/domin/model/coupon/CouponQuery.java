package com.parkinglotmiddleware.carpark.daoer.client.domin.model.coupon;

import cn.hutool.core.util.StrUtil;
import com.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;

/**
 * 优惠券查询
 *
 * @author Suhuyuan
 */
@Data
public class CouponQuery extends DaoerBase {

    private String openId;

    public void setOpenId(String openId) {
        if(!StrUtil.isBlank(openId)) {
            this.openId = openId;
        }
    }
}
