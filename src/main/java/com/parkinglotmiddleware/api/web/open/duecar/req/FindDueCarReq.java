package com.parkinglotmiddleware.api.web.open.duecar.req;

import com.yfkyframework.common.core.model.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 查询是否催缴车辆请求
 *
 * @author Suhuyuan
 */
@Data
public class FindDueCarReq extends BaseModel {

    @Schema(description = "车场ID（第三方车场ID）")
    @NotNull(message = "车场ID（第三方车场ID）不能为空")
    private String parkNo;

    @Schema(description = "通道编号")
    @NotNull(message = "通道编号不能为空")
    private String dsn;

    @Schema(description = "通道属性 1：入口，2：出口")
    @NotNull(message = "通道属性不能为空")
    private Integer inOrOut;

    @Schema(description = "车牌号")
    @Length(max = 9, message = "车牌长度过长")
    @NotNull(message = "车牌号不能为空")
    private String plateNumber;

    @Schema(description = "车牌颜色 1:蓝,2:绿,3:黄,4:黑,5:白,9其他")
    private Integer plateColor;

    @Schema(description = "车场ID 0:不限制,1:小型车,2:中型车,3:大型车,4:公务车")
    private Integer vehicleType;
}
