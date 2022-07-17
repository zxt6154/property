package com.ziroom.suzaku.main.controller;


import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.entity.SuzakuDepot;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.SuzakuDepotDto;
import com.ziroom.suzaku.main.model.vo.SuzakuDepotVo;
import com.ziroom.suzaku.main.service.SuzakuDepotService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author libingsi
 * @since 2021-11-01
 */
@Api(value = "资产仓库接口", tags = "资产仓库接口")
@RestController
@RequestMapping("/v1/api/depot")
public class SuzakuDepotController {

    @Autowired
    private SuzakuDepotService suzakuDepotService;


    @Autowired
    private BeanMapper beanMapper;

    /**
     * 仓库信息入库
     */
    @RequestMapping(value = "/addDepotInfo", method = RequestMethod.POST)
    public ModelResponse<String> addDepotInfo(@RequestBody List<SuzakuDepot> suzakuDepots) {
        suzakuDepotService.addDepotInfo(suzakuDepots);
        return ModelResponseUtil.ok("");
    }

    @RequestMapping(value = "/getDepot", method = RequestMethod.GET)
    public JsonResult<List<SuzakuDepotVo>> getDepot() {
        List<SuzakuDepotDto> depotDtos = suzakuDepotService.getDepot();
        List<SuzakuDepotVo> vos = beanMapper.mapList(depotDtos,SuzakuDepotVo.class);
        return JsonResult.ok(vos);
    }
}
