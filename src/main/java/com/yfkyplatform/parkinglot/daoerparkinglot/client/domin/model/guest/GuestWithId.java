package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model.guest;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * 访客信息—拥有ID
 *
 * @author Suhuyuan
 */
@Getter
@Setter
public class GuestWithId extends GuestMessage{
    /**
     * 	唯一记录标识
     */
    private String objectId;

    public GuestWithId(String uri,String objectId) {
        super(uri);
        if(!StrUtil.isBlank(objectId)) {
            this.objectId=objectId;
        }
    }
}
