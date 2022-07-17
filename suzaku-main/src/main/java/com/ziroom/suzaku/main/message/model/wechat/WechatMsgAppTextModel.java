package com.ziroom.suzaku.main.message.model.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * 企微App应用消息text类型model
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class WechatMsgAppTextModel extends WechatMsgBaseModel{
    /**
     * 消息内容
     */
    HashMap<String,String> data;
}
