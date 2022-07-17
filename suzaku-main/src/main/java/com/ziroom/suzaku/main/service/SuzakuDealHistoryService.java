package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.ziroom.suzaku.main.entity.SuzakuDealHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.model.qo.HisShowQo;

import java.util.List;

public interface SuzakuDealHistoryService extends IService<SuzakuDealHistory> {

    void saveWhileCreateDealBill(String dealNumStr,  List<SuzakuImportAsserts> asserts);

    boolean check(String dealNumStr, Integer checkState);

    List<String> selAssrtsCode(HisShowQo hisShowQo);
}
