package com.ziroom.suzaku.main.service.impl;

import com.ziroom.suzaku.main.dao.SuzakuBrandMapper;
import com.ziroom.suzaku.main.entity.SuzakuBrandEntity;
import com.ziroom.suzaku.main.service.SuzakuBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资产品牌
 * @author xuzeyu
 */
@Service
public class SuzakuBrandServiceImpl implements SuzakuBrandService {

    @Autowired
    private SuzakuBrandMapper suzakuBrandMapper;


    @Override
    public void addBrandInfo(List<SuzakuBrandEntity> suzakuBrandEntities) {
        suzakuBrandMapper.insert(suzakuBrandEntities);
    }

    @Override
    public List<SuzakuBrandEntity> getBrandsByCat(String category) {
        return suzakuBrandMapper.getBrandsByCat(category);
    }
}
