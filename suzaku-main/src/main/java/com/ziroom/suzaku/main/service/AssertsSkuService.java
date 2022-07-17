package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;

import java.util.List;

public interface AssertsSkuService {

    List<SuzakuImportAsserts> assertsList(AssertsSkuQo assertsSkuQo);

    PageData<SuzakuImportAsserts> assertJoinSku(AssertsSkuQo assertsSkuQo);

    /**
     * 获取处置单资产详细
     * @param assertsSku
     * @return
     */
    PageData<SuzakuImportAsserts> getAssertList(AssertsSkuQo assertsSku);
}
