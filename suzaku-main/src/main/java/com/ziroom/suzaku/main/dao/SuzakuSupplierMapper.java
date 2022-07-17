package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuSupplierEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产供应商
 * @author xuzeyu
 */
@Mapper
public interface SuzakuSupplierMapper {

    void insert(@Param("supplierList")List<SuzakuSupplierEntity> suzakuSupplierEntities);

    List<SuzakuSupplierEntity> getSuppliersByCat(String category);

    List<SuzakuSupplierEntity> fuzzySearch(String supplierName);

}
