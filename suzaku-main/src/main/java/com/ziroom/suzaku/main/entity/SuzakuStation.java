package com.ziroom.suzaku.main.entity;

import lombok.Data;

/**
 * 门店/工作站记录实体
 * @author xuzeyu
 */
@Data
public class SuzakuStation {


    /**
     * 主键
     */
    private Long id;

    /**
     * 管理类型 1.IT 2.门店 3.工作站 4.行政
     */
    private Integer type;

    /**
     * 城市
     */
    private String city;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 编码
     */
    private String stationNo;

    /**
     * 名称
     */
    private String stationName;

    /**
     * 地址
     */
    private String address;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String moditiyTime;


}
