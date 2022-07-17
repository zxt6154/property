package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.components.DealBillAssertHisComponent;
import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.exception.SuzakuBussinesException;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.DealBillsReq;
import com.ziroom.suzaku.main.model.qo.HisShowQo;
import com.ziroom.suzaku.main.model.vo.DealBillAssertsVo;
import com.ziroom.suzaku.main.service.SuzakuDealBillService;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import com.ziroom.tech.enums.ResponseEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@Api("资产处置页面相关")
@RequestMapping("/api/v1/dealAsserts")
public class DealAssertsController {

    @Autowired
    private SuzakuDealBillService dealBillService;

    @Autowired
    private DealBillAssertHisComponent component;


    //1、按条件查询、分页展示资产处置表单
    @ApiModelProperty("根据条件查询表单信息")
    @RequestMapping(value = "/listBills", method = RequestMethod.POST)
    public JsonResult<PageData<SuzakuDealBill>> importBills(@RequestBody DealBillsReq dealBillsReq){
        PageData<SuzakuDealBill> dealBills = dealBillService.pageDealBillsList(dealBillsReq);
        return JsonResult.ok(dealBills);
    }

    //3、创建处置单
    @ApiModelProperty("创建处置单")
    @RequestMapping(value = "/createDealBill", method = RequestMethod.POST)
    public JsonResult createDealBill(@RequestBody DealBillAssertsVo dealBillAssertsVo){

        //如果没有选资产
        if(dealBillAssertsVo.getAsserts().size() == 0){
            throw new SuzakuBussinesException(ResponseEnum.RESPONSE_SUCCESS_CODE.getCode(), "选择资产不能为空");
        }
        //创建处置单以及添加历史处置资产
        component.createDealBill(dealBillAssertsVo);
        return JsonResult.ok();
    }

    //4、审核处置单  事务
    @ApiModelProperty("审核成功")
    @RequestMapping(value = "/checkPass", method = RequestMethod.POST)
    public JsonResult checkPass(@RequestBody SuzakuDealBill suzakuDealBill){
        return JsonResult.ok(component.checkPass(suzakuDealBill));
    }

    @ApiModelProperty("审核驳回")
    @RequestMapping(value = "/checkReject", method = RequestMethod.POST)
    public JsonResult checkReject(@RequestBody SuzakuDealBill suzakuDealBill){
        return JsonResult.ok( component.checkReject(suzakuDealBill));
    }

    @ApiModelProperty("查看")
    @RequestMapping(value = "/showDetail", method = RequestMethod.POST)
    public JsonResult<PageData<SuzakuImportAsserts>> showDetail(@RequestBody HisShowQo hisShowQo){
        return JsonResult.ok(component.show(hisShowQo));
    }




}
