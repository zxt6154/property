package com.ziroom.suzaku.main.service.impl;

import com.ziroom.suzaku.main.dao.SuzakuSupplierMapper;
import com.ziroom.suzaku.main.entity.SuzakuSupplierEntity;
import com.ziroom.suzaku.main.service.SuzakuSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产供应商
 * @author xuzeyu
 */
@Service
public class SuzakuSupplierServiceImpl implements SuzakuSupplierService {

    @Autowired
    SuzakuSupplierMapper suzakuSupplierMapper;


    @Override
    public List<SuzakuSupplierEntity> fuzzySearch(String supplierName) {
        return suzakuSupplierMapper.fuzzySearch(supplierName);
    }

    @Override
    public void addSupplierInfo(List<SuzakuSupplierEntity> suzakuSupplierEntities) {
        suzakuSupplierMapper.insert(suzakuSupplierEntities);
    }

    @Override
    public List<SuzakuSupplierEntity> getSuppliersByCat(String category) {
        return suzakuSupplierMapper.getSuppliersByCat(category);
    }

}
