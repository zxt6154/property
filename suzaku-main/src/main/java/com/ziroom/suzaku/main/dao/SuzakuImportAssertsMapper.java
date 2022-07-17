package com.ziroom.suzaku.main.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */

public interface SuzakuImportAssertsMapper extends BaseMapper<SuzakuImportAsserts> {

    Integer total(@Param("reqParam")AssertSelectReqParam assertSelectReqParam);

    List<SuzakuImportAsserts> getPageAssertsByBillIds(@Param("reqParam") AssertSelectReqParam assertSelectReqParam);

    void batchUpdate(@Param("suzakuImportAsserts")List<SuzakuImportAsserts> suzakuImportAsserts);

    IPage<SuzakuImportAsserts> selectAssertsList(@Param("page")Page<SuzakuImportAsserts> page,@Param("qo") AssertQo qo);

    List<SuzakuImportAsserts> selectAssertsList(@Param("qo") AssertQo qo);

    SuzakuImportAsserts selByCode(String assertCode);

    Integer totalAssert(@Param("qo") AssertQo qo);

    List<SuzakuImportAsserts> assoSearch(@Param("reqParam") AssertsSkuQo assertsSkuQo);

    List<SuzakuImportAsserts> searchAsso(@Param("reqParam") AssertsSkuQo assertsSkuQo);
   // void batchSave(@Param("asserts")List<SuzakuImportAsserts> suzakuImportAsserts);
   int assoSearchTotal(@Param("reqParam") AssertsSkuQo assertsSkuQo);

}
