package com.ziroom.suzaku.main.service.impl;

import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.AssertState;
import com.ziroom.suzaku.main.constant.enums.DealState;
import com.ziroom.suzaku.main.constant.enums.DealType;
import com.ziroom.suzaku.main.dao.SuzakuDealHistoryMapper;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.ziroom.suzaku.main.dao.SuzakuDealBillMapper;
import com.ziroom.suzaku.main.entity.SuzakuDealHistory;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.model.dto.req.DealBillsReq;
import com.ziroom.suzaku.main.model.qo.HisQo;
import com.ziroom.suzaku.main.model.vo.AssertSkuVoDeal;
import com.ziroom.suzaku.main.model.vo.DealBillAssertsVo;
import com.ziroom.suzaku.main.service.SuzakuDealBillService;
import com.ziroom.suzaku.main.utils.StringUtils;
import com.ziroom.suzaku.main.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 处置单 服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-10
 */
@Service
public class SuzakuDealBillServiceImpl implements SuzakuDealBillService{


    private static final String preStr = "CZ";

    @Autowired
    private SuzakuDealBillMapper dealBillMapper;

    @Autowired
    private SuzakuDealHistoryMapper historyMapper;



    /**
     * 分页查询
     * @param dealBillsReq
     * @return
     */
    @Override
    public PageData<SuzakuDealBill> pageDealBillsList(DealBillsReq dealBillsReq) {


        List<String> dealLists = dealBillsReq.getDealNums() == null ? Lists.newArrayList() : dealBillsReq.getDealNums();
        PageData<SuzakuDealBill> pageData = new PageData<>();

        if (StringUtils.isNotBlank(dealBillsReq.getDealNum())){
            dealLists.add(dealBillsReq.getDealNum());
        }
        if (!StringUtils.isEmpty(dealBillsReq.getCategoryId())) {
            HisQo qo = new HisQo();
            qo.setCategoryId(dealBillsReq.getCategoryId());
            List<SuzakuDealHistory> histories = historyMapper.selHis(qo);
            if(histories.size() < 0){ //如果没查询到匹配的数据返回空
               return pageData;
            } else {
                List<String> dns = histories.stream().map(his -> {
                    return his.getDealNum();
                }).collect(Collectors.toList());

                dealLists.addAll(dns);
            }
        }
        dealBillsReq.setDealNums(dealLists);

        Integer pageSize = dealBillsReq.getPageSize();
        Integer total = Math.toIntExact(dealBillMapper.total(dealBillsReq));
        int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        //获取总数
        if (dealBillsReq.getOffset() == null) {
            dealBillsReq.setOffset(1);
        }
        if (total > 0){
            List<SuzakuDealBill> suzakuDealBills = dealBillMapper.pageDealBillsList(dealBillsReq);
            pageData.setData(suzakuDealBills);
            pageData.setPageNum(dealBillsReq.getOffset());
            pageData.setPageSize(pageSize);
            pageData.setTotal(total);
            pageData.setPages(page);
        }
        return pageData;
    }

    @Override
    public PageData<SuzakuDealBill> mockPageDealBillsList(DealBillsReq dealBillsReq) {
        //获取总数
        Integer total = Math.toIntExact(dealBillMapper.total(dealBillsReq));
        PageData<SuzakuDealBill> pageData = new PageData<>();
        if (total == 0){
            List<SuzakuDealBill> suzakuDealBills = new ArrayList<>();
//            suzakuDealBills.add(new SuzakuDealBill(0L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(10), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(1L, "c", 3, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(10), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(2L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(11), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(3L, "c", 2, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(12), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(4L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(13), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(5L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(14), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(6L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(15), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(7L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(16), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(8L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(17), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(9L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(18), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(0L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(19), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(1L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(20), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(2L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(21), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(3L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(22), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(4L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(23), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(5L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(24), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(6L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(25), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(7L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(26), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(8L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(27), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));
//            suzakuDealBills.add(new SuzakuDealBill(9L, "c", 1, 1, "frefs", BigDecimal.ZERO, 100, new BigDecimal(28), LocalDateTime.now(), "128", "zhangxt3", "creatoeDept", LocalDateTime.now(), 2, 3, LocalDateTime.now(), "ZHANGXT3"));


            Integer pageSize = dealBillsReq.getPageSize();
            int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

            pageData.setData(suzakuDealBills);
//            pageData.setPageNum(dealBillsReq.getOffset());
//            pageData.setPageSize(pageSize);
//            pageData.setTotal(total);
//            pageData.setPages(page);
        }
        return pageData;
    }


    @Override
    public String add(DealBillAssertsVo dealBillAssertsVo) {
        //生成处置单编码
        StringBuffer ab = new StringBuffer(preStr);
        String dealNumStr = ab.append(UUIDUtil.getUID()).toString();
        SuzakuDealBill suzakuDealBill = dealBillAssertsVo.getSuzakuDealBill();
        suzakuDealBill.setDealNum(dealNumStr);
        suzakuDealBill.setDealState(DealState.WAITING_CHECKING.getValue());

        dealBillMapper.add(suzakuDealBill);
        //存到历史处置数据表
       // suzakuDealHistoryService
        return dealNumStr;
    }

    @Override
    public void update(SuzakuDealBill suzakuDealBill) {
        dealBillMapper.update(suzakuDealBill);
    }

    @Override
    public String checkPass( SuzakuDealBill suzakuDealBill) {
        suzakuDealBill.setDealState(DealState.PASS.getValue());
        this.update(suzakuDealBill);
        return "审核通过";
    }

    @Override
    public String checkReject(SuzakuDealBill suzakuDealBill) {
        suzakuDealBill.setDealState(DealState.REJECT.getValue());
        this.update(suzakuDealBill);
        return "审核驳回";
    }

    @Override
    /**
     * 回显数据
     */
    public DealBillAssertsVo reShow(SuzakuDealBill suzakuDealBill) {

        return null;
    }


}

