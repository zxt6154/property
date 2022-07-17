package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuCompanyMapping;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.dto.req.CompanyMappingReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-10-19
 */
public interface SuzakuCompanyMappingMapper extends BaseMapper<SuzakuCompanyMapping> {

    List<SuzakuCompanyMapping> fuzzySearch(@Param("companyMappingReq") CompanyMappingReq companyMappingReq);

}
