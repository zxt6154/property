package com.ziroom.suzaku.main.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class AssertSelectReqParam extends PageCommonParam{

    /**
     * 资产条码
     */
    private String assertsCode;
    /**
     * 资产id
     */
    private List<String> assertsCodes;

    //排除的资产条码
    private List<String> exceptExsit;

    /**
     * 资产名称
     */
    private String skuName;

    /**
     * 资产id
     */
    private List<String> skuIds;

    /**
     * 资产状态
     */
    private Integer assertStatus;

    /**
     * SN码
     */
    private String SN;

    /**
     * 管理类型
     */
    private Integer type;

    /**
     * 门店/工作站
     */
    private String stationName;

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
     * 入库单Ids
     */
    private List<String> importBills;

    /**
     * 规格组(扩展信息)
     */
    private Map<String,String> attributes;

    /**
     * 规格组
     */
    private String attributeStr;

    /**
     * 使用人
     */
    private String useName;

    /**
     * 资产状态
     */
    @ApiModelProperty(value = "资产状态 1闲置 2代签收 3领用 4借用 5退库中 6处置中 7转移中 8报废 9遗失 10变卖")
    private List<String> assertsStateList;

    /**
     * 部门范围
     */
    private String useDepartment;


    /**
     * 盘点资产状态，排查以下状态的资产
     */
    @ApiModelProperty(value = "资产状态  6-处置中 8-报废 9-遗失 10-变卖")
    private List<String> notInAssertsStateList;

}
