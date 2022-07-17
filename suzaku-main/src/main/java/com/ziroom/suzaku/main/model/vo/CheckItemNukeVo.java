package com.ziroom.suzaku.main.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 盘点单审核处理请求参数
 * @author xuzeyu
 */
@Getter
@Setter
@ApiModel(value="盘点单审核处理请求参数", description="")
public class CheckItemNukeVo implements Serializable {

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "资产skuId")
    private String sku;

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "SN码")
    private String snCode;

    @ApiModelProperty(value = "MAC地址/IMEI")
    private String macImic;

    @ApiModelProperty(value = "购置日期")
    private String purchaseDate;

    @ApiModelProperty(value = "维保到期日期")
    private String maintainOverdue;

    @ApiModelProperty(value = "购置单价")
    private BigDecimal purchasePrice;


}
