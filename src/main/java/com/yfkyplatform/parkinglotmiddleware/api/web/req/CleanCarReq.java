package com.yfkyplatform.parkinglotmiddleware.api.web.req;

import lombok.Data;

/**
 * 人工清场
 *
 * @author Suhuyuan
 */
@Data
public class CleanCarReq {
    private String token;

    private Long parkingLotId;

    private String chargeTimeStart;

    private String chargeTimeClose;

    private Integer size;
}
