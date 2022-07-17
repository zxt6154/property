package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.ImportBillReq;
import com.ziroom.suzaku.main.model.dto.resp.ExcelImportDetail;
import com.ziroom.suzaku.main.model.vo.AssertSkuVo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */
@RestController
@RequestMapping("/api/v1/importBill")
public class SuzakuImportBillController {

    @Autowired
    private SuzakuImportAssertsService importAssertsService;


    @PostMapping("/assertSkuVos")
    @ApiOperation(value = "根据入库单获取相应的资产和sku信息", notes = "根据入库单获取相应的资产和sku信息")
    public JsonResult pageAssertSkuVoList(@RequestBody ImportBillReq importBillReq){

        List<String> billNums = new ArrayList<>();
        billNums.add(importBillReq.getImportBillNum());

        AssertSelectReqParam reqParam = new AssertSelectReqParam();
                             reqParam.setImportBills(billNums);
                             reqParam.setPageSize(importBillReq.getPageSize());
                             reqParam.setOffset(importBillReq.getOffset());

         //获取对应的资产数据
        PageData<AssertSkuVo> pageList = importAssertsService.listByBillNum(reqParam);

        return JsonResult.ok(pageList);
    }

}
