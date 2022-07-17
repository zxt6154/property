package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuCheckDto;
import com.ziroom.suzaku.main.model.qo.CheckQo;
import com.ziroom.suzaku.main.model.vo.SuzakuCheckVo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.LaunchCheckReqParam;
import com.ziroom.suzaku.main.service.SuzakuCheckService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 盘点任务 前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
@RestController
@Api(value = "盘点任务接口", tags = "盘点任务接口")
@RequestMapping("/api/v1/check")
public class SuzakuCheckController {

    @Autowired
    private SuzakuCheckService suzakuCheckService;

    @Autowired
    private BeanMapper beanMapper;

    @PostMapping(value = "/pageCheck")
    @ApiOperation(value = "获取资产盘点计划信息", notes = "获取资产盘点计划信息")
    public JsonResult<PageData<SuzakuCheckVo>> pageCheck(@RequestBody @ApiParam(value = "资产盘点信息") CheckQo qo){
        PageData<SuzakuCheckDto> pageData = suzakuCheckService.pageCheck(qo);
        return JsonResult.ok(beanMapper.mapPageData(pageData,SuzakuCheckVo.class));
    }

    /**
     * 发起资产盘点计划
     * @param launchCheckReqParam
     * @return
     */
    @PostMapping("/launchCheck")
    @ApiOperation(value = "发起资产盘点计划", notes = "发起资产盘点计划")
    public JsonResult launchCheck(@ApiParam(value = "发起资产盘点计划请求参数") @RequestBody LaunchCheckReqParam launchCheckReqParam) {
        suzakuCheckService.launchCheck(launchCheckReqParam);
        return JsonResult.ok();
    }

    /**
     * 查询盘点资产
     * @param assertSelectReqParam
     * @return
     */
    @ApiOperation(value = "查询盘点资产", notes = "查询盘点资产")
    @RequestMapping(value = "/pageGetAssert", method = RequestMethod.POST)
    public JsonResult<PageData<SuzakuAssertDto>> pageGetAssert(@ApiParam(value = "查询盘点资产请求参数") @RequestBody AssertSelectReqParam assertSelectReqParam){
        PageData<SuzakuAssertDto> pageData = suzakuCheckService.pageGetAssert(assertSelectReqParam);
        return JsonResult.ok(pageData);
    }

}
