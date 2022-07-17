package com.ziroom.suzaku.main.model.dto.resp;

import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowRecipientExcelResp {

    @ApiModelProperty("资产条码(必填)")
    @ExcelProperty("资产条码")
    private String assertsCode;

    @ApiModelProperty("管理类型(必填)")
    @ExcelProperty("管理类型")
    private String manageType;

    @ApiModelProperty("门店/工作站ID(选填)")
    @ExcelProperty("门店/工作站ID")
    private String station;

    @ApiModelProperty("领用/借用人(必填)")
    @ExcelProperty("领用/借用人")
    private String borrowerRecipientUid;

    @ApiModelProperty("领借，必填")
    @ExcelProperty("领借")
    private String borrowerOrRecipient;

    @ApiModelProperty("领借日期(必填,日期格式必须正确)")
    @ExcelProperty("领借日期")
    private String date;

    @ApiModelProperty("日期(必填,日期格式必须正确)")
    @ExcelProperty("日期")
    private String returnDate;

    @ApiModelProperty("领借原因，必填")
    @ExcelProperty("领借原因")
    private String reason;

    @ApiModelProperty("错误信息")
    @ExcelProperty("错误信息")
    private String failInfo;

    @ApiModelProperty("管理类型id")
    @ExcelIgnore
    private Integer manageTypeId;

}
