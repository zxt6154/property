package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.dto.req.AssertImportReq;
import io.swagger.models.auth.In;

import java.util.List;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */
public interface SuzakuImportBillMapper extends BaseMapper<SuzakuImportBill> {

    List<SuzakuImportBill> selectByCondition(AssertImportReq assertImportReq);

    List<SuzakuImportBill> selectAll(AssertImportReq assertImportReq);

    Integer selectTotal(AssertImportReq assertImportReq);

    List<SuzakuImportBill> getListByType(Integer type);

    List<SuzakuImportBill> getListByTypeAndR(Integer type, String remandDepot);

    SuzakuImportBill getByNum(String num);

}
