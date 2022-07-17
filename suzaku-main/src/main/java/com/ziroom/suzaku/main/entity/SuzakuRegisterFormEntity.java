package com.ziroom.suzaku.main.entity;

import lombok.Data;

/**
 * 资产领借登记单实体
 * @author xuzeyu
 */
@Data
public class SuzakuRegisterFormEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 登记单号
     */
    private String orderId;

    /**
     * 领借类型 1领用 2借用
     */
    private Integer orderType;

    /**
     * 领用单状态 1待确认 2已取消 3已确认 4已归还
     */
    private Integer status;

    /**
     * 领借原因
     */
    private String orderDesc;

    /**
     * 预计归还日期
     */
    private String returnDate;

    /**
     * 领借人
     */
    private String userName;

    /**
     * 领借人工号
     */
    private String userNo;

    /**
     * 使用部门(领借人所属部门)
     */
    private String deparment;

    /**
     * 操作人
     */
    private String operateName;

    /**
     * 管理类型
     */
    private Integer managerType;

    /**
     * 门店/工作站编码
     */
    private String stationNo;

    /**
     * 门店/工作站名称
     */
    private String stationName;

    /**
     * 创建时间
     */
    private String createName;

    /**
     * 更新时间
     */
    private String moditiyTime;


}
