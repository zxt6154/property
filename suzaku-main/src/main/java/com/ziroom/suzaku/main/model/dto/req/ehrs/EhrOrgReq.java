package com.ziroom.suzaku.main.model.dto.req.ehrs;

import com.google.common.base.Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel("使用部门编码查询部门信息的请求体")
public class EhrOrgReq {

    @ApiModelProperty(value = "部门编码", required = true)
    private String deptId;

    @ApiModelProperty(value = "公司编码")
    private String setId;

    public void validate() {
        Preconditions.checkArgument(StringUtils.isNotBlank(deptId), "部门编码不能为空");
    }
}
