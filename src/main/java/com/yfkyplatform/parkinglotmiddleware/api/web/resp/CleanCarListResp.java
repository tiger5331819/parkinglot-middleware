package com.yfkyplatform.parkinglotmiddleware.api.web.resp;

import lombok.Data;

import java.util.List;

/**
 * 人工清场结果
 *
 * @author Suhuyuan
 */
@Data
public class CleanCarListResp {
    private Integer total;

    private List<CleanCarResp> data;
}
