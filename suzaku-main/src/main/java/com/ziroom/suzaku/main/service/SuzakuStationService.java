package com.ziroom.suzaku.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.entity.SuzakuStation;

import java.util.List;

/**
 * 门店/工作站记录
 * @author xuzeyu
 */
public interface SuzakuStationService extends IService<SuzakuStation> {

    void addStationInfo(List<SuzakuStation> suzakuStationEntities);

    List<SuzakuStation> getStationByType(Integer type);

    SuzakuStation getStationById(Integer type);

}
