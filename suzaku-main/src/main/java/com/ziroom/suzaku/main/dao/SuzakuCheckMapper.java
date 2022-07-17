package com.ziroom.suzaku.main.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziroom.suzaku.main.entity.SuzakuCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.po.SuzakuCheckPo;
import com.ziroom.suzaku.main.model.po.SuzakuRemandPo;
import com.ziroom.suzaku.main.model.qo.CheckQo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 盘点任务 Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
public interface SuzakuCheckMapper extends BaseMapper<SuzakuCheck> {

    /**
     *获取资产盘点计划信息
     * @param page
     * @param qo
     * @return
     */
    IPage<SuzakuCheckPo> selectCheckPage(@Param("page")Page<SuzakuCheck> page, @Param("qo") CheckQo qo);
}
