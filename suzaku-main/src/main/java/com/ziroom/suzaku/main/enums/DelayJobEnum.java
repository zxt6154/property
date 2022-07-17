package com.ziroom.suzaku.main.enums;

/**
 * @author xuzeyu
 */
public enum DelayJobEnum {

    SYNC_EHR_FAIL_EVENT(1, "ehr用户信息同步事件"),
    SYNC_BRAND_FAIL_EVENT(2, "品牌信息同步事件"),
    ASYNC_APPLYFORM_EVENT(3, "资产状态变更记录事件"),
    ASYNC_WECHATMSG_APP_EVENT(4, "企微app消息发送异常事件"),
    ;
    private final Integer type;
    private final String desc;

    DelayJobEnum(Integer type, String desc) {
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
