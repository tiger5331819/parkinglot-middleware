package com.yfkyplatform.parkinglotmiddleware.api.web.req;

import lombok.Data;

import java.util.List;

/**
 * 人工清场
 *
 * @author Suhuyuan
 */
@Data
public class CleanCarReq {
    private String token;

    private List<Long> parkingLotIdList;

    private Long parkingLotId;

    private String chargeTimeStart;

    private String chargeTimeClose;

    private Integer size;
}
