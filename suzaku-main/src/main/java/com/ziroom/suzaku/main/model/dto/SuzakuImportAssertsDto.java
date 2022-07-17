package com.ziroom.suzaku.main.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *  资产列表vo
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */
@Data
public class SuzakuImportAssertsDto{

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "连接入库单-入库单主键")
    private String importBillNum;

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "SKU")
    private String sku;

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "资产描述")
    private String skuDesc;

    @ApiModelProperty(value = "三级分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String category;

    @ApiModelProperty(value = "品牌")
    private String brandName;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "SN码")
    private String snCode;

    @ApiModelProperty(value = "型号")
    private String skuType;

    @ApiModelProperty(value = "单位")
    private String unit;

    @ApiModelProperty(value = "MAC地址/IMEI")
    private String macImic;

    @ApiModelProperty(value = "购置单价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "购置日期")
    private String purchaseDate;

    @ApiModelProperty(value = "维保到期日期")
    private String maintainOverdue;

    @ApiModelProperty(value = "使用期限")
    private String period;

    @ApiModelProperty(value = "库存报警阈值")
    private String threshold;

    @ApiModelProperty(value = "规格描述")
    private String attributeStr;

    @ApiModelProperty(value = "是否上传成功，0 - 失败，1 - 成功    默认0")
    private String successFlag;

    @ApiModelProperty(value = "资产状态")
    private String assetsState;

    @ApiModelProperty(value = "使用人")
    private String usePeople;

    @ApiModelProperty(value = "使用人部门")
    private String useDepartment;

    @ApiModelProperty(value = "使用人")
    private String operator;

    @ApiModelProperty(value = "门店编码")
    private String stationNo;

    @ApiModelProperty(value = "门店名称")
    private String stationName;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "管理类型")
    private String managerType;


}
