package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ziroom.suzaku.main.model.dto.req.DealBillsReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SuzakuDealBillMapper {

    List<SuzakuDealBill> pageDealBillsList(DealBillsReq dealBillsReq);

    Long total(DealBillsReq dealBillsReq);

    int add(SuzakuDealBill suzakuDealBill);

    void update(SuzakuDealBill suzakuDealBill);
}
