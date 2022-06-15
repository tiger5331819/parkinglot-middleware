package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.carpark;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.Setter;

/**
 * 通道号车辆费用
 *
 * @author Suhuyuan
 */
@Getter
@Setter
public class ChannelCarFee extends DaoerBase {
    private String dsn;
    private String openId;

    public ChannelCarFee(String uri) {
        super(uri);
    }

    public void setOpenId(String openId) {
        if(!StrUtil.isBlank(openId)) {
            this.openId = openId;
        }
    }
}
