package com.ziroom.suzaku.main.client.wechat.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xuzeyu
 */
@Data
public class WechatBaseRespModel<T> implements Serializable {
    private String code;
    private String message;
    private String response_code;
    private String error_info;
    private String message_info;

}
