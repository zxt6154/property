package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuBrandEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产品牌
 * @author xuzeyu
 */
@Mapper
public interface SuzakuBrandMapper {

    void insert(@Param("brandList")List<SuzakuBrandEntity> suzakuBrandEntities);

    List<SuzakuBrandEntity> getBrandsByCat(String category);
}
