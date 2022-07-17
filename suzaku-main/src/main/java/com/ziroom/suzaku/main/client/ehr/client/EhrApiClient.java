package com.ziroom.suzaku.main.client.ehr.client;

import com.ziroom.suzaku.main.client.ehr.handler.EhrErrorRespHandler;
import com.ziroom.suzaku.main.client.ehr.model.EhrBaseRespModel;
import com.ziroom.suzaku.main.client.ehr.model.EhrDepartmentInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.model.EhrUserInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.model.EhrUserSimpleInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.param.EhrDepartmentInfoReqParam;
import com.ziroom.suzaku.main.client.ehr.param.EhrUserInfoReqParam;
import com.ziroom.suzaku.main.client.ehr.param.EhrUserSimpleInfoReqParam;
import com.ziroom.tech.aop.retry.ExceptionRetry;
import com.ziroom.tech.exception.HttpException;
import com.ziroom.tech.http.HttpBodyParam;
import com.ziroom.tech.http.HttpClient;
import com.ziroom.tech.http.HttpInvoke;
import org.springframework.http.HttpMethod;
import java.util.List;

/**
 * ehr接口对接列表
 * @author xuzeyu
 */
@HttpClient(path = "${api.client.ehr.url}")
public interface EhrApiClient {

    /**
     * 获取用户列表
     *
     * @return
     */
    @ExceptionRetry(supportRetryExceptions = {HttpException.class}, retryTimes = 1)
    @HttpInvoke(path = "/api/ehr/getEmpList", method = HttpMethod.GET, respHandlers= EhrErrorRespHandler.class)
    EhrBaseRespModel<List<EhrUserInfoRespModel>> getUserSimpleList(@HttpBodyParam EhrUserInfoReqParam ehrUserInfoReqParam);

    /**
     * 获取用户信息
     */
    @ExceptionRetry(supportRetryExceptions = {HttpException.class}, retryTimes = 1)
    @HttpInvoke(path = "/api/ehr/getUserSimple.action", method = HttpMethod.GET, respHandlers= EhrErrorRespHandler.class)
    EhrBaseRespModel<List<EhrUserSimpleInfoRespModel>> getUserSimpleInfo(@HttpBodyParam EhrUserSimpleInfoReqParam ehrUserInfoReqParam);

    /**
     * 获取部门信息
     */
    @ExceptionRetry(supportRetryExceptions = {HttpException.class}, retryTimes = 1)
    @HttpInvoke(path = "/api/ehr/getOrgByCode.action", method = HttpMethod.GET, respHandlers= EhrErrorRespHandler.class)
    EhrBaseRespModel<EhrDepartmentInfoRespModel> getOrgByCode(@HttpBodyParam EhrDepartmentInfoReqParam ehrDepartmentInfoReqParam);

}
