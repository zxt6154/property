package com.ziroom.suzaku.main.channel.wechat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 企业微信应用消息-textcard
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class WechatAppMessageTextCardModel extends WechatAppMessageBaseModel {

    /**
     * 消息体
     */
    private CardModel cardModel;

    @Data
    @NoArgsConstructor
    public static class CardModel{
        /**
         * 标题
         */
        private String title;

        /**
         * 文本卡片的描述
         */
        private String description;

        /**
         * 点击后跳转的链接，请确保包含了协议头(http/https)
         */
        private String url;

        /**
         * 按钮文本，这个可以传递空值，剩余三个均不能为空
         */
        private String btntxt;
    }

}
