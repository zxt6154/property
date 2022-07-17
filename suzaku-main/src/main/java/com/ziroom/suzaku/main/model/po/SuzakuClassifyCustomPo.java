package com.ziroom.suzaku.main.model.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资产分类扩展属性表
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Data
public class SuzakuClassifyCustomPo implements Serializable {

    @ApiModelProperty(value = "分类扩展id")
    private Long id;

    @ApiModelProperty(value = "分类id")
    private String classifyId;

    @ApiModelProperty(value = "分类扩展名称")
    private String customName;

    @ApiModelProperty(value = "是否必填（0：必填 1： 非必填）")
    private Integer requiredFlag;

    @ApiModelProperty(value = "是否应用于检索（0：检索  1： 非检索")
    private Integer searchFlag;

    @ApiModelProperty(value = "操作人工号")
    private String operatorCode;

    @ApiModelProperty(value = "操作人名称")
    private String operatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
