package com.ziroom.suzaku.main.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ziroom.suzaku.main.model.vo.CheckItemNukeVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="SuzakuCheckItem对象", description="")
public class CheckSkuDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "盘点父单号")
    private String checkId;

    @ApiModelProperty(value = "资产条码")
    private String assertCode;

    @ApiModelProperty(value = "skuiD")
    private String skuId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "资产名称")
    private String skuName;

    @ApiModelProperty(value = "员工盘点状态")
    private String staffStatus;

    @ApiModelProperty(value = "管理员盘点状态")
    private String adminStatus;

    @ApiModelProperty(value = "盘点人")
    private String usePeople;

    @ApiModelProperty(value = "盘点部门")
    private String checkDepartment;

    @ApiModelProperty(value = "异常类型")
    private String errorType;

    @ApiModelProperty(value = "异常说明")
    private String errorInfo;

    @ApiModelProperty(value = "上传图片地址")
    private String picUrl;

    @ApiModelProperty(value = "上传条码")
    private String uploadCode;

    @ApiModelProperty(value = "扫码结果")
    private String scanResult;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "SN码")
    private String snCode;

    @ApiModelProperty(value = "MAC地址/IMEI")
    private String macImic;

    @ApiModelProperty(value = "购置日期")
    private String purchaseDate;

    @ApiModelProperty(value = "维保到期日期")
    private String maintainOverdue;

    @ApiModelProperty(value = "购置单价")
    private BigDecimal purchasePrice;



}
