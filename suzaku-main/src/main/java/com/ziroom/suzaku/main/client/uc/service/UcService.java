package com.ziroom.suzaku.main.client.uc.service;

import com.ziroom.suzaku.main.client.uc.model.UcTokenRespModel;
import com.ziroom.suzaku.main.client.uc.model.UcUserInfoRespModel;

import java.util.List;

/**
 * @author xuzeyu
 */
public interface UcService {

    UcTokenRespModel getTokenModel();

    List<UcUserInfoRespModel> getUserInfoList();

}
