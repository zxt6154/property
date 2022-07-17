package com.ziroom.suzaku.main.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author libingsi
 * @since 2021-10-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="资产退还信息", description="")
public class SuzakuRemandVo implements Serializable {

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "退库单号")
    private String remandId;

    @ApiModelProperty(value = "退库状态(1.待审核，2.审核通过，3.审核驳回)")
    private String remandState;

    @ApiModelProperty(value = "退还审核人")
    private String remandAdmin;

    @ApiModelProperty(value = "操作人")
    private String operateName;

    @ApiModelProperty(value = "退还类型")
    private String remandType;

    @ApiModelProperty(value = "退还备注")
    private String remandRemark;

    @ApiModelProperty(value = "退还日期")
    private String remandDate;

    @ApiModelProperty(value = "退还仓库")
    private String remandDepot;

    @ApiModelProperty(value = "退还人")
    private String usePeople;

    @ApiModelProperty(value = "门店编码")
    private String remandStoreCode;

    @ApiModelProperty(value = "门店名称")
    private String remandStoreName;

    @ApiModelProperty(value = "资产条码")
    private String assertCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertCode;

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "创建日期")
    private String createTime;

    @ApiModelProperty(value = "修改日期")
    private String updateTime;


}
