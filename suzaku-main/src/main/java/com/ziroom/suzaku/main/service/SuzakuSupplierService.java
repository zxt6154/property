package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuSupplierEntity;
import java.util.List;

/**
 * 资产供应商
 * @author xuzeyu
 */
public interface SuzakuSupplierService {

    void addSupplierInfo(List<SuzakuSupplierEntity> suzakuSupplierEntities);

    List<SuzakuSupplierEntity> getSuppliersByCat(String category);

    List<SuzakuSupplierEntity> fuzzySearch(String supplierName);

}
