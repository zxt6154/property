package com.ziroom.suzaku.main.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class SkuCreateReqParam {

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
     * 品牌名称
     */
    private String brandName;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 型号
     */
    private String skuType;

    /**
     * 计量单位
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
     * 三级分类id
     */
    private String categoryId;

    /**
     * 三级分类名称
     */
    private List<String> categoryName;

    /**
     * 规格组(扩展信息)
     */
    private Map<String,String> attributes;

    /**
     * 操作人
     */
    private String operatorName;

}
