package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.entity.SuzakuClassifyProperty;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.vo.ClassifyPropertyVo;
import com.ziroom.suzaku.main.model.vo.SuzakuClassifyPropertyVo;
import com.ziroom.suzaku.main.service.SuzakuClassifyPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 资产分类表 前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Api(value = "资产分类接口", tags = "资产分类接口")
@RestController
@RequestMapping("/api/v1/property")
public class SuzakuClassifyPropertyController {

    @Autowired
    private SuzakuClassifyPropertyService suzakuClassifyPropertyService;


    @GetMapping("/getClassifyPropertyTreeNode")
    @ApiOperation(value = "获取资产分类树状结构信息", notes = "获取资产分类树状结构信息")
    public JsonResult<List<SuzakuClassifyPropertyVo>> getClassifyPropertyTreeNode() {
        List<SuzakuClassifyPropertyVo> suzakuClassifyPropertyVos = suzakuClassifyPropertyService.getClassifyPropertyTreeNode();
        return JsonResult.ok(suzakuClassifyPropertyVos);
    }


    @GetMapping("/getClassifyPropertyInfo")
    @ApiOperation(value = "获取某个资产分类信息和子资产", notes = "获取某个资产分类信息和子资产")
    public JsonResult<List<SuzakuClassifyPropertyVo>> getClassifyPropertyInfo(@RequestParam(name = "classifyId") @ApiParam(value = "资产分类标识") String classifyId) {
        List<SuzakuClassifyPropertyVo> suzakuClassifyPropertyVos = suzakuClassifyPropertyService.getClassifyPropertyInfo(classifyId);
        return JsonResult.ok(suzakuClassifyPropertyVos);
    }

    @GetMapping("/getClassifyInfoById")
    @ApiOperation(value = "获取某个资产分类信息", notes = "获取某个资产分类信息")
    public JsonResult<SuzakuClassifyProperty> getClassifyInfoById(@RequestParam(name = "classifyId") @ApiParam(value = "资产分类id") String classifyId) {
        SuzakuClassifyProperty classifyInfoById = suzakuClassifyPropertyService.getClassifyInfoById(classifyId);
        return JsonResult.ok(classifyInfoById);
    }


    @PostMapping("/saveClassifyProperty")
    @ApiOperation(value = "保存资产分类信息", notes = "保存资产分类信息")
    public JsonResult saveClassifyProperty(@ApiParam(value = "资产分类信息") @RequestBody ClassifyPropertyVo vo) {
        suzakuClassifyPropertyService.saveClassifyProperty(vo);
        return JsonResult.ok();
    }

    @GetMapping("/deleteClassifyProperty")
    @ApiOperation(value = "删除资产分类信息", notes = "删除资产分类信息")
    public JsonResult deleteClassifyProperty(@RequestParam(name = "classifyId") @ApiParam(value = "资产分类标识") String classifyId) {
        suzakuClassifyPropertyService.deleteClassifyProperty(classifyId);
        return JsonResult.ok();
    }


    @GetMapping("/getClassifyProperty")
    @ApiOperation(value = "获取某个资产分类信息", notes = "获取某个资产分类信息")
    public JsonResult<SuzakuClassifyPropertyVo> getClassifyProperty(@RequestParam(name = "classifyId") @ApiParam(value = "资产分类标识") String classifyId) {
        SuzakuClassifyPropertyVo vo = suzakuClassifyPropertyService.getClassifyProperty(classifyId);
        return JsonResult.ok(vo);
    }

    @GetMapping("/checkClassifySku")
    @ApiOperation(value = "判断资产分类下是否存在资产", notes = "删除时判断资产分类下是否存在资产 ( 0 : 存在资产，1：不存在资产)")
    public JsonResult<Integer> checkClassifySku(@RequestParam(name = "classifyId") @ApiParam(value = "资产分类标识") String classifyId) {
        int result = suzakuClassifyPropertyService.checkClassifySku(classifyId);
        return JsonResult.ok(result);
    }

    @GetMapping("/checkClassifyName")
    @ApiOperation(value = "判断资产分类名称是否存在", notes = "添加分类时判断资产分类名称是否存在( 0 : 存在资产分类名称，1：不存在资产分类名称)")
    public JsonResult<Integer> checkClassifyName(@RequestParam(name = "classifyName") @ApiParam(value = "资产分类名称") String classifyName) {
        int result = suzakuClassifyPropertyService.checkClassifyName(classifyName);
        return JsonResult.ok(result);
    }
}
