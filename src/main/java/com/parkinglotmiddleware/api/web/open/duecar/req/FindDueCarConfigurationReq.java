package com.parkinglotmiddleware.api.web.open.duecar.req;

import com.yfkyframework.common.core.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 查询催缴联动配置
 *
 * @author Suhuyuan
 */
@Data
public class FindDueCarConfigurationReq extends BaseModel {

    @Schema(description = "车场ID（第三方车场ID）")
    @NotNull(message = "车场ID（第三方车场ID）不能为空")
    private String parkNo;
}
