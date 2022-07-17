package com.ziroom.suzaku.main.param;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class ApplyFormSelectReqParam extends PageCommonParam{

    /**
     * 资产名称
     */
    private String skuName;

    /**
     * 资产条码
     */
    private String assertsCode;

    /**
     * 领借人工号  1
     */
    private String userNo;

    /**
     * 使用人
     */
    private String useName;

    /**
     * 领用单号  1
     */
    private String orderId;

    /**
     * 领用单状态  1
     */
    private Integer status;

    /**
     * 领借类型  1
     */
    private Integer orderType;

    /**
     * 使用部门  1
     */
    private String useDepartment;

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 分类
     */
    private String categoryId;

    /**
     *供应商
     */
    private String supplierName;

}
