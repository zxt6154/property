package com.ziroom.suzaku.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author libingsi
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuNoticeLog对象", description="")
public class SuzakuNoticeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "员工名称")
    @TableField("userName")
    private String username;

    @ApiModelProperty(value = "资产名称")
    @TableField("assertName")
    private String assertname;

    @ApiModelProperty(value = "品牌名称")
    @TableField("brandName")
    private String brandname;

    @ApiModelProperty(value = "条码")
    @TableField("assertCode")
    private String assertcode;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "目标信息")
    private String userInfo;

    @ApiModelProperty(value = "发送类型")
    private String type;

    @ApiModelProperty(value = "发送人")
    private String createUser;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
