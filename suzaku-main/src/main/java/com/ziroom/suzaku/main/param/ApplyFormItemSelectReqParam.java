package com.ziroom.suzaku.main.param;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class ApplyFormItemSelectReqParam {

    /**
     * 资产名称
     */
    private String skuName;

    /**
     * 资产条码
     */
    private String assertsCode;

    /**
     * 领用单号
     */
    private List<String> orderList;

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

    /**
     * 索引
     */
    private Integer index;

    /**
     * 分页大小
     */
    private Integer pageSize;

}
