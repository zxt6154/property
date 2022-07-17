package com.ziroom.suzaku.main.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 申请单Dto
 * @author xuzeyu
 */
@Data
public class SuzakuApplyFormDto {

    /**
     * 登记单号
     */
    private String orderId;

    /**
     * 领借类型 1领用 2借用
     */
    private String orderType;

    /**
     * 领用单状态 1待确认 2已取消 3已确认 4已归还
     */
    private String status;

    /**
     * 领借原因
     */
    private String desc;

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
     * 管理类型描述
     */
    private String manageDesc;

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

    /**
     * 资产名称
     */
    private String skuName;

    /**
     * 资产条码
     */
    private String assertsCode;

    /**
     * 旧资产条码
     */
    private String oldAssertsCode;
}
