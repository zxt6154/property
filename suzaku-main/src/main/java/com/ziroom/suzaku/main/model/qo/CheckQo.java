package com.ziroom.suzaku.main.model.qo;

import com.ziroom.suzaku.main.param.PageCommonParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author libingsi
 * @date 2021/10/25 14:54
 */
@Getter
@Setter
public class CheckQo extends PageCommonParam {

    @ApiModelProperty(value = "盘点id")
    private String checkId;

    @ApiModelProperty(value = "盘点名称")
    private String checkName;

    @ApiModelProperty(value = "部门")
    private String department;

    @ApiModelProperty(value = "盘点状态")
    private Integer checkStatus;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
