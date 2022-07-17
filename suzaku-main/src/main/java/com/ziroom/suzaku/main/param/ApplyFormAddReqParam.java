package com.ziroom.suzaku.main.param;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 登记单添加请求参数
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplyFormAddReqParam {

    /**
     * 领借人工号
     */
    private String userNo;

    /**
     * 领借人
     */
    private String userName;

    /**
     * 操作人
     */
    private String operateName;

    /**
     * 使用部门
     */
    private String department;

    /**
     * 管理类型
     */
    private Integer managerType;

    /**
     * 门店/工作站
     */
    private String stationName;

    /**
     * 领用/借用 1领用 2借用
     */
    private Integer type;

    /**
     * 预计归还时间
     */

    private String returnDate;

    /**
     * 领借原因
     */
    private String desc;

    private List<AssertModel> assertModels;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AssertModel {
        /**
         * 登记申请单号
         */
        private String applyOrderId;

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
         * 资产名称
         */
        private String skuName;

        /**
         * 三级分类id
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

    }

}
