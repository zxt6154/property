package com.ziroom.suzaku.main.model.dto.req.ehrs;

import com.google.common.base.Preconditions;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class UserDetailReq {

    private String userCode;

    public void validate() {
        Preconditions.checkArgument(StringUtils.isNotBlank(userCode), "用户code不能为空");
    }
}
