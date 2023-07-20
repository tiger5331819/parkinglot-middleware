package com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.token;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.jieshun.client.domin.model.JieShunBase;
import lombok.Data;


/**
 * 道尔云Token
 *
 * @author Suhuyuan
 */
@Data
public class JieShunToken extends JieShunBase {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 令牌
     */
    private String token;

    public JieShunToken() {

    }

    public JieShunToken(String appName) {
        this.appName = appName;
    }

    public void setToken(String token) {
        if (!StrUtil.isBlank(token)) {
            this.token = token;
        }
    }


}
