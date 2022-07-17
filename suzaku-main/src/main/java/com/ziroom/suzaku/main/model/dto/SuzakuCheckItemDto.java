package com.ziroom.suzaku.main.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuCheckItem对象", description="")
public class SuzakuCheckItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "盘点父单号")
    private String checkId;

    @ApiModelProperty(value = "资产条码")
    private String assertCode;

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

    @ApiModelProperty(value = "上传条码")
    private String uploadCode;

    @ApiModelProperty(value = "扫码结果")
    private String scanResult;

    @ApiModelProperty(value = "上传图片地址")
    private String picUrl;

    @ApiModelProperty(value = "创建人")
    private String createUser;

    @ApiModelProperty(value = "修改人")
    private String updateUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;




}
