package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.guest;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 访客信息—拥有ID
 *
 * @author Suhuyuan
 */
@Data
public class GuestWithId extends GuestMessage{
    /**
     * 	唯一记录标识
     */
    private String objectId;

    public GuestWithId(String objectId) {
        if (!StrUtil.isBlank(objectId)) {
            this.objectId = objectId;
        }
    }
}
