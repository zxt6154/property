package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.dto.SuzakuSkuDto;
import com.ziroom.suzaku.main.param.SkuCreateReqParam;
import com.ziroom.suzaku.main.param.SkuSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuSkuService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 资产sku基础数据
 * @author xuzeyu
 */
@RestController
@RequestMapping("/sku")
public class SuzakuSkuController {

    @Autowired
    private SuzakuSkuService suzakuSkuService;

    /**
     * 新建资产
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelResponse<String> create(@RequestBody SkuCreateReqParam skuCreateReqParam) {
        suzakuSkuService.create(skuCreateReqParam);
        return ModelResponseUtil.ok("success");
    }

    /**
     * 根据skuId查询资产
     */
    @RequestMapping(value = "/getBySkuId", method = RequestMethod.GET)
    public ModelResponse<SuzakuSkuDto> getBySkuId(@RequestParam String skuId) {
        SuzakuSkuDto skuDto = suzakuSkuService.getSuzakuSkuDtoBySkuId(skuId);
        return ModelResponseUtil.ok(skuDto);
    }

    /**
     * 编辑资产
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelResponse<String> update(@RequestBody SkuCreateReqParam skuUpdateReqParam){
        suzakuSkuService.update2(skuUpdateReqParam);
        return ModelResponseUtil.ok("success");
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/pageSkus", method = RequestMethod.POST)
    public ModelResponse<PageData<SuzakuSkuEntity>> pageSkus(@RequestBody SkuSelectReqParam skuSelectReqParam){
        PageData<SuzakuSkuEntity> pageData = suzakuSkuService.pageSkus(skuSelectReqParam);
        return ModelResponseUtil.ok(pageData);
    }

    /**
     * 分页查询
     */
    @RequestMapping(value = "/getSkusAll", method = RequestMethod.GET)
    public ModelResponse<List<SuzakuSkuEntity>> getSkusAll(@RequestParam String skuName){
        return ModelResponseUtil.ok(suzakuSkuService.searchSku(skuName));
    }

}
