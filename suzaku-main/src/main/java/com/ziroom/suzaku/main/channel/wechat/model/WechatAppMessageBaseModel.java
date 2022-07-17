package com.ziroom.suzaku.main.channel.wechat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 企业微信应用消息-base
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class WechatAppMessageBaseModel {

    /**
     * 消息接收人
     */
    private List<String> toUser;

    /**
     * 模板号
     */
    private String modelCode;


}
