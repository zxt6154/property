package com.ziroom.suzaku.main.channel.wechat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业微信应用消息基类
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class WechatAppMessageModel {

    /**
     * 应用消息类型
     */
    private String type;

    /**
     * data
     */
    private String data;

}
