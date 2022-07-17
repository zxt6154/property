package com.ziroom.suzaku.main.client.wechat.param;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class AppMessageReqParam extends WechatBaseReqParam implements Serializable {

    /**
     * 推送类型，目前支持四种。发送应用消息(app)，发送消息到群聊会话(group_chat)，发送企业互联消息(link_corp)，发送群机器人消息(group_chat_robot)
     */
    private String pushType = "app";

    /**
     * 消息类型，文本消息(text)，文本卡片消息(textcard)，markdown消息(markdown)，任务卡片消息(taskcard)，图片消息(image)
     */
    private String msgType;

    /**
     * 接受消息的成员，多个接收者以'|'分隔，最多支持1000个, 支持邮箱前缀和系统号，请不要混传即toUser内的数据应同为邮箱前缀或系统号，使用系统号时请将transferToUser字段置为true
     */
    private String toUser;

    /**
     * 注意 此参数属性 由 messageType 决定 具体请参考文档 http://docs-tech.t.ziroom.com/message/%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8/%E4%BC%81%E4%B8%9A%E5%BE%AE%E4%BF%A1%E5%BF%AB%E9%80%9F%E5%85%A5%E9%97%A8.html
     * 传递模板中参数替换的内容。
     */
    private Map<String,String> params;

    /**
     * 模板号
     */
    protected String modelCode;
}
