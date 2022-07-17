package com.ziroom.suzaku.main.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 资产分类扩展属性表
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="资产分类扩展属性表", description="资产分类扩展属性表")
public class SuzakuClassifyCustom implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类扩展id")
    @TableId(value = "id",type = IdType.AUTO)
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
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;


}
