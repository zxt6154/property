package com.ziroom.suzaku.main.client.wechat.handler;

import com.ziroom.suzaku.main.client.wechat.param.WechatBaseReqParam;
import com.ziroom.tech.http.HttpReq;
import com.ziroom.tech.http.HttpReqHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xuzeyu
 */
@Component
@Slf4j
public class WechatReqHandler implements HttpReqHandler<WechatBaseReqParam> {

    @Value("${wechat.token}")
    private String token;

    @Override
    public void handle(HttpReq<WechatBaseReqParam> req) {
        req.getBodyParam().setToken(token);
    }
}
