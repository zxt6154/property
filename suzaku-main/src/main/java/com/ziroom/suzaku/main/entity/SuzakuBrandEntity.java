package com.ziroom.suzaku.main.entity;

import lombok.Data;

/**
 * 资产品牌
 * @author xuzeyu
 */
@Data
public class SuzakuBrandEntity{

    /**
     * 主键
     */
    private Long id;

    /**
     * 品牌编码
     */
    private String brandCode;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 来源
     */
    private String source;

    /**
     * 三级分类
     */
    private String catId;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String moditiyTime;


}
