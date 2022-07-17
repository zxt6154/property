package com.ziroom.suzaku.main.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.param.PageCommonParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

//入库单req
@Data
public class ImportBillReq extends PageCommonParam {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Integer Id;

    @ApiModelProperty(value = "入库单号")
    private String importBillNum;

    @ApiModelProperty(value = "入库状态")
    private Integer importState;

    @ApiModelProperty(value = "入库方式")
    private Integer importWay;

    @ApiModelProperty(value = "管理类型")
    private Integer managerType;

    @ApiModelProperty(value = "供应商")
    private String supplier;

    @ApiModelProperty(value = "OA审批单号")
    private String oaApplyNum;

    @ApiModelProperty(value = "入库数量")
    private Long importCount;

    @ApiModelProperty(value = "入库金额")
    private BigDecimal importSum;

    @ApiModelProperty(value = "入库仓库")
    private String repository;

    @ApiModelProperty(value = "入库时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime importDate;

    @ApiModelProperty(value = "入库说明")
    private String importNote;

    @ApiModelProperty(value = "入库公司")
    private String importCompany;

    @ApiModelProperty(value = "创建部门")
    private String createDept;

    @ApiModelProperty(value = "创建人")
    private String creator;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "修改人")
    private String modifietor;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifieDate;

    //apply_state
    @ApiModelProperty(value = "审批状态")
    private Integer applyState;

    private Integer stationId;
}
