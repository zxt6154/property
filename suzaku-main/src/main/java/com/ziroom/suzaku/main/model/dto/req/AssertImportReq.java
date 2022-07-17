package com.ziroom.suzaku.main.model.dto.req;

import com.ziroom.suzaku.main.param.PageCommonParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @zhangxt3
 * 资产入库查询查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssertImportReq extends PageCommonParam {

    //入库状态，枚举
    private Integer importState;

    private String brand;

    //入库单号
    private String importBillNum;

    //供应商
    private Integer supplier;

    //供应商
    private Integer managerType;

}
