package com.ziroom.suzaku.main.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 仓库表
 * </p>
 *
 * @author libingsi
 * @since 2021-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuDepot对象", description="仓库表")
public class SuzakuDepotDto implements Serializable {

    @ApiModelProperty(value = "唯一id")
    private Long id;

    @ApiModelProperty(value = "仓库名称")
    private String depotName;

    @ApiModelProperty(value = "创建日期")
    private String createTime;

    @ApiModelProperty(value = "更新日期")
    private String updateTime;


}
