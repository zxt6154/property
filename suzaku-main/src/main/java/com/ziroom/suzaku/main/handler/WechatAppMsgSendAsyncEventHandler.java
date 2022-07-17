package com.ziroom.suzaku.main.handler;

import com.ziroom.suzaku.main.client.wechat.client.WechatApiClient;
import com.ziroom.suzaku.main.client.wechat.param.AppMessageReqParam;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.tech.queue.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 企微app消息异常处理类
 *
 * @author xuzeyu
 */
@Component
@Slf4j
public class WechatAppMsgSendAsyncEventHandler implements EventHandler {

    @Autowired
    private WechatApiClient wechatApiClient;

    @Override
    public Integer support() {
        return DelayJobEnum.ASYNC_WECHATMSG_APP_EVENT.getType();
    }

    @Override
    public void handleMessage(Object data) {
        log.info("WechatAppMsgSendAsyncEventHandler start");
        AppMessageReqParam messageReqParam = (AppMessageReqParam) data;
        wechatApiClient.sendAppMessage(messageReqParam);
        log.info("WechatAppMsgSendAsyncEventHandler end.");
    }
}
