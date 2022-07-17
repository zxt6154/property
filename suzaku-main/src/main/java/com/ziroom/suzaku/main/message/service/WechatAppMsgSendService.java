package com.ziroom.suzaku.main.message.service;

import com.alibaba.fastjson.JSON;
import com.ziroom.suzaku.main.channel.wechat.enums.WechatAppMsgTypeEnum;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTaskCardModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTextCardModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTextModel;
import com.ziroom.suzaku.main.message.converter.MessageModelConverter;
import com.ziroom.suzaku.main.message.event.WechatAppMessageChannelEvent;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTaskCardModel;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextCardModel;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * 企业微信app应用消息服务
 * @author xuzeyu
 */
@Slf4j
@Service
@AllArgsConstructor
public class WechatAppMsgSendService {

    private final ApplicationEventPublisher eventPublisher;


    /**
     * text类型消息发送
     */
    public void textMsgSend(WechatMsgAppTextModel textModel){
        log.info("[WechatAppMsgSendService] textMsgSend param is {}", JSON.toJSONString(textModel));
        WechatAppMessageModel wechatAppMessageModel = new WechatAppMessageModel();
        wechatAppMessageModel.setType(WechatAppMsgTypeEnum.APP_MSG_TEXT.getType());

        //参数封装
        WechatAppMessageTextModel wechatAppMessageTextModel = MessageModelConverter.wechatTextModelConverter().apply(textModel);
        wechatAppMessageModel.setData(JSON.toJSONString(wechatAppMessageTextModel));

        //事件发送
        WechatAppMessageChannelEvent messageChannelEvent = new WechatAppMessageChannelEvent();
        messageChannelEvent.setData(JSON.toJSONString(wechatAppMessageModel));
        eventPublisher.publishEvent(messageChannelEvent);
    }

    /**
     * textCard类型消息发送
     */
    public void textCardMsgSend(WechatMsgAppTextCardModel wechatMsgAppTextCardModel){
        log.info("[WechatAppMsgSendService] textCardMsgSend param is {}", JSON.toJSONString(wechatMsgAppTextCardModel));

        WechatAppMessageModel wechatAppMessageModel = new WechatAppMessageModel();
        wechatAppMessageModel.setType(WechatAppMsgTypeEnum.APP_MSG_TEXTCARD.getType());

        //参数封装
        WechatAppMessageTextCardModel wechatAppMessageTextCardModel = MessageModelConverter.wechatTextCardModelConverter().apply(wechatMsgAppTextCardModel);
        wechatAppMessageModel.setData(JSON.toJSONString(wechatAppMessageTextCardModel));

        //事件发送
        WechatAppMessageChannelEvent messageChannelEvent = new WechatAppMessageChannelEvent();
        messageChannelEvent.setData(JSON.toJSONString(wechatAppMessageModel));
        eventPublisher.publishEvent(messageChannelEvent);
    }

    /**
     * taskCard类型消息发送
     */
    public void taskCardMsgSend(WechatMsgAppTaskCardModel wechatMsgAppTaskCardModel){
        log.info("[WechatAppMsgSendService] taskCardMsgSend param is {}", JSON.toJSONString(wechatMsgAppTaskCardModel));

        WechatAppMessageModel wechatAppMessageModel = new WechatAppMessageModel();
        wechatAppMessageModel.setType(WechatAppMsgTypeEnum.APP_MSG_TASKCARD.getType());

        //参数封装
        WechatAppMessageTaskCardModel wechatAppMessageTaskCardModel = MessageModelConverter.wechatTaskCardModelConverter().apply(wechatMsgAppTaskCardModel);
        wechatAppMessageModel.setData(JSON.toJSONString(wechatAppMessageTaskCardModel));

        //事件发送
        WechatAppMessageChannelEvent messageChannelEvent = new WechatAppMessageChannelEvent();
        messageChannelEvent.setData(JSON.toJSONString(wechatAppMessageModel));
        eventPublisher.publishEvent(messageChannelEvent);
    }
}
