package com.ziroom.suzaku.main.message.converter;

import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTaskCardModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTextCardModel;
import com.ziroom.suzaku.main.channel.wechat.model.WechatAppMessageTextModel;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTaskCardModel;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextCardModel;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextModel;
import org.apache.commons.collections.CollectionUtils;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * msg modle 转换器
 * @author xuzeyu
 */
public class MessageModelConverter {

    /**
     * text model converter
     * @return
     */
    public static Function<WechatMsgAppTextModel, WechatAppMessageTextModel> wechatTextModelConverter() {
        return wechatMsgAppTextModel -> {
            WechatAppMessageTextModel wechatAppMessageTextModel = new WechatAppMessageTextModel();
            wechatAppMessageTextModel.setData(wechatMsgAppTextModel.getData());
            wechatAppMessageTextModel.setToUser(wechatMsgAppTextModel.getToUser());
            wechatAppMessageTextModel.setModelCode(wechatMsgAppTextModel.getModelCode());
            return wechatAppMessageTextModel;
        };
    }

    /**
     * textCard model converter
     * @return
     */
    public static Function<WechatMsgAppTextCardModel, WechatAppMessageTextCardModel> wechatTextCardModelConverter() {
        return wechatMsgAppTextCardModel -> {
            WechatAppMessageTextCardModel wechatAppMessageTextCardModel = new WechatAppMessageTextCardModel();
            wechatAppMessageTextCardModel.setToUser(wechatMsgAppTextCardModel.getToUser());
            wechatAppMessageTextCardModel.setModelCode(wechatMsgAppTextCardModel.getModelCode());

            WechatAppMessageTextCardModel.CardModel cardModel = new WechatAppMessageTextCardModel.CardModel();
            cardModel.setTitle(wechatMsgAppTextCardModel.getTitle());
            cardModel.setDescription(wechatMsgAppTextCardModel.getDescription());
            cardModel.setUrl(wechatMsgAppTextCardModel.getUrl());
            cardModel.setBtntxt(wechatMsgAppTextCardModel.getBtntxt());
            wechatAppMessageTextCardModel.setCardModel(cardModel);
            return wechatAppMessageTextCardModel;
        };
    }

    /**
     * taskCard model converter
     * @return
     */
    public static Function<WechatMsgAppTaskCardModel, WechatAppMessageTaskCardModel> wechatTaskCardModelConverter() {
        return wechatMsgAppTaskCardModel -> {
            WechatAppMessageTaskCardModel wechatAppMessageTaskCardModel = new WechatAppMessageTaskCardModel();
            wechatAppMessageTaskCardModel.setToUser(wechatMsgAppTaskCardModel.getToUser());
            wechatAppMessageTaskCardModel.setModelCode(wechatMsgAppTaskCardModel.getModelCode());

            WechatAppMessageTaskCardModel.TaskCardModel taskCardModel = new WechatAppMessageTaskCardModel.TaskCardModel();
            taskCardModel.setTitle(wechatMsgAppTaskCardModel.getTitle());
            taskCardModel.setDescription(wechatMsgAppTaskCardModel.getDescription());
            taskCardModel.setUrl(wechatMsgAppTaskCardModel.getUrl());
            taskCardModel.setTaskId(wechatMsgAppTaskCardModel.getTaskId());

            if(CollectionUtils.isNotEmpty(wechatMsgAppTaskCardModel.getButtonList())){
                List<WechatAppMessageTaskCardModel.TaskCardModel.Btn> btns = wechatMsgAppTaskCardModel.getButtonList().stream().map(m -> {
                    WechatAppMessageTaskCardModel.TaskCardModel.Btn btn = new WechatAppMessageTaskCardModel.TaskCardModel.Btn();
                    btn.setKey(m.getKey());
                    btn.setName(m.getName());
                    btn.setReplace_name(m.getReplace_name());
                    btn.setCallback_url(m.getCallback_url());
                    btn.setCallback_type(m.getCallback_type());
                    btn.setCallback_rm(m.getCallback_rm());
                    btn.setCallback_params(m.getCallback_params());
                    return btn;
                }).collect(Collectors.toList());
                taskCardModel.setBtn(btns);
            }
            return wechatAppMessageTaskCardModel;
        };
    }

}
