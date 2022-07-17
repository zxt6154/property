package com.ziroom.suzaku.main.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziroom.suzaku.main.entity.SuzakuCheckItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.po.SuzakuCheckItemPo;
import com.ziroom.suzaku.main.model.qo.CheckItemQo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
public interface SuzakuCheckItemMapper extends BaseMapper<SuzakuCheckItem> {

    /**
     * 获取资产盘点明细信息
     * @param page
     * @param qo
     * @return
     */
    IPage<SuzakuCheckItemPo> selectCheckItemPage(@Param("page")Page<SuzakuCheckItem> page, @Param("qo")CheckItemQo qo);
}
