package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuCompanyMapping;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.req.CompanyMappingReq;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-19
 */
public interface SuzakuCompanyMappingService extends IService<SuzakuCompanyMapping> {

    List<SuzakuCompanyMapping> fuzzySearch(CompanyMappingReq companyMappingReq);


}
