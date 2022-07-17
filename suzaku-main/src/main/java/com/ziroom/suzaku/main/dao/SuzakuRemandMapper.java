package com.ziroom.suzaku.main.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuRemand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.po.SuzakuRemandPo;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.model.qo.RemandQo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-10-26
 */
public interface SuzakuRemandMapper extends BaseMapper<SuzakuRemand> {

    /**
     * 获取资产退还分页信息
     * @param page
     * @param qo
     * @return
     */
    IPage<SuzakuRemandPo> selectRemandPage(@Param("page")Page<SuzakuRemand> page, @Param("qo")RemandQo qo);
}
