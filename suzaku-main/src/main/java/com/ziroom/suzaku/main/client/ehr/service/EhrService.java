package com.ziroom.suzaku.main.client.ehr.service;

import com.ziroom.suzaku.main.client.ehr.model.EhrUserInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.param.EhrUserInfoReqParam;

import java.util.List;

/**
 * @author xuzeyu
 */
public interface EhrService {

    Integer getTotalCount();

    List<EhrUserInfoRespModel> getUserSimple(EhrUserInfoReqParam ehrUserInfoReqParam);

    String getUserInfo(String userEmail);

}
