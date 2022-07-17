package com.ziroom.suzaku.main.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 用户信息
 * @author xuzeyu
 */
@Data
public class SuzakuUserInfoModel {

    /**
     * 名字
     */
    private String userName;

    /**
     * 邮箱
     */
    private String email;

}
