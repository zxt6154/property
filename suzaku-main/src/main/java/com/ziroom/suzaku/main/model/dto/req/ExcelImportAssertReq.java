package com.ziroom.suzaku.main.model.dto.req;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ExcelImportAssertReq {

    @ApiModelProperty(value = "资产条码")
    private String assertsCode;

    @ApiModelProperty(value = "旧资产条码")
    private String oldAssertsCode;

    @ApiModelProperty(value = "SKU")
    private String sku;

    @ApiModelProperty(value = "SN码")
    private String snCode;

    @ApiModelProperty(value = "MAC地址/IMEI")
    private String macImic;

    @ApiModelProperty(value = "购置单价")
    private BigDecimal purchasePrice;

    private String purchaseDate;

    private String maintainOverdue;

    @ApiModelProperty(value = "是否上传成功，0 - 失败，1 - 成功    默认0")
    private String successFlag;


}
