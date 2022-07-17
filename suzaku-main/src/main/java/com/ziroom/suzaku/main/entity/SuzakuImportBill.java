package com.ziroom.suzaku.main.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author libingsi
 * @since 2021-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuImportBill对象", description="")
public class SuzakuImportBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty(value = "审批状态: 0-待审核，1-审核成功， 2-审核驳回")
    private Integer applyState;

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

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifieDate;

}
