package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.entity.SuzakuCompanyMapping;
import com.ziroom.suzaku.main.entity.SuzakuSupplierEntity;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.CompanyMappingReq;
import com.ziroom.suzaku.main.service.SuzakuSupplierService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 资产供应商
 * @author xuzeyu
 */
@RestController
@RequestMapping("/supplier")
public class SuzakuSupplierController {

    @Autowired
    private SuzakuSupplierService suzakuSupplierService;

    /**
     * 供应商入库
     */
    @RequestMapping(value = "/addSupplierInfo", method = RequestMethod.POST)
    public ModelResponse<String> addSupplierInfo(@RequestBody List<SuzakuSupplierEntity> suzakuSupplierEntities) {
        suzakuSupplierService.addSupplierInfo(suzakuSupplierEntities);
        return ModelResponseUtil.ok("");
    }

    /**
     * 根据3级分类 查询供应商列表
     */
    @RequestMapping(value = "/getSuppliersByCat", method = RequestMethod.GET)
    public ModelResponse<List<String>> getBrandsByCat(@RequestParam String category) {
        List<SuzakuSupplierEntity> suppliers = suzakuSupplierService.getSuppliersByCat(category);
        return ModelResponseUtil.ok(suppliers.stream().map(SuzakuSupplierEntity::getSupplierName).distinct().collect(Collectors.toList()));
    }

    @RequestMapping(value = "/getSuppliers", method = RequestMethod.GET)
    public ModelResponse<List<SuzakuSupplierEntity>> getSuppliers() {
        List<SuzakuSupplierEntity> suppliers = suzakuSupplierService.getSuppliersByCat(null);
        return ModelResponseUtil.ok(suppliers);
    }

    @PostMapping("/fuzzySearch")
    public ModelResponse<List<SuzakuSupplierEntity>> fuzzySearch(@RequestParam String supplierName){
        List<SuzakuSupplierEntity> supplier = suzakuSupplierService.fuzzySearch(supplierName);
        return ModelResponseUtil.ok(supplier);
    }

}
