package com.ziroom.suzaku.main.channel.wechat.enums;

/**
 * 企业微信应用消息类型枚举
 * @author xuzeyu
 */
public enum WechatAppMsgTypeEnum {

    APP_MSG_TEXT("text", "文本消息"),
    APP_MSG_TEXTCARD("textcard", "文本卡片消息"),
    APP_MSG_MARKDOWN("markdown","markdown"),
    APP_MSG_TASKCARD("taskcard","任务卡片消息"),
    APP_MSG_IMAGE("image","图片消息"),
    ;
    private final String type;
    private final String desc;

    WechatAppMsgTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static WechatAppMsgTypeEnum getMsgType(String type) {
        for (WechatAppMsgTypeEnum e : values()) {
            if(e.getType().equals(type)){
                return e;
            }
        }
        return null;
    }
}
