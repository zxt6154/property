package com.ziroom.suzaku.main.entity.excelModel;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExcelImportAssert {

    @ApiModelProperty(value = "资产条码")
    @ExcelProperty("资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "旧资产条码")
    @ExcelProperty("旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "SKU")
    @ExcelProperty("SKU")
    private String sku;

    @ApiModelProperty(value = "SN码")
    @ExcelProperty("SN码")
    private String snCode;

    @ApiModelProperty(value = "MAC地址/IMEI")
    @ExcelProperty("MAC地址/IMEI")
    private String macImic;

    @ApiModelProperty(value = "购置单价")
    @ExcelProperty("购置单价")
    private BigDecimal purchasePrice;

    @ExcelProperty("购置日期")
    private String purchaseDate;

    @ExcelProperty("维保日期")
    private String maintainOverdue;

    @ApiModelProperty(value = "是否上传成功，0 - 失败，1 - 成功    默认0")
    @ExcelProperty("失败原因")
    private String successFlag;

}
