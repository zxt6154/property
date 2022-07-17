package com.ziroom.suzaku.main.message.model.wechat;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * 企微App应用消息taskcard类型model
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class WechatMsgAppTaskCardModel extends WechatMsgBaseModel {
    /**
     * 标题
     */
    private String title;

    /**
     * 任务卡片的描述
     */
    private String description;

    /**
     * url为点击后跳转的链接, 可以不传,默认设置会跳转到http://www.ziroom.com。如果传url参数, 就会跳转传入的url。
     */
    private String url;

    /**
     * taskId可以不传，消息平台默认会生产一个taskId
     */
    private String taskId;

    /**
     * 按钮文本，这个可以传递空值，剩余三个均不能为空
     */
    private List<Button> buttonList;

    @Data
    @NoArgsConstructor
    public static class Button{
        /**
         * key是用于回调处理的唯一标识，调用方可以使用一个标识，消息平台会做唯一性的处理
         */
        private String key;

        /**
         * 按钮的文案
         */
        private String name;

        /**
         * 按钮点击后的文案
         */
        private String replace_name;

        /**
         * 用于收到企业微信回调后，消息平台将回调的对应的调用方系统，完成整个流程
         */
        private String callback_url;

        /**
         * 回调类型，目前有三类origin(不做任何处理),append(在url后面拼接固定参数),replace(替换占位符中的固定参数)
         */
        private String callback_type;

        /**
         * 指定回调的请求方式，目前支持GET、POST、PUT、PATCH、DELETE
         */
        private String callback_rm;

        /**
         * 放入body的参数，只有能够支持body的请求方式该参数才起作用， callback_params类型为Map<String, Object>
         */
        private String callback_params;

    }

}
