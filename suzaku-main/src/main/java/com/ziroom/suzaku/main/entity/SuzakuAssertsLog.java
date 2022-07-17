package com.ziroom.suzaku.main.entity;

import java.time.LocalDateTime;
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
 * @since 2021-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuAssertsLog对象", description="")
public class SuzakuAssertsLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "资产名称")
    private String assertsName;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作人ip")
    private String operatorIp;

    @ApiModelProperty(value = "操作原因")
    private String remark;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime updateTime;


}
