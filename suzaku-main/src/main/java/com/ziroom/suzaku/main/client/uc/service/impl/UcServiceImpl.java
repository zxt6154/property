package com.ziroom.suzaku.main.client.uc.service.impl;

import com.ziroom.suzaku.main.client.uc.client.UcApiClient;
import com.ziroom.suzaku.main.client.uc.model.UcBaseRespModel;
import com.ziroom.suzaku.main.client.uc.model.UcTokenRespModel;
import com.ziroom.suzaku.main.client.uc.model.UcUserInfoRespModel;
import com.ziroom.suzaku.main.client.uc.param.UcTokenReqParam;
import com.ziroom.suzaku.main.client.uc.param.UcUserInfoReqParam;
import com.ziroom.suzaku.main.client.uc.service.UcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author xuzeyu
 */
@Service
@Slf4j
public class UcServiceImpl implements UcService {

    @Autowired
    private UcApiClient ucApiClient;


    /**
     * 获取token信息
     */
    public UcTokenRespModel getTokenModel() {
        UcTokenReqParam ucTokenReqParam = new UcTokenReqParam();
        UcBaseRespModel<UcTokenRespModel> ucTokenRespModel = ucApiClient.getToken(ucTokenReqParam);
        if(Objects.nonNull(ucTokenRespModel)){
            return ucTokenRespModel.getData();
        }
        return null;
    }

    @Override
    public List<UcUserInfoRespModel> getUserInfoList() {
        UcUserInfoReqParam ucUserInfoReqParam = new UcUserInfoReqParam();
        UcBaseRespModel<List<UcUserInfoRespModel>> respModel = ucApiClient.getUserSimpleList(ucUserInfoReqParam);
        if(Objects.nonNull(respModel)){
            return respModel.getData();
        }
        return null;
    }
}
