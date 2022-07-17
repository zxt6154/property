package com.ziroom.suzaku.main.model.dto.resp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api("用户详情")
public class UserDetailResp {

    private String userCode;

    private String userName;

    @ApiModelProperty("职务 P:主职")
    private String job;

    @ApiModelProperty("部门")
    private String dept;

    @ApiModelProperty("部门code")
    private String deptCode;

    @ApiModelProperty("组")
    private String group;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;
}
