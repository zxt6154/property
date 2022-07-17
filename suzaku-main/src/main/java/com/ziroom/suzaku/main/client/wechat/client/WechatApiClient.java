package com.ziroom.suzaku.main.client.wechat.client;

import com.ziroom.suzaku.main.client.wechat.handler.WechatErrorRespHandler;
import com.ziroom.suzaku.main.client.wechat.handler.WechatReqHandler;
import com.ziroom.suzaku.main.client.wechat.model.WechatBaseRespModel;
import com.ziroom.suzaku.main.client.wechat.param.AppMessageReqParam;
import com.ziroom.tech.aop.retry.ExceptionRetry;
import com.ziroom.tech.exception.HttpException;
import com.ziroom.tech.http.HttpBodyParam;
import com.ziroom.tech.http.HttpClient;
import com.ziroom.tech.http.HttpInvoke;
import org.springframework.http.HttpMethod;

/**
 * 企业微信接口对接列表
 * @author xuzeyu
 */
@HttpClient(path = "${api.client.wechat.url}")
public interface WechatApiClient {

    /**
     * 发送应用消息
     *
     * @return
     */
    @ExceptionRetry(supportRetryExceptions = {HttpException.class}, retryTimes = 2)
    @HttpInvoke(path = "/api/work/wechat/send", method = HttpMethod.POST, reqHandlers = WechatReqHandler.class,respHandlers= WechatErrorRespHandler.class)
    WechatBaseRespModel sendAppMessage(@HttpBodyParam AppMessageReqParam appMessageReqParam);

}
