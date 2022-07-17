package com.ziroom.suzaku.main.model.qo;

import com.ziroom.suzaku.main.param.PageCommonParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @author libingsi
 * @date 2021/10/25 14:54
 */
@Data
@NoArgsConstructor
public class AssertQo extends PageCommonParam {

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "资产状态，资产状态 1闲置 2代签收 3领用 4借用 5退库中 6处置中 7转移中 8报废 9遗失 10变卖")
    private Integer assertsState;

    @ApiModelProperty(value = "使用人")
    private String userName;

    @ApiModelProperty(value = "使用人归属部门")
    private String useDepartment;

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "管理类型")
    private Integer managerType;

    @ApiModelProperty(value = "资产状态，资产状态 1闲置 2代签收 3领用 4借用 5退库中 6处置中 7转移中 8报废 9遗失 1变卖")
    private List<String> assertsStateList;

    private List<String> importBills;

    private List<String> assertsCodes;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "规格组(扩展信息)")
    private Map<String,String> attributes;

    @ApiModelProperty(value = "规格组")
    private String attributeStr;
}
