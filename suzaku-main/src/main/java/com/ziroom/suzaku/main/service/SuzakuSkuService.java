package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.dto.SuzakuSkuDto;
import com.ziroom.suzaku.main.param.SkuCreateReqParam;
import com.ziroom.suzaku.main.param.SkuSelectReqParam;

import java.util.List;

/**
 * 资产sku基础数据
 * @author xuzeyu
 */
public interface SuzakuSkuService {

    void create(SkuCreateReqParam skuCreateReqParam);

    void update(SkuCreateReqParam skuCreateReqParam);

    void update2(SkuCreateReqParam skuCreateReqParam);

    SuzakuSkuDto getSuzakuSkuDtoBySkuId(String skuId);

    PageData<SuzakuSkuEntity> pageSkus(SkuSelectReqParam skuSelectReqParam);

    List<SuzakuSkuEntity> getSkuInfoList(List<String> skus);

    /**
     * 分类下是否存在资产sku
     * @param categoryId
     * @return
     */
    List<SuzakuSkuEntity> haveSkuByCategoryId(String categoryId);

    List<String> searchSkuFuzzy(String skuName);

    List<SuzakuSkuEntity> searchSku(String skuName);

}
