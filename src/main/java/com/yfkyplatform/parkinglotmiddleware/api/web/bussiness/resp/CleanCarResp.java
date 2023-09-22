package com.yfkyplatform.parkinglotmiddleware.api.web.bussiness.resp;

import lombok.Data;

/**
 * 人工清场结果
 *
 * @author Suhuyuan
 */
@Data
public class CleanCarResp {
    private Long orderId;

    private Boolean success;
}
