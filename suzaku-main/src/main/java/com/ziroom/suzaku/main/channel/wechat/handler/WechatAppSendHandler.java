package com.ziroom.suzaku.main.channel.wechat.handler;

import com.alibaba.fastjson.JSON;
import com.ziroom.suzaku.main.channel.wechat.enums.WechatAppMsgTypeEnum;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTaskCardModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTextCardModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTextModel;
import com.ziroom.suzaku.main.client.wechat.client.WechatApiClient;
import com.ziroom.suzaku.main.client.wechat.model.WechatBaseRespModel;
import com.ziroom.suzaku.main.client.wechat.param.AppMessageReqParam;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.suzaku.main.enums.MessageChannelEnum;
import com.ziroom.suzaku.main.enums.SuzakuServiceExceptionEnum;
import com.ziroom.suzaku.main.event.WechatAppMsgSendSyncFailEvent;
import com.ziroom.suzaku.main.exception.SuzakuBussinesException;
import com.ziroom.suzaku.main.message.router.MessageSendHandler;
import com.ziroom.suzaku.main.utils.BeanUtil;
import com.ziroom.tech.queue.DelayQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * 企业微信应用消息发送
 * @author xuzeyu
 */
@Slf4j
@Component
public class WechatAppSendHandler implements MessageSendHandler {

    @Autowired
    private WechatApiClient wechatApiClient;

    @Override
    public Integer support() {
        return MessageChannelEnum.WECHAT_APP_CHANNEL.getType();
    }

    @Override
    public void sendMessage(String data) {
        WechatAppMessageModel appMessageModel = JSON.parseObject(data, WechatAppMessageModel.class);
        String type = appMessageModel.getType();
        WechatAppMsgTypeEnum msgTypeEnum = WechatAppMsgTypeEnum.getMsgType(type);
        AppMessageReqParam appMessageReqParam = new AppMessageReqParam();

        switch (msgTypeEnum){
            case APP_MSG_TEXT:
                WechatAppMessageTextModel textModel = JSON.parseObject(appMessageModel.getData(), WechatAppMessageTextModel.class);
                appMessageReqParam.setMsgType(WechatAppMsgTypeEnum.APP_MSG_TEXT.getType());
                if(CollectionUtils.isEmpty(textModel.getToUser())){
                    throw new SuzakuBussinesException(SuzakuServiceExceptionEnum.REQUEST_CHECKPARAM_CODE.getCode(),"消息接收人必填");
                }
                appMessageReqParam.setToUser(String.join("|", textModel.getToUser()));
                appMessageReqParam.setParams(textModel.getData());
                appMessageReqParam.setModelCode(textModel.getModelCode());
                break;
            case APP_MSG_TEXTCARD:
                WechatAppMessageTextCardModel textCardModel = JSON.parseObject(appMessageModel.getData(), WechatAppMessageTextCardModel.class);
                appMessageReqParam.setMsgType(WechatAppMsgTypeEnum.APP_MSG_TEXTCARD.getType());
                if(CollectionUtils.isEmpty(textCardModel.getToUser())){
                    throw new SuzakuBussinesException(SuzakuServiceExceptionEnum.REQUEST_CHECKPARAM_CODE.getCode(),"消息接收人必填");
                }
                appMessageReqParam.setToUser(String.join("|", textCardModel.getToUser()));
                appMessageReqParam.setParams(BeanUtil.beanToMap(textCardModel.getCardModel()));
                appMessageReqParam.setModelCode(textCardModel.getModelCode());
                break;
            case APP_MSG_TASKCARD:
                WechatAppMessageTaskCardModel taskCardModel = JSON.parseObject(appMessageModel.getData(), WechatAppMessageTaskCardModel.class);
                appMessageReqParam.setMsgType(WechatAppMsgTypeEnum.APP_MSG_TASKCARD.getType());
                if(CollectionUtils.isEmpty(taskCardModel.getToUser())){
                    throw new SuzakuBussinesException(SuzakuServiceExceptionEnum.REQUEST_CHECKPARAM_CODE.getCode(),"消息接收人必填");
                }
                appMessageReqParam.setToUser(String.join("|", taskCardModel.getToUser()));
                WechatAppMessageTaskCardModel.TaskCardModel taskCardModel1 = taskCardModel.getTaskCardModel();
                appMessageReqParam.setParams(BeanUtil.beanToMap(taskCardModel1));
                appMessageReqParam.setModelCode(taskCardModel.getModelCode());
                break;
            case APP_MSG_MARKDOWN:
                break;
            case APP_MSG_IMAGE:
                break;
            default:
                break;

        }

        WechatBaseRespModel respModel = wechatApiClient.sendAppMessage(appMessageReqParam);
        //timeout
        if(Objects.isNull(respModel)){
            //补偿机制
            log.info("[WechatAppSendHandler] WechatAppMsgSendSyncFailEvent insurance, param is {}", JSON.toJSONString(appMessageReqParam));
            DelayQueueUtil.getInstance().put(new WechatAppMsgSendSyncFailEvent(DelayJobEnum.ASYNC_WECHATMSG_APP_EVENT.getType(), appMessageReqParam));
        }
        //业务异常
        if (!"0".equals(respModel.getCode())) {
            log.error("[WechatAppSendHandler] wechatApiClient.sendAppMessage fail, message is {}", JSON.toJSONString(respModel));
        }

    }
}
