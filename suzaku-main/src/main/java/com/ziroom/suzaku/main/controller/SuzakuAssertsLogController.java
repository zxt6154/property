package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertsLogDto;
import com.ziroom.suzaku.main.model.vo.SuzakuAssertsLogVo;
import com.ziroom.suzaku.main.service.SuzakuAssertsLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-10-25
 */
@Api(value = "资产操作log接口", tags = "资产操作log接口")
@RestController
@RequestMapping("/api/v1/log")
public class SuzakuAssertsLogController {

    @Autowired
    private SuzakuAssertsLogService suzakuAssertsLogService;

    @Autowired
    private BeanMapper beanMapper;


    @GetMapping("/getAssertsLog")
    @ApiOperation(value = "获取资产操作log", notes = "获取资产操作log信息")
    public JsonResult<List<SuzakuAssertsLogVo>> getAssertsLog(@RequestParam(name = "id") @ApiParam(value = "资产信息标识") String id) {
        List<SuzakuAssertsLogDto> dtos = suzakuAssertsLogService.getAssertsLog(id);
        List<SuzakuAssertsLogVo> vos = beanMapper.mapList(dtos,SuzakuAssertsLogVo.class);
        return JsonResult.ok(vos);
    }

}
