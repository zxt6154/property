package com.ziroom.suzaku.main.model.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ziroom.suzaku.main.param.PageCommonParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @zhangxt3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealBillsReq extends PageCommonParam {

    //处置单号s
    private List<String> dealNums;

    //处置原因
    private String dealNum;

    //处置原因
    private String dealNote;

    //建单人
    private String creator;

    //处置类型
    private Integer dealType;

    //管理类型
    private Integer manageType;

    //管理类型
    private String categoryId;

    //处置时间
    @ApiModelProperty(value = "维保到期日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}