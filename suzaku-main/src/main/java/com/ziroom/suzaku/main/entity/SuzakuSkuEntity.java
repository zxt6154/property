package com.ziroom.suzaku.main.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
import lombok.Getter;

/**
 * 资产sku基础数据
 * @author xuzeyu
 */
@Data
@Getter
public class SuzakuSkuEntity {

    /**
     * 主键
     */
    private Long id;

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
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String moditiyTime;

    /**
     * 修改人
     */
    private String operatorName;



}
