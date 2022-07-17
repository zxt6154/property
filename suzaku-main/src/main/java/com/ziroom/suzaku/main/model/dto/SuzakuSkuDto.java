package com.ziroom.suzaku.main.model.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 资产sku基础数据Dto
 * @author xuzeyu
 */
@Data
public class SuzakuSkuDto {

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
    private List<String> catTree;

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
     * 规格信息
     */
    private Map<String, String> attibuteMap;

}
