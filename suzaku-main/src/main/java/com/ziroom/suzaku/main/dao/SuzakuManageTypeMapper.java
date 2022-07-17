package com.ziroom.suzaku.main.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.entity.SuzakuManageType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 管理类型
 * @author xuzeyu
 */
@Mapper
public interface SuzakuManageTypeMapper extends BaseMapper<SuzakuManageType> {

    List<SuzakuManageType> getManagerType();
}
