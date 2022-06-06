package com.yfkyplatform.parkinglot.domain.manager.container.ability.carport;

import lombok.Data;

/**
 * 无牌车出场结果
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarOutResult {
    /**
     * 通过openid生成的临时车牌
     */
    private String carNo;
}
