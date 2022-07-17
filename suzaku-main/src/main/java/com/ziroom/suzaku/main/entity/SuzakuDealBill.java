package com.ziroom.suzaku.main.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author zhangxt3
 * @since 2021-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="SuzakuDealBill对象", description="处置单")
public class SuzakuDealBill implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "处置单号")
    private String dealNum;

    @ApiModelProperty(value = "处置状态")
    private Integer dealState;

    @ApiModelProperty(value = "处置类型")
    private Integer dealType;

    @ApiModelProperty(value = "资产原值合计")
    private BigDecimal sumPrice;

    @ApiModelProperty(value = "处置数量")
    private Integer dealCount;

    @ApiModelProperty(value = "处置费用")
    private BigDecimal dealCost;

    @ApiModelProperty(value = "变卖金额")
    private BigDecimal sellOff;

    @ApiModelProperty(value = "管理类型")
    private Integer manageType;

    @ApiModelProperty(value = "门店/工作站id")
    //@TableField("stationId")
    private Integer stationId;

    @ApiModelProperty(value = "处置说明")
    private String dealNote;

    @ApiModelProperty(value = "建单人")
    private String creator;
    private String creatorDept;
    @ApiModelProperty(value = "建单日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "操作人")
    private String operator;
    private String operatorDept;
    @ApiModelProperty(value = "操作日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime operatorTime;

    @ApiModelProperty(value = "审核人")
    private String checkPeople;
    private String checkDept;
    @ApiModelProperty(value = "审核日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkTime;

}
