package com.ziroom.suzaku.main.controller;
import com.ziroom.suzaku.main.constant.enums.*;
import com.ziroom.suzaku.main.model.JsonResult;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@Api(value = "枚举类", tags = "枚举类")
@RestController
@RequestMapping("/api/v1/enums")
@Slf4j
public class EnumsController {

    //入库方式
    @GetMapping(value = "/importWays")
    public JsonResult<List<ImportWay>> getImportWays() { return JsonResult.ok(ImportWay.getAllImportWays());}

    //管理类型
    @GetMapping(value = "/managerTypes")
    public JsonResult<List<ManagerType>> getManagerTypes() { return JsonResult.ok(ManagerType.getAllManagerTypes());}


    @GetMapping(value = "/applyStates")
    public JsonResult<List<ImportState>> getApplyStates() { return JsonResult.ok(ImportState.getAllImportStates());}

    //处置类型
    @GetMapping(value = "/dealTypes")
    public JsonResult<List<DealType>> getDealTypes() { return JsonResult.ok(DealType.getAllDealType());}

    //处置状态
    @GetMapping(value = "/dealStates")
    public JsonResult<List<DealState>> getDealStates() { return JsonResult.ok(DealState.getAllDealStates());}

}
