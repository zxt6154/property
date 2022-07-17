package com.ziroom.suzaku.main.entity;

import lombok.Data;

/**
 * 资产领借登记明细
 * @author xuzeyu
 */
@Data
public class SuzakuRegisterFormItemEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 登记申请单号
     */
    private String applyOrderId;

    /**
     * 资产条码
     */
    private String assertsCode;

    /**
     * 旧资产条码
     */
    private String oldAssertsCode;

    /**
     * skuId
     */
    private String skuId;

    /**
     * 资产名称
     */
    private String skuName;

    /**
     * 三级分类id
     */
    private String categoryId;

    /**
     * 分类
     */
    private String category;

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 供应商
     */
    private String supplierName;

    /**
     * 创建时间
     */
    private String createName;

    /**
     * 更新时间
     */
    private String moditiyTime;

    private SuzakuRegisterFormEntity registerFormEntity;


}
