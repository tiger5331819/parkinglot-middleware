package com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model.token;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglot.daoerparkinglot.client.domin.model.DaoerBase;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 道尔云Token
 *
 * @author Suhuyuan
 */
@Getter
@NoArgsConstructor
public class DaoerToken extends DaoerBase {
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 令牌
     */
    private String token;

    public DaoerToken(String parkId,String appName){
        super("api/index/auth/token");
        this.appName=appName;
    }

    public void setToken(String token){
        if(!StrUtil.isBlank(token)){
            this.token=token;
        }
    }
}
