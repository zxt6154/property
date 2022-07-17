package com.ziroom.suzaku.main.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ImportAssertSkuDto {


    @ApiModelProperty(value = "连接入库单-入库单主键")
    private String importBillNum;

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "SKU")
    private String sku;

    @ApiModelProperty(value = "分类名称")
    private String category;

    @ApiModelProperty(value = "规格描述")
    private String attributeStr;


    @ApiModelProperty(value = "SN码")
    private String snCode;

    @ApiModelProperty(value = "MAC地址/IMEI")
    private String macImic;

    @ApiModelProperty(value = "购置单价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "购置日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime purchaseDate;

    @ApiModelProperty(value = "维保到期日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime maintainOverdue;

    @ApiModelProperty(value = "是否上传成功，0 - 失败，1 - 成功    默认0")
    private String successFlag;

    @ApiModelProperty(value = "资产状态，资产状态 1闲置 2代签收 3领用 4借用 5退库中 6处置中 7转移中 8报废 9遗失 10变卖")
    private Integer assetsState;

    @ApiModelProperty(value = "管理类型")
    private Integer manageType;

    @ApiModelProperty(value = "使用人")
    private String usePeople;

    @ApiModelProperty(value = "使用人归属部门")
    private String useDepartment;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "门店编码")
    private String stationNo;

    @ApiModelProperty(value = "门店名称")
    private String stationName;

    @ApiModelProperty(value = "生成资产编码记录key")
    private String codeKey;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    // sku
    /**
     * 资产sku
     */
    private String skuId;

    /**
     * 资产名称
     */
    private String skuName;

    /**
     * 资产描述
     */
    private String skuDesc;

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 分类tree
     */
    private String categoryTree;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 型号
     */
    private String skuType;

    /**
     * 单位
     */
    private String unit;

    /**
     * 使用期限
     */
    private String period;

    /**
     * 库存报警阈值
     */
    private String threshold;

    /**
     * 规格（扩展信息）
     */
    private String attributeDesc;


    /**
     * 修改人
     */
    private String operatorName;


}
