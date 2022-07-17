package com.ziroom.suzaku.main.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author libingsi
 * @since 2021-10-26
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="SuzakuRemandItem对象", description="")
public class SuzakuRemandItemVo implements Serializable {

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

    @ApiModelProperty(value = "使用人")
    private String usePeople;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;


}
