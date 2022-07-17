package com.ziroom.suzaku.main.model.qo;

import com.ziroom.suzaku.main.param.PageCommonParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssertsSkuQo extends PageCommonParam {

        @ApiModelProperty(value = "资产条码")
        private String assertsCode;

        @ApiModelProperty(value = "资产条码")
        private List<String> assertsCodes;

        @ApiModelProperty(value = "入库单code")
        private List<String> importBillNums;

        @ApiModelProperty(value = "管理类型")
        private Integer manageType;

        @ApiModelProperty(value = "资产名称")
        private String skuName;

        @ApiModelProperty(value = "入库仓库")
        private String remandDepot;

        @ApiModelProperty(value = "部门")
        private List<String> departNames;

        @ApiModelProperty(value = "分类id")
        private String categoryId;

        @ApiModelProperty(value = "stationId")
        private String stationId;

        /**
         * 资产状态
         */
        private List<Integer> assertStatus;

        /**
         * 资产状态
         */
        private Integer assertStatu;
        /**
         * 资产id
         */
        private List<String> skuIds;

}
