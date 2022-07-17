package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.model.dto.SuzakuApplyFormDto;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.param.ApplyFormAddReqParam;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.ApplyFormSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuRegisterFormService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 资产领借登记
 * @author xuzeyu
 */
@RestController
@RequestMapping("/register/form")
public class SuzakuRegisterFormController {

    @Autowired
    private SuzakuRegisterFormService suzakuRegisterFormService;


    /**
     * 分页查询
     */
    @RequestMapping(value = "/pageRegisterForms", method = RequestMethod.POST)
    public ModelResponse<PageData<SuzakuApplyFormDto>> pageRegisterForms(@RequestBody ApplyFormSelectReqParam reqParam){
        PageData<SuzakuApplyFormDto> applyFormItems = suzakuRegisterFormService.page(reqParam);
        return ModelResponseUtil.ok(applyFormItems);
    }

    /**
     * 查询闲置资产
     */
    @RequestMapping(value = "/pageGetLeaveAssert", method = RequestMethod.POST)
    public ModelResponse<PageData<SuzakuAssertDto>> pageGetLeaveAssert(@RequestBody AssertSelectReqParam assertSelectReqParam){
        PageData<SuzakuAssertDto> pageData = suzakuRegisterFormService.pageGetLeaveAssert(assertSelectReqParam);
        return ModelResponseUtil.ok(pageData);
    }

    /**
     * 登记资产
     */
    @RequestMapping(value = "/assertAdd", method = RequestMethod.POST)
    public ModelResponse<String> assertAdd(@RequestBody ApplyFormAddReqParam applyFormAddReqParam){
        suzakuRegisterFormService.assertAdd(applyFormAddReqParam);
        return ModelResponseUtil.ok("sucess");
    }


}
