package com.ziroom.suzaku.main.service.impl;

import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.constant.enums.AssertState;
import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.ziroom.suzaku.main.entity.SuzakuDealHistory;
import com.ziroom.suzaku.main.dao.SuzakuDealHistoryMapper;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.qo.HisQo;
import com.ziroom.suzaku.main.model.qo.HisShowQo;
import com.ziroom.suzaku.main.service.SuzakuDealHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 处置历史数据 服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-16
 */
@Service
public class SuzakuDealHistoryServiceImpl extends ServiceImpl<SuzakuDealHistoryMapper, SuzakuDealHistory> implements SuzakuDealHistoryService {

    @Resource
    private SuzakuDealHistoryMapper historyMapper;

    @Override
    public void saveWhileCreateDealBill(String dealNumStr, List<SuzakuImportAsserts> asserts) {
        List<SuzakuDealHistory> dealHistories = asserts.stream().map(entity -> {
            SuzakuImportAsserts importAssert = entity;
            SuzakuSkuEntity skuEntity = entity.getSkuEntity();
            return SuzakuDealHistory.builder()
                    .dealNum(dealNumStr)
                    .assertCode(importAssert.getAssertsCode())
                    .oldAssertsCode(importAssert.getOldAssertsCode())
                    .skuId(importAssert.getSku())
                    .dealState(AssertState.State_6.getValue())
                    .categoryId(importAssert.getCategoryId())
                    .createTime(LocalDateTime.now())
                    .build();
        }).collect(Collectors.toList());
        this.saveBatch(dealHistories);
    }
    @Override
    public boolean check(String dealNumStr, Integer checkState) {
            HisQo qo = new HisQo();
                qo.setDealNum(dealNumStr);

            Integer count = Math.toIntExact(historyMapper.selHisTotal(qo));

            List<SuzakuDealHistory> suzakuDealHistories = Lists.newArrayList();
            if (count > 0){
                suzakuDealHistories = historyMapper.selHis(qo);
            }

            List<SuzakuDealHistory> histories = suzakuDealHistories.stream().map(history -> {
                history.setDealState(checkState);
                return history;
            }).collect(Collectors.toList());
            return this.updateBatchById(histories);
    }
    @Override
    public List<String> selAssrtsCode(HisShowQo hisShowQo) {
            HisQo qo = new HisQo();
//        Integer pageSize = qo.getPageSize();
            qo.setDealNum(hisShowQo.getDealBill().getDealNum());
        Integer total = Math.toIntExact(historyMapper.selHisTotal(qo));
//        int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
      //  qo.setDealNum(hisShowQo.getDealBill().getDealNum());

        List<String> assertsCodes = Lists.newArrayList();
        if (total > 0){
            List<SuzakuDealHistory> suzakuDealHistories = historyMapper.selHis(qo);
            assertsCodes = suzakuDealHistories.stream().map(history -> {
                return history.getAssertCode();
            }).collect(Collectors.toList());
        }

        return assertsCodes;
    }


}
