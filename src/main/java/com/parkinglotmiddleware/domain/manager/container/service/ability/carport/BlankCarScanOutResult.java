package com.parkinglotmiddleware.domain.manager.container.service.ability.carport;

import lombok.Data;

/**
 * 无牌车出场结果
 *
 * @author Suhuyuan
 */
@Data
public class BlankCarScanOutResult {
    /**
     * 通过openid生成的临时车牌
     */
    private String carNo;
}
