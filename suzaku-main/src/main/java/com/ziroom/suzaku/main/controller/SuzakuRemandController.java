package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuRemandDto;
import com.ziroom.suzaku.main.model.qo.RemandQo;
import com.ziroom.suzaku.main.model.vo.SuzakuRemandVo;
import com.ziroom.suzaku.main.param.ApplyRemandReqParam;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuRegisterFormService;
import com.ziroom.suzaku.main.service.SuzakuRemandService;
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
 *  资产退还前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-10-26
 */
@Api(value = "资产退还接口", tags = "资产退还接口")
@RestController
@RequestMapping("/api/v1/remand")
public class SuzakuRemandController {

    @Autowired
    private SuzakuRemandService suzakuRemandService;

    @Autowired
    private BeanMapper beanMapper;

    @PostMapping(value = "/pageRemand")
    @ApiOperation(value = "获取资产退还分页信息", notes = "获取资产退还分页信息")
    public JsonResult<PageData<SuzakuRemandVo>> pageRemand(@RequestBody @ApiParam(value = "资产退还信息") RemandQo qo){
        PageData<SuzakuRemandDto> pageData = suzakuRemandService.pageRemand(qo);
        return JsonResult.ok(beanMapper.mapPageData(pageData,SuzakuRemandVo.class));
    }

    /**
     * 资产退还登记
     * @param applyRemandReqParam
     * @return
     */
    @PostMapping("/saveRemand")
    @ApiOperation(value = "保存资产退还登记信息", notes = "保存资产退还登记信息")
    public JsonResult saveRemand(@ApiParam(value = "资产退还登记信息") @RequestBody ApplyRemandReqParam applyRemandReqParam) {
        suzakuRemandService.saveRemand(applyRemandReqParam);
        return JsonResult.ok();
    }



}
