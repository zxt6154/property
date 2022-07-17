package com.ziroom.suzaku.main.model.qo;

import com.ziroom.suzaku.main.param.PageCommonParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class HisQo extends PageCommonParam {

    @ApiModelProperty(value = "关联处置单")
    private String dealNum;

    @ApiModelProperty(value = "关联资产")
    private String assertCode;

    @ApiModelProperty(value = "sku")
    private String skuId;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "状态")
    private Integer assertsState;

    @ApiModelProperty(value = "分类ID")
    private String categoryId;
}
