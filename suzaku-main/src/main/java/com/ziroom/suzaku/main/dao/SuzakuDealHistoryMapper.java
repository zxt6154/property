package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuDealHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.qo.HisQo;

import java.util.List;

/**
 * <p>
 * 处置历史数据 Mapper 接口
 * </p>
 *
 * @author libingsi
 * @since 2021-11-16
 */
public interface SuzakuDealHistoryMapper extends BaseMapper<SuzakuDealHistory> {

    void saveBatch(List<SuzakuDealHistory> dealHistories);

    List<SuzakuDealHistory> selHis(HisQo hisQo);

   // List<SuzakuDealHistory> selHisByCat(String cate);

    List<SuzakuDealHistory> selHisPage(HisQo hisQo);

    long selHisTotal(HisQo hisQo);

}
