package com.ziroom.suzaku.main.model.dto.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("EHR部门")
public class EhrDeptResp implements Serializable {

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty(value = "部门编码", notes = "这里的部门编码是新的部门编码")
    private String code;

    @ApiModelProperty(value = "深度路径", notes = "部门树路径")
    private String treepath;

    public static EhrDeptResp build(String code) {
        EhrDeptResp ehrDeptResp = new EhrDeptResp();
        ehrDeptResp.setCode(code);
        return ehrDeptResp;
    }
}
