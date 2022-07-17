package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuBrandEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 资产品牌
 * @author xuzeyu
 */
public interface SuzakuBrandService {

    void addBrandInfo(List<SuzakuBrandEntity> suzakuBrandEntities);

    List<SuzakuBrandEntity> getBrandsByCat(String category);
}
