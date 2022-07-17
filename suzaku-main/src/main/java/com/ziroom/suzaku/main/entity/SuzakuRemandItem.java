package com.ziroom.suzaku.main.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author libingsi
 * @since 2021-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuRemandItem对象", description="")
public class SuzakuRemandItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "退还id")
    private String remandId;

    @ApiModelProperty(value = "资产条码")
    private String assertCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertCode;

    @ApiModelProperty(value = "skuId")
    private String skuId;

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "退还人")
    private String usePeople;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
