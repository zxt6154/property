package com.ziroom.suzaku.main.message.event;

import com.ziroom.suzaku.main.enums.MessageChannelEnum;
import com.ziroom.suzaku.main.message.router.MessageChannelEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业微信app应用消息事件
 * @author xuzeyu
 */
@Data
public class WechatAppMessageChannelEvent extends MessageChannelEvent {

    public WechatAppMessageChannelEvent() {
        super(MessageChannelEnum.WECHAT_APP_CHANNEL.getType());
    }
}
