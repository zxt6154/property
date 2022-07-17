package com.ziroom.suzaku.main.model.qo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import java.util.List;

/**
 * @author libingsi
 * @date 2021/10/25 14:54
 */
@Data
public class ImportAssertQo {

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "资产状态，资产状态 1闲置 2代签收 3领用 4借用 5退库中 6处置中 7转移中 8报废 9遗失 10变卖")
    private Integer assertsState;

    @ApiModelProperty(value = "使用人")
    private String usePeople;

    @ApiModelProperty(value = "使用人归属部门")
    private String useDepartment;

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "管理类型")
    private Integer type;

    @ApiModelProperty(value = "资产状态，资产状态 1闲置 2代签收 3领用 4借用 5退库中 6处置中 7转移中 8报废 9遗失 1变卖")
    private List<String> assertsStateList;

    @ApiModelProperty(value = "入库单Ids")
    private List<String> importBills;
}
