package com.ziroom.suzaku.main.param;

import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class SkuSelectReqParam extends PageCommonParam{

    /**
     * 资产sku
     */
    private String skuId;

    /**
     * 资产名称
     */
    private String skuName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 资产型号
     */
    private String type;

    /**
     * 三级分类id
     */
    private String categoryId;

    /**
     * 三级分类
     */
    private String categoryName;

    /**
     * sku描述
     */
    private String attributeDesc;

}
