package com.ziroom.suzaku.main.channel.wechat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 企业微信应用消息-text
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class WechatAppMessageTextModel extends WechatAppMessageBaseModel {

    /**
     * 消息体
     */
    private Map<String, String> data;

}
