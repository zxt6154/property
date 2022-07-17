package com.ziroom.suzaku.main.client.wechat.handler;

import com.ziroom.suzaku.main.client.wechat.model.WechatBaseRespModel;
import com.ziroom.tech.exception.HttpException;
import com.ziroom.tech.http.HttpRespHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xuzeyu
 */
@Slf4j
@Component
public class WechatErrorRespHandler implements HttpRespHandler<WechatBaseRespModel<?>> {

    @Override
    public void handle(WechatBaseRespModel<?> respModel) {
        if(Objects.isNull(respModel)) {
            throw new HttpException("-1", "wechat interface result is null");
        }
    }
}
