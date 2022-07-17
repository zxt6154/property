package com.ziroom.suzaku.main.message.model.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 企微App消息model
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public abstract class WechatMsgBaseModel {
    /**
     * 消息接收人
     */
    private List<String> toUser;

    /**
     * 模板号
     */
    private String modelCode;
}
