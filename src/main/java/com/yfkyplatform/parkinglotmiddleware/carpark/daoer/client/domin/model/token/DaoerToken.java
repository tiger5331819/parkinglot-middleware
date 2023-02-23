package com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.token;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.model.DaoerBase;
import lombok.Data;


/**
 * 道尔云Token
 *
 * @author Suhuyuan
 */
@Data
public class DaoerToken extends DaoerBase {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 令牌
     */
    private String token;

    public DaoerToken(String appName) {
        this.appName = appName;
    }

    public void setToken(String token) {
        if (!StrUtil.isBlank(token)) {
            this.token = token;
        }
    }


}
