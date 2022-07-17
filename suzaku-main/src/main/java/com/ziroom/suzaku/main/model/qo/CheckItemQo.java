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
public class CheckItemQo extends PageCommonParam {

    @ApiModelProperty(value = "资产id")
    private String assertCode;

    @ApiModelProperty(value = "盘点单父单号")
    private String checkId;

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "盘点人")
    private String userName;

    @ApiModelProperty(value = "员工盘点状态")
    private Integer staffStatus;

    @ApiModelProperty(value = "管理员盘点状态")
    private Integer adminStatus;
}
