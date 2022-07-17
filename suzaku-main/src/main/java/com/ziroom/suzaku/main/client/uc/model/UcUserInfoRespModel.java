package com.ziroom.suzaku.main.client.uc.model;

import lombok.Data;

/**
 * @author xuzeyu
 */
@Data
public class UcUserInfoRespModel {

    /**
     * 名字
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门
     */
    private String department;
}
