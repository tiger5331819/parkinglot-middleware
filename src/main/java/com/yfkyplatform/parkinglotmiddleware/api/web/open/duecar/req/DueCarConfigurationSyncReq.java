package com.yfkyplatform.parkinglotmiddleware.api.web.open.duecar.req;

import com.yfkyframework.common.core.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalTime;

/**
 * 催缴配置同步请求
 *
 * @author Suhuyuan
 */
@Data
public class DueCarConfigurationSyncReq extends BaseModel {

    @Schema(description = "车场ID（第三方车场ID）")
    private String parkNo;

    @Schema(description = "是否不可进 0:启用,1:禁用")
    private Integer urgepayNotIn;

    @Schema(description = "是否不可出 0:启用,1:禁用")
    private Integer urgepayNotOut;

    @Schema(description = "生效开始时间")
    private LocalTime startTime;

    @Schema(description = "生效结束时间")
    private LocalTime closeTime;
}
