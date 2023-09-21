package com.yfkyplatform.parkinglotmiddleware.api.web.open.duecar.resp;

import com.yfkyframework.common.core.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

/**
 * 查询催缴联动配置
 *
 * @author Suhuyuan
 */
@Data
public class FindDueCarConfigurationResp extends BaseModel {

    @Schema(description = "是否不可进 0:启用,1:禁用")
    private Integer urgepayNotIn;

    @Schema(description = "是否不可出 0:启用,1:禁用")
    private Integer urgepayNotOut;

    @Schema(description = "生效开始时间")
    private LocalTime startTime;

    @Schema(description = "生效结束时间")
    private LocalTime closeTime;
}
