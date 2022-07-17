package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.ImportState;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.AssertImportReq;
import com.ziroom.suzaku.main.model.dto.req.ImportBillReq;
import com.ziroom.suzaku.main.model.vo.ImportBillAssertVo;
import com.ziroom.suzaku.main.service.ImportBillAssert;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import com.ziroom.suzaku.main.service.SuzakuImportBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @zhangxt
 */
@RestController
@Slf4j
@Api(value = "资产入库")
@RequestMapping("/api/v1/assetImport")
public class AssetImportController {

    @Resource
    private SuzakuImportBillService importBillService;

    @Resource
    private SuzakuImportAssertsService importAssertsService;

    @Resource
    private ImportBillAssert importBillAssert;


    @ApiModelProperty("根据条件查询表单信息")
    @RequestMapping(value = "/listBills", method = RequestMethod.POST)
    public JsonResult<PageData<SuzakuImportBill>> importBills(@RequestBody AssertImportReq assertImportReq){
        PageData<SuzakuImportBill> bills = importBillService.importBillsByCondition(assertImportReq);
        return JsonResult.ok(bills);
    }

    @ApiModelProperty("根据条件查询表单信息")
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public JsonResult init(){
        importBillService.init();
        return JsonResult.ok();
    }

    @GetMapping("/remove")
    @ApiOperation(value = "移除指定资产明细数据", notes = "移除指定资产明细数据")
    public JsonResult deleteClassifyProperty(@RequestParam(name = "assertId") @ApiParam(value = "移除指定资产明细数据") String assertId) {
        return JsonResult.ok(importAssertsService.removeById(assertId));
    }

    @PostMapping("/checkSuccess")
    @ApiOperation(value = "确认审核", notes = "确认审核")
    public JsonResult checkSuccess(@ApiParam(value = "确认审核") @RequestBody ImportBillReq importBill) {
        SuzakuImportBill suzakuImportBill = new SuzakuImportBill();
        BeanUtils.copyProperties(importBill, suzakuImportBill);
        suzakuImportBill.setImportState(ImportState.PASS.getValue());

        return JsonResult.ok(importBillService.updateForCheck(suzakuImportBill,"pass"));
    }

    @PostMapping("/checkReject")
    @ApiOperation(value = "审核驳回", notes = "审核驳回")
    public JsonResult checkReject(@ApiParam(value = "审核驳回")@RequestBody ImportBillReq importBill) {
        SuzakuImportBill suzakuImportBill = new SuzakuImportBill();
        BeanUtils.copyProperties(importBill, suzakuImportBill);
        suzakuImportBill.setImportState(ImportState.REJECT.getValue());

        return JsonResult.ok(importBillService.updateForCheck(suzakuImportBill,"reject"));
    }

    @PostMapping("/submit")
    @ApiOperation(value = "确认提交", notes = "确认提交")
    public JsonResult submit(@RequestBody ImportBillAssertVo billAssertVo) {
        //入库单
        return JsonResult.ok(importBillAssert.saveAssertBill(billAssertVo));
    }

}
