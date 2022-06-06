package com.yfkyplatform.parkinglot.carpark.daoer.client.domin.model.token;

import lombok.Data;

/**
 * 道尔云 Token 返回结果
 *
 * @author Suhuyuan
 */
@Data
public class TokenResult {
    private int status;
    private String msg;
    private String data;
}
