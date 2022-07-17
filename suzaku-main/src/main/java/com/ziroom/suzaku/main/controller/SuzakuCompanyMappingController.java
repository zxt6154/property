package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.entity.SuzakuCompanyMapping;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.CompanyMappingReq;
import com.ziroom.suzaku.main.service.SuzakuCompanyMappingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-10-19
 */
@RestController
@RequestMapping("/api/v1/importCompany")
public class SuzakuCompanyMappingController {

    @Resource
    private SuzakuCompanyMappingService companyMappingService;


    @GetMapping("/companyMapping")
    public JsonResult getCompanyMapping(){

        List<SuzakuCompanyMapping> list = companyMappingService.list(null);
        return JsonResult.ok(list);

    }

    @PostMapping("/fuzzySearch")
    public JsonResult fuzzySearch(@RequestBody CompanyMappingReq companyMappingReq){
        List<SuzakuCompanyMapping> suzakuCompanyMappings = companyMappingService.fuzzySearch(companyMappingReq);
        return JsonResult.ok(suzakuCompanyMappings);
    }

}
