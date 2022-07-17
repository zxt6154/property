package com.ziroom.suzaku.main.model.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 资产数据
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuImportAsserts对象", description="")
public class SuzakuImportAssertsPo implements Serializable {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "连接入库单-入库单主键")
    private String importBillNum;

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "SKU")
    private String sku;

    @ApiModelProperty(value = "skuName")
    private String skuName;

    @ApiModelProperty(value = "三级分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String category;

    @ApiModelProperty(value = "品牌")
    private String brandName;

    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @ApiModelProperty(value = "规格描述")
    private String attributeStr;

    @ApiModelProperty(value = "型号")
    private String skuType;

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

    @ApiModelProperty(value = "使用人")
    private String usePeople;

    @ApiModelProperty(value = "使用人归属部门")
    private String useDepartment;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;



}
