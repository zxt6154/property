package com.ziroom.suzaku.main.entity.excelModel;

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
public class AssertImportReq {

    //入库状态，枚举
    private String importState;

    private String brand;

    //入库单号
    private String importBillNum;

    //供应商
    private String supplier;

    //供应商
    private String managerType;

}
