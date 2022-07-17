package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.req.AssertImportReq;
import com.ziroom.suzaku.main.model.dto.resp.AssertImportResp;
import com.ziroom.suzaku.main.model.dto.resp.CreateBill;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */
public interface SuzakuImportBillService extends IService<SuzakuImportBill> {

    PageData<SuzakuImportBill> importBillsByCondition(AssertImportReq assertImportReq);

    SuzakuImportBill newBills(CreateBill newbill);

    SuzakuImportBill updateForCheck(SuzakuImportBill newbill, String checkState);

    void init();
}
