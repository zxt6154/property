package com.ziroom.suzaku.main.model.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 资产数据Dto
 * @author xuzeyu
 */
@Data
public class SuzakuAssertDto {

    /**
     * 资产条码
     */
    private String assertCode;

    /**
     * 旧资产条码
     */
    private String oldAssertCode;

    /**
     * sku
     */
    private String skuId;

    /**
     * sn码
     */
    private String SN;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 购置单价
     */
    private BigDecimal purchasePrice;

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
     * 分类名称
     */
    private String categoryName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 供应商
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
     * 使用人
     */
    private String usePeople;

    /**
     * 使用部门
     */
    private String useDepartment;

}
