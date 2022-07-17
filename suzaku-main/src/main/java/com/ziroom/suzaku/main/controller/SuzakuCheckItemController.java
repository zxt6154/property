package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.CheckSkuDto;
import com.ziroom.suzaku.main.model.dto.SuzakuCheckItemDto;
import com.ziroom.suzaku.main.model.qo.CheckItemQo;
import com.ziroom.suzaku.main.model.vo.CheckSkuVo;
import com.ziroom.suzaku.main.model.vo.SuzakuCheckItemVo;
import com.ziroom.suzaku.main.model.vo.CheckItemNukeVo;
import com.ziroom.suzaku.main.service.SuzakuCheckItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
@RestController
@Api(value = "盘点任务明细接口", tags = "盘点任务明细接口")
@RequestMapping("/api/v1/checkItem")
public class SuzakuCheckItemController {


    @Autowired
    private SuzakuCheckItemService suzakuCheckItemService;

    @Autowired
    private BeanMapper beanMapper;


    @PostMapping(value = "/pageCheckItem")
    @ApiOperation(value = "获取资产盘点明细信息", notes = "获取资产盘点明细信息")
    public JsonResult<PageData<SuzakuCheckItemVo>> pageCheckItem(@RequestBody @ApiParam(value = "获取资产盘点明细信息") CheckItemQo qo){
        PageData<SuzakuCheckItemDto> pageData = suzakuCheckItemService.pageCheckItem(qo);
        return JsonResult.ok(beanMapper.mapPageData(pageData,SuzakuCheckItemVo.class));
    }


    @GetMapping(value = "/getCheckItemInfo")
    @ApiOperation(value = "获取盘点结果信息", notes = "获取盘点结果信息")
    public JsonResult<CheckSkuVo> getCheckItemInfo(@RequestParam(value = "id") @ApiParam(value = "盘点明细id") String id){
        CheckSkuDto dtos = suzakuCheckItemService.getCheckItemInfo(id);
        CheckSkuVo vos = beanMapper.map(dtos,CheckSkuVo.class);
        return JsonResult.ok(vos);
    }


    /**
     * 审核盘点单明细状态
     * @param vo
     * @return
     */
    @PostMapping(value = "/updateCheckItemStatus")
    @ApiOperation(value = "审核盘点单明细状态", notes = "审核盘点单明细状态")
    public JsonResult updateCheckItemStatus(@RequestBody CheckSkuVo vo){
        suzakuCheckItemService.updateCheckItemStatus(vo);
        return JsonResult.ok();
    }

    /**
     * 审核盘点单明细状态及资产信息
     * @param vo
     * @return
     */
    @PostMapping("/updateCheckItem")
    @ApiOperation(value = "审核盘点单明细状态及资产信息", notes = "审核盘点单明细状态及资产信息")
    public JsonResult updateCheckItem(@ApiParam(value = "审核盘点单明细状态及资产信息请求参数") @RequestBody CheckSkuVo vo) {
        suzakuCheckItemService.updateCheckItem(vo);
        return JsonResult.ok();
    }

}
