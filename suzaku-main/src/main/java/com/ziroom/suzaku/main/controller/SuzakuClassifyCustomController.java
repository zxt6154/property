package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.converter.WebConverter;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.model.vo.SuzakuClassifyCustomVo;
import com.ziroom.suzaku.main.service.SuzakuClassifyCustomService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 资产分类扩展属性表 前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Api(value = "资产分类扩展属性接口", tags = "资产分类扩展属性接口")
@RestController
@RequestMapping("/api/v1/custom")
public class SuzakuClassifyCustomController {

    @Autowired
    SuzakuClassifyCustomService suzakuClassifyCustomService;


    /**
     * 根据三级分类查询扩展信息
     */
    @RequestMapping(value = "/getAttributesByCat", method = RequestMethod.GET)
    public ModelResponse<List<SuzakuClassifyCustomVo>> getBrandsByCat(@RequestParam(name = "category") String category) {
        List<SuzakuClassifyCustomDto> attributes = suzakuClassifyCustomService.getClassifyCustomByClassifyId(category);
        List<SuzakuClassifyCustomVo> suzakuClassifyCustomVos = attributes.stream().map(WebConverter.suzakuClassifyCustomDto2ClassifyCustomVO()).collect(Collectors.toList());
        return ModelResponseUtil.ok(suzakuClassifyCustomVos);
    }
    @RequestMapping(value = "/getAttributesById", method = RequestMethod.GET)
    public ModelResponse<SuzakuClassifyCustomDto> getBrandsById(@RequestParam(name = "id") String id) {
        SuzakuClassifyCustomDto attribute = suzakuClassifyCustomService.getClassifyCustomById(id);
       // SuzakuClassifyCustomVo suzakuClassifyCustomVos = WebConverter.suzakuClassifyCustomDto2ClassifyCustomVO();
        return ModelResponseUtil.ok(attribute);
    }

}
