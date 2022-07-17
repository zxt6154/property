package com.ziroom.suzaku.main.model.dto.req.ehrs;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("获取部门列表请求体")
public class EhrOrgListReq {

    @ApiModelProperty("部门层级, 0集团, 1虚拟层, 11 城市业务战区, 2 城市下战区／职能中心, 3 业务大区／职能部门, 4 组默认为所有层级")
    private Integer orgLevel;

    @ApiModelProperty("页数 默认为第一页")
    private Integer page = 1;

    @ApiModelProperty("每页记录数，默认为10条，最大不超过100")
    private Integer size = 10;
}
