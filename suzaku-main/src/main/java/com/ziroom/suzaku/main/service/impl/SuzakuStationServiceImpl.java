package com.ziroom.suzaku.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.entity.SuzakuStation;
import com.ziroom.suzaku.main.dao.SuzakuStationMapper;
import com.ziroom.suzaku.main.service.SuzakuStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店/工作站记录
 * @author xuzeyu
 */
@Service
public class SuzakuStationServiceImpl extends ServiceImpl<SuzakuStationMapper, SuzakuStation> implements SuzakuStationService {

    @Autowired
    private SuzakuStationMapper suzakuStationMapper;

    @Override
    public void addStationInfo(List<SuzakuStation> suzakuStationEntities) {
        suzakuStationMapper.insert(suzakuStationEntities);
    }

    /**
     * 根据类型查询门店列表
     */
    public List<SuzakuStation> getStationByType(Integer type){
        return suzakuStationMapper.getStationByType(type);
    }

    @Override
    public SuzakuStation getStationById(Integer id) {
        return suzakuStationMapper.getStationById(id);
    }
}
