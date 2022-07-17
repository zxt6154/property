package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuDepot;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 仓库表 Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-11-01
 */
public interface SuzakuDepotMapper extends BaseMapper<SuzakuDepot> {

    void insert(@Param("suzakuDepots")List<SuzakuDepot> suzakuDepots);
}
