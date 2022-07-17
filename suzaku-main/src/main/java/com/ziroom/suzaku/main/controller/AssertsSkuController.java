package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.AssertImportReq;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.service.AssertsSkuService;
import com.ziroom.suzaku.main.service.ImportBillAssert;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import com.ziroom.suzaku.main.service.SuzakuImportBillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@Api(value = "资产与sku联表")
@RequestMapping("/api/v1/assertsSku")
public class AssertsSkuController {

    @Resource
    private AssertsSkuService assertsSkuService;

    @ApiModelProperty("关联资产和sku")
    @RequestMapping(value = "/assertJoinSku", method = RequestMethod.POST)
    public JsonResult<PageData<SuzakuImportAsserts>> assertJoinSku(@RequestBody AssertsSkuQo assertsSkuQo){
        PageData<SuzakuImportAsserts> suzakuImportAssertsPageData = assertsSkuService.assertJoinSku(assertsSkuQo);
        return JsonResult.ok(suzakuImportAssertsPageData);
    }



}
