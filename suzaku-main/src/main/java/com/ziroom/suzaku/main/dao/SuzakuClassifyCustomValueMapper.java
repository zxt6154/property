package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuClassifyCustomValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产分类扩展属性值
 * @author xuzeyu
 */
@Mapper
public interface SuzakuClassifyCustomValueMapper {

    void insert(@Param("custom")SuzakuClassifyCustomValueEntity custom);

    void batchInsert(@Param("customs")List<SuzakuClassifyCustomValueEntity> customs);

    void batchUpdate(@Param("customs")List<SuzakuClassifyCustomValueEntity> customs);

    void update(@Param("customs")SuzakuClassifyCustomValueEntity customs);

    List<SuzakuClassifyCustomValueEntity> getClassifyCustom(@Param("customIds")List<String> customIds, @Param("skuId")String skuId);

    void tagSku(@Param("skuList")List<SuzakuClassifyCustomValueEntity> skuList);

    void delBatch(@Param("ids")List<Long> ids);

    List<SuzakuClassifyCustomValueEntity> selByskuId(@Param("skuId")String skuId);

    SuzakuClassifyCustomValueEntity ifChange(@Param("customId")String customId, @Param("customName")String customName, @Param("skuId")String skuId);


}
