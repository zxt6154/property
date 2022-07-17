package com.ziroom.suzaku.main.entity;

import lombok.Data;

/**
 * 管理类型实体
 * @author xuzeyu
 */
@Data
public class SuzakuManageType {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String moditiyTime;


    /**
     * 类型编码
     */
    private String typeCode;
}
