package com.ziroom.suzaku.main.model.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 盘点任务
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
@Data
@ApiModel(value="SuzakuCheck对象", description="盘点任务")
public class SuzakuCheckPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "盘点id")
    private String checkId;

    @ApiModelProperty(value = "盘点名称")
    private String checkName;

    @ApiModelProperty(value = "资产分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "管理类型")
    private Integer manageType;

    @ApiModelProperty(value = "盘点状态")
    private Integer checkStatus;

    @ApiModelProperty(value = "部门范围")
    private String department;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "盘点类型")
    private Integer checkType;

    @ApiModelProperty(value = "盘前资产数量")
    private Integer beforeQuantity;

    @ApiModelProperty(value = "已盘资产数量")
    private Integer alreadyQuantity;

    @ApiModelProperty(value = "盘后资产数量")
    private Integer afterQuantity;

    @ApiModelProperty(value = "门店编码")
    private String storeCode;

    @ApiModelProperty(value = "门店名称")
    private String storeName;

    @ApiModelProperty(value = "入库仓库")
    private String checkDepot;


    @ApiModelProperty(value = "发起人")
    private String createUser;

    @ApiModelProperty(value = "更新人")
    private String updateUser;

    @ApiModelProperty(value = "盘点单创建日期")
    private String createTime;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

}
