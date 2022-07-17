package com.ziroom.suzaku.main.entity;

import lombok.Data;

/**
 * 资产供应商表
 * @author xuzeyu
 */
@Data
public class SuzakuSupplierEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 来源
     */
    private String source;

    /**
     * 三级分类Id
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
