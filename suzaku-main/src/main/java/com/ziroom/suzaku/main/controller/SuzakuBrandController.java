package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.config.BrandCacheService;
import com.ziroom.suzaku.main.entity.SuzakuBrandEntity;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.service.SuzakuBrandService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资产品牌
 * @author xuzeyu
 */
@RestController
@RequestMapping("/brand")
public class SuzakuBrandController {

    @Autowired
    private SuzakuBrandService suzakuBrandService;

    @Autowired
    private BrandCacheService brandCacheService;

    /**
     * 品牌入库
     */
    @RequestMapping(value = "/addBrandInfo", method = RequestMethod.POST)
    public ModelResponse<String> addBrandInfo(@RequestBody List<SuzakuBrandEntity> suzakuBrandEntities) {
        suzakuBrandService.addBrandInfo(suzakuBrandEntities);
        return ModelResponseUtil.ok("");
    }

    /**
     * 根据3级分类 查询品牌列表
     */
    @RequestMapping(value = "/getBrandsByCat", method = RequestMethod.GET)
    public ModelResponse<List<String>> getBrandsByCat(String category) {
        List<SuzakuBrandEntity> brands = brandCacheService.getBrandInfoList();
        return ModelResponseUtil.ok(brands.stream().map(SuzakuBrandEntity::getBrandName).collect(Collectors.toList()));
    }

    /**
     * 根据3级分类 查询品牌列表
     */
    @RequestMapping(value = "/getBrands", method = RequestMethod.GET)
    public JsonResult<List<SuzakuBrandEntity>> getBrandsByCat() {
        List<SuzakuBrandEntity> brands = suzakuBrandService.getBrandsByCat(null);
        return JsonResult.ok(brands);
    }

}
