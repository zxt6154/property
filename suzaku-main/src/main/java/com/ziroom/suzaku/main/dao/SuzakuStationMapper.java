package com.ziroom.suzaku.main.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.entity.SuzakuStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 门店/工作站记录
 * @author xuzeyu
 */
@Mapper
public interface SuzakuStationMapper extends BaseMapper<SuzakuStation> {

    void insert(@Param("stationList")List<SuzakuStation> suzakuStationEntities);

    List<SuzakuStation> getStationByType(Integer type);

    SuzakuStation getStationById(Integer id);
}
