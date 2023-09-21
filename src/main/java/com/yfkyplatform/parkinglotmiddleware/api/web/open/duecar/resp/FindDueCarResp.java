package com.yfkyplatform.parkinglotmiddleware.api.web.open.duecar.resp;

import com.yfkyframework.common.core.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


/**
 * 查询是否催缴车辆响应
 *
 * @author Suhuyuan
 */
@Data
public class FindDueCarResp extends BaseModel {

    @Schema(description = "是否是催缴车辆 1：是，2：否")
    private Integer dueCar;

}
