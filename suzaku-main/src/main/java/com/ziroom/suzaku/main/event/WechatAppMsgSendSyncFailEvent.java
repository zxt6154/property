package com.ziroom.suzaku.main.event;

import com.ziroom.tech.queue.BaseDelayed;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 企业微信消息发送异常 处理事件
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class WechatAppMsgSendSyncFailEvent extends BaseDelayed {

    public WechatAppMsgSendSyncFailEvent(Integer type, Object data) {
        super(new Date(new Date().getTime() + 1000*60*2), type, data);
    }
}
