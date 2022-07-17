package com.ziroom.suzaku.main.enums;

/**
 * 消息渠道枚举
 * @author xuzeyu
 */
public enum MessageChannelEnum {

    WECHAT_APP_CHANNEL(1, "企业微信应用消息"),
    DINGDING_CHANNEL(2, "钉钉"),
    ;
    private final Integer type;
    private final String desc;

    MessageChannelEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }


}
