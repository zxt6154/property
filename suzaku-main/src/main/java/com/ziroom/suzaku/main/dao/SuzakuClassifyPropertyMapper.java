package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuClassifyProperty;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资产分类表 Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
public interface SuzakuClassifyPropertyMapper extends BaseMapper<SuzakuClassifyProperty> {

    /**
     * 获取某个资产分类信息下的子资产
     * @param classifyId
     * @return
     */
    List<SuzakuClassifyProperty> getClassifyPropertyAndChildren(@Param("classifyId") String classifyId);

    SuzakuClassifyProperty getClassInfoById(@Param("classifyId") String classifyId);

    List<SuzakuClassifyProperty> getClassifyInfos(@Param("classifyIds")List<String> classifyIds);

    /**
     * 父节点最大值
     * @return
     */
    String selectRootMax();

    /**
     * 子节点最大值
     * @return
     */
    String selectChildrenMax(@Param("seniorId") String seniorId);

    /**
     * 查询全部数据
     * @return
     */
    List<SuzakuClassifyProperty> selectAllData();

}
