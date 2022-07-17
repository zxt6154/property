package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.req.DealBillsReq;
import com.ziroom.suzaku.main.model.vo.DealBillAssertsVo;
import org.apache.ibatis.annotations.Mapper;



public interface SuzakuDealBillService {

    //分页查询
    PageData<SuzakuDealBill> pageDealBillsList(DealBillsReq dealBillsReq);

    //分页查询
    PageData<SuzakuDealBill> mockPageDealBillsList(DealBillsReq dealBillsReq);

    //新增
    String add(DealBillAssertsVo dealBillAssertsVo);

    void update(SuzakuDealBill suzakuDealBill);

    String checkPass( SuzakuDealBill suzakuDealBill);

    String checkReject( SuzakuDealBill suzakuDealBill);

    DealBillAssertsVo reShow(SuzakuDealBill suzakuDealBill);

}
