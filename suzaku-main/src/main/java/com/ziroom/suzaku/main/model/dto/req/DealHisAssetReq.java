package com.ziroom.suzaku.main.model.dto.req;

import com.ziroom.suzaku.main.param.PageCommonParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealHisAssetReq extends PageCommonParam {

    private String dealNum;

}
