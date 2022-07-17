package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.param.SkuCreateReqParam;
import com.ziroom.suzaku.main.param.SkuSelectReqParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产sku基础数据维护
 * @author xuzeyu
 */
@Mapper
public interface SuzakuSkuMapper {

    void insert(@Param("skuEntity")SuzakuSkuEntity skuEntity);

    void update(SuzakuSkuEntity skuEntity);

    SuzakuSkuEntity getSuzakuSkuEntityBySkuId(String skuId);

    SuzakuSkuEntity getByName(@Param("skuCreateReqParam")SkuCreateReqParam skuCreateReqParam);

    SuzakuSkuEntity getBySelectParam(SkuSelectReqParam selectParam);

    List<SuzakuSkuEntity> getSkuInfoList(@Param("skus")List<String> skus);

    List<SuzakuSkuEntity> pageSkus(SkuSelectReqParam selectParam);

    Integer skusTotal(SkuSelectReqParam selectParam);

    List<SuzakuSkuEntity> selectSkuByCategoryId(@Param("categoryId")String categoryId);

    List<SuzakuSkuEntity> batchSelect(@Param("skuIds")List<String> skuIds);

    List<SuzakuSkuEntity> getByNameFuzzy(String skuName);
}
