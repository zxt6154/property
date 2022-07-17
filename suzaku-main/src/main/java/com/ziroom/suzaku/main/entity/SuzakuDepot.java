package com.ziroom.suzaku.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 仓库表
 * </p>
 *
 * @author libingsi
 * @since 2021-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SuzakuDepot对象", description="仓库表")
public class SuzakuDepot implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 仓库编码
     */
    private String depotCode;

    @ApiModelProperty(value = "仓库名称")
    private String depotName;

    /**
     * 地址
     */
    private String address;

    /**
     * 负责人工号
     */
    private String managerNo;

    /**
     * 负责人姓名
     */
    private String managerName;

    @ApiModelProperty(value = "创建日期")
    private String createTime;

    @ApiModelProperty(value = "更新日期")
    private String updateTime;


}
