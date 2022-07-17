package com.ziroom.suzaku.main.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author libingsi
 * @since 2021-10-19
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuCompanyMapping对象", description="")
public class SuzakuCompanyMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "公司代码")
    private String companyCode;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "所在城市")
    private String companyCity;

    @ApiModelProperty(value = "城市编码")
    private String cityCode;


}
