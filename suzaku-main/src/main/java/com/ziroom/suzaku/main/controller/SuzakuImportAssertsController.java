package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuImportAssertsDto;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.model.qo.ImportAssertQo;
import com.ziroom.suzaku.main.model.vo.SuzakuImportAssertsVo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  资产列表前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */
@Api(value = "资产列表接口", tags = "资产列表接口")
@RestController
@RequestMapping("/api/v1/asserts")
public class SuzakuImportAssertsController {


    @Autowired
    private SuzakuImportAssertsService assertsService;

    @Autowired
    private BeanMapper beanMapper;

    @PostMapping(value = "/pageAsserts")
    @ApiOperation(value = "获取资产列表分页信息", notes = "获取资产列表分页信息")
    public JsonResult<PageData<SuzakuImportAssertsVo>> getAssertsList(@RequestBody @ApiParam(value = "资产信息") AssertQo qo){
        PageData<SuzakuImportAssertsDto> pageData = assertsService.getAssertsList(qo);
        return JsonResult.ok(beanMapper.mapPageData(pageData,SuzakuImportAssertsVo.class));
    }


    @PostMapping(value = "/getList")
    @ApiOperation(value = "查询资产列表", notes = "查询资产列表(入库单审核通过的)")
    public JsonResult<List<SuzakuImportAssertsVo>> assertList(@RequestBody @ApiParam(value = "资产信息") AssertQo qo){
        List<SuzakuImportAssertsDto> dtos = assertsService.assertList(qo);
        List<SuzakuImportAssertsVo> vos = beanMapper.mapList(dtos,SuzakuImportAssertsVo.class);
        return JsonResult.ok(vos);
    }


}
