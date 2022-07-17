package com.ziroom.suzaku.main.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author libingsi
 * @date 2021/10/9 16:35
 */
@Data
@ApiModel(value="资产分类扩展属性表", description="资产分类扩展属性表")
public class SuzakuClassifyCustomVo {

    @ApiModelProperty(value = "分类扩展id")
    private Long id;

    @ApiModelProperty(value = "分类id")
    private String classifyId;

    @ApiModelProperty(value = "分类扩展名称")
    private String customName;

    @ApiModelProperty(value = "是否必填（0：必填 1： 非必填）")
    private Integer requiredFlag;

    private Boolean required;

    @ApiModelProperty(value = "是否应用于检索（0：检索  1： 非检索")
    private Integer searchFlag;

    @ApiModelProperty(value = "操作人工号")
    private String operatorCode;

    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;
}
