package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuImportAssertsDto;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.model.dto.resp.Assert;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.model.qo.ImportAssertQo;
import com.ziroom.suzaku.main.model.vo.AssertSkuVo;
import com.ziroom.suzaku.main.model.vo.SuzakuImportAssertsVo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.SkuSelectReqParam;

import java.io.IOException;
import java.util.List;

public interface SuzakuImportAssertsService extends IService<SuzakuImportAsserts> {

    //Boolean addAssets() throws IOException;
    /**
     * 根据入库单获取相应的资产和sku信息
     * @param assertSelectReqParam
     * @return
     */
    PageData<AssertSkuVo> listByBillNum(AssertSelectReqParam assertSelectReqParam);

    /**
     * 获取资产列表分页信息
     * @param qo
     * @return
     */
    PageData<SuzakuImportAssertsDto> getAssertsList(AssertQo qo);

    /**
     * 查询资产列表
     * @param qo
     * @return
     */
    List<SuzakuImportAssertsDto> assertList(AssertQo qo);

    void updateBatch(List<SuzakuImportAsserts> lists);

}
