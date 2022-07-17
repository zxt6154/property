package com.ziroom.suzaku.main.client.ehr.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ziroom.suzaku.main.client.ehr.client.EhrApiClient;
import com.ziroom.suzaku.main.client.ehr.model.EhrBaseRespModel;
import com.ziroom.suzaku.main.client.ehr.model.EhrDepartmentInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.model.EhrUserInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.model.EhrUserSimpleInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.param.EhrDepartmentInfoReqParam;
import com.ziroom.suzaku.main.client.ehr.param.EhrUserInfoReqParam;
import com.ziroom.suzaku.main.client.ehr.param.EhrUserSimpleInfoReqParam;
import com.ziroom.suzaku.main.client.ehr.service.EhrService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author xuzeyu
 */
@Service
@Slf4j
public class EhrServiceImpl implements EhrService {

    @Autowired
    private EhrApiClient ehrApiClient;

    @Override
    public Integer getTotalCount() {
        EhrUserInfoReqParam ehrUserInfoReqParam = new EhrUserInfoReqParam();
        ehrUserInfoReqParam.setPage(1);
        ehrUserInfoReqParam.setSize(1);

        EhrBaseRespModel<List<EhrUserInfoRespModel>> respModel = ehrApiClient.getUserSimpleList(ehrUserInfoReqParam);
        if(Objects.nonNull(respModel)){
            return respModel.getTotleCount();
        }
        return null;
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<EhrUserInfoRespModel> getUserSimple(EhrUserInfoReqParam ehrUserInfoReqParam) {
        EhrBaseRespModel<List<EhrUserInfoRespModel>> respModel = ehrApiClient.getUserSimpleList(ehrUserInfoReqParam);
        if(Objects.nonNull(respModel)){
            return respModel.getData();
        }
        return null;
    }

    /**
     * 查询中心部门
     */
    @Override
    public String getUserInfo(String userEmail) {
        EhrUserSimpleInfoReqParam ehrUserInfoReqParam = new EhrUserSimpleInfoReqParam();
        ehrUserInfoReqParam.setUserEmail(userEmail);
        EhrBaseRespModel<List<EhrUserSimpleInfoRespModel>> respModel = ehrApiClient.getUserSimpleInfo(ehrUserInfoReqParam);
        if(Objects.nonNull(respModel)){
            String departmentCode = "";
            List<EhrUserSimpleInfoRespModel> dataList = respModel.getData();
            if(CollectionUtils.isNotEmpty(dataList)){
                String treePath = dataList.get(dataList.size() - 1).getTreePath();
                List<String> treeList = Arrays.asList(treePath.split(","));
                if (CollectionUtil.isEmpty(treeList)){
                    return null;
                }
                if (treeList.size() ==  1 ){
                    departmentCode  = treeList.get(0);
                }else if (treeList.size() == 2){
                    departmentCode  = treeList.get(1);
                }else if ((treeList.size() == 3)){
                    departmentCode  = treeList.get(2);
                }else {
                    departmentCode  = treeList.get(3);
                }
                EhrDepartmentInfoReqParam ehrDepartmentInfoReqParam = new EhrDepartmentInfoReqParam();
                ehrDepartmentInfoReqParam.setCode(departmentCode);

                EhrBaseRespModel<EhrDepartmentInfoRespModel> respModel1 = ehrApiClient.getOrgByCode(ehrDepartmentInfoReqParam);
                if(Objects.nonNull(respModel1)){
                    return respModel1.getData().getDescrShort();
                } else if(Objects.nonNull(dataList.get(dataList.size() - 1).getDept())){
                    return dataList.get(dataList.size() - 1).getDept();
                }
            }
        }
        return null;
    }

}
