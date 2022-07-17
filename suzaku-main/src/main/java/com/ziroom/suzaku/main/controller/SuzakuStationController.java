package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.entity.SuzakuStation;
import com.ziroom.suzaku.main.service.SuzakuStationService;
import com.ziroom.suzaku.main.utils.DistinctUtils;
import com.ziroom.suzaku.main.utils.StringUtils;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 门店/工作站记录
 * @author xuzeyu
 */
@RestController
@RequestMapping("/station")
public class SuzakuStationController {

    @Autowired
    private SuzakuStationService suzakuStationService;

    /**
     * 门店/工作站入库
     */
    @RequestMapping(value = "/addStationInfo", method = RequestMethod.POST)
    public ModelResponse<String> addStationInfo(@RequestBody List<SuzakuStation> suzakuStationEntities) {
        suzakuStationService.addStationInfo(suzakuStationEntities);
        return ModelResponseUtil.ok("");
    }

    /**
     * 根据管理类型获取门店列表
     */
    @RequestMapping(value = "/getStationByType", method = RequestMethod.GET)
    public ModelResponse<List<SuzakuStation>> getStationByType(@RequestParam String type){
        Integer managerType = null;
        if(StringUtils.isNotBlank(type) && !"null".equals(type)){
            managerType = Integer.parseInt(type);
        }
        List<SuzakuStation> stations = suzakuStationService.getStationByType(managerType);
        stations = stations.stream().filter(DistinctUtils.distinctByKey(SuzakuStation::getStationName)).collect(Collectors.toList());
        return ModelResponseUtil.ok(stations);
    }
}
