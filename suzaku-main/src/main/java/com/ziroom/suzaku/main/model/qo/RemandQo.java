package com.ziroom.suzaku.main.model.qo;

import com.ziroom.suzaku.main.param.PageCommonParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author libingsi
 * @date 2021/10/25 14:54
 */
@Getter
@Setter
public class RemandQo extends PageCommonParam {

    @ApiModelProperty(value = "退库单号")
    private String remandId;

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "退库状态(1.待审核，2.审核通过，3.审核驳回)")
    private String remandState;

    @ApiModelProperty(value = "资产名称")
    private String skuName;
}
