package com.ziroom.suzaku.main.entity;

import lombok.Data;

/**
 * 资产分类扩展属性
 * @author xuzeyu
 */
@Data
public class SuzakuClassifyCustomValueEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 扩展属性id
     */
    private String customId;

    /**
     * 扩展属性值
     */
    private String customValue;

    /**
     * sku标记
     */
    private String skus;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String moditiyTime;


}
