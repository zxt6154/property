package com.ziroom.suzaku.main.service.impl;

import com.ziroom.suzaku.main.entity.SuzakuCompanyMapping;
import com.ziroom.suzaku.main.dao.SuzakuCompanyMappingMapper;
import com.ziroom.suzaku.main.model.dto.req.CompanyMappingReq;
import com.ziroom.suzaku.main.service.SuzakuCompanyMappingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-19
 */
@Service
public class SuzakuCompanyMappingServiceImpl extends ServiceImpl<SuzakuCompanyMappingMapper, SuzakuCompanyMapping> implements SuzakuCompanyMappingService {

    @Autowired
    private SuzakuCompanyMappingMapper mappingMapper;

    @Override
    public List<SuzakuCompanyMapping> fuzzySearch(CompanyMappingReq companyMappingReq) {
        List<SuzakuCompanyMapping> suzakuCompanyMappings = mappingMapper.fuzzySearch(companyMappingReq);
        return suzakuCompanyMappings;
    }
}
