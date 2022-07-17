package com.ziroom.suzaku.main.client.ehr.model;

import lombok.Data;

/**
 * @author xuzeyu
 */
@Data
public class EhrUserInfoRespModel {

    /**
     * 名字
     */
    private String fullName;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门
     */
    private String department;
}
