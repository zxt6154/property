package com.ziroom.suzaku.main.client.uc.client;

import com.ziroom.suzaku.main.client.uc.handler.UcErrorRespHandler;
import com.ziroom.suzaku.main.client.uc.model.UcBaseRespModel;
import com.ziroom.suzaku.main.client.uc.model.UcTokenRespModel;
import com.ziroom.suzaku.main.client.uc.model.UcUserInfoRespModel;
import com.ziroom.suzaku.main.client.uc.param.UcTokenReqParam;
import com.ziroom.suzaku.main.client.uc.param.UcUserInfoReqParam;
import com.ziroom.tech.aop.retry.ExceptionRetry;
import com.ziroom.tech.exception.HttpException;
import com.ziroom.tech.http.HttpBodyParam;
import com.ziroom.tech.http.HttpClient;
import com.ziroom.tech.http.HttpInvoke;
import org.springframework.http.HttpMethod;

import java.util.List;

/**
 * uc接口对接列表
 * @author xuzeyu
 */
@HttpClient(path = "${api.client.uc.url}")
public interface UcApiClient {

    /**
     * 获取token
     *
     * @return
     */
    @ExceptionRetry(supportRetryExceptions = {HttpException.class}, retryTimes = 1)
    @HttpInvoke(path = "/oauth/token", method = HttpMethod.GET, respHandlers= UcErrorRespHandler.class)
    UcBaseRespModel<UcTokenRespModel> getToken(@HttpBodyParam UcTokenReqParam ucTokenReqParam);

    /**
     * 获取uc用户
     *
     * @return
     */
    @ExceptionRetry(supportRetryExceptions = {HttpException.class}, retryTimes = 1)
    @HttpInvoke(path = "/users/info/v1", method = HttpMethod.GET, respHandlers= UcErrorRespHandler.class)
    UcBaseRespModel<List<UcUserInfoRespModel>> getUserSimpleList(@HttpBodyParam UcUserInfoReqParam ucUserInfoReqParam);

}
