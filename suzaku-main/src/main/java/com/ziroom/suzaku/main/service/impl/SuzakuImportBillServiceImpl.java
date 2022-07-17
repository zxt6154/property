package com.ziroom.suzaku.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.components.DealBillAssertHisComponent;
import com.ziroom.suzaku.main.constant.enums.AssertState;
import com.ziroom.suzaku.main.constant.enums.ImportState;
import com.ziroom.suzaku.main.constant.enums.ApplyState;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.dao.SuzakuImportBillMapper;
import com.ziroom.suzaku.main.model.dto.req.AssertImportReq;
import com.ziroom.suzaku.main.model.dto.resp.CreateBill;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuImportBillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.utils.DateUtils;
import com.ziroom.suzaku.main.utils.StringUtils;
import com.ziroom.suzaku.main.utils.UUIDUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-14
 */
@Service
public class SuzakuImportBillServiceImpl extends ServiceImpl<SuzakuImportBillMapper, SuzakuImportBill> implements SuzakuImportBillService {

    private static String preStr = "RK";

    private AtomicInteger atomicInteger;

    @Resource
    private SuzakuImportBillMapper importBillMapper;

    @Resource
    private SuzakuImportAssertsMapper importAssertsMapper;

    @Autowired
    private DealBillAssertHisComponent component;


    //条件查询表单
    @Override
    public PageData<SuzakuImportBill> importBillsByCondition(AssertImportReq assertImportReq) {


        Integer total = importBillMapper.selectTotal(assertImportReq);
        if (assertImportReq.getOffset() == null) {
            assertImportReq.setOffset(1);
        }
        if (assertImportReq.getPageSize() == null) {
            assertImportReq.setPageSize(20);
        }
        Integer pageSize = assertImportReq.getPageSize();

        List<SuzakuImportBill> importBills = importBillMapper.selectByCondition(assertImportReq);
        PageData<SuzakuImportBill> pageData = new PageData<>();
        if(total > 0){
            int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
            pageData.setData(importBills);
            pageData.setPages(page);
            pageData.setTotal(total);
            pageData.setPageNum(assertImportReq.getOffset());
            pageData.setPageSize(assertImportReq.getPageSize());
        }
        return pageData;
    }

    //新增表单
    @Override
    @Transactional(rollbackFor = Exception.class)
    public SuzakuImportBill newBills(CreateBill bill) {
        SuzakuImportBill newbill = new SuzakuImportBill();

        BeanUtils.copyProperties(bill, newbill);
//        CreateBill cb = new CreateBill();
//        BeanUtils.copyProperties(bill, cb);
        //入库单号自动生成
        String id = UUIDUtil.getId();
        newbill.setImportBillNum(id);
        //设置待审核
        newbill.setApplyState(ImportState.WAITING_CHECKING.getValue());
        newbill.setImportState(ApplyState.WAITING_CHECKING.getValue());

        return newbill;
    }

    @Transactional
    @Override
    public SuzakuImportBill updateForCheck(SuzakuImportBill importBill, String chenckState) {

        String importBillNum = importBill.getImportBillNum();
        AssertImportReq assertImportReq = new AssertImportReq();
        assertImportReq.setImportBillNum(importBillNum);
        List<SuzakuImportBill> suzakuImportBills = importBillMapper.selectByCondition(assertImportReq);
        Long id = suzakuImportBills.get(0).getId();
        importBill.setId(id);
        importBill.setModifieDate(LocalDateTime.now());
        importBillMapper.updateById(importBill);

        //如果审核通过，更新资产明细状态
        if(chenckState.equals("pass")){changAssertState(importBillNum);}

        return importBill;
    }

    @Override
    public void init() {
        AssertImportReq req = new AssertImportReq();
        req.setImportState(2);

        List<SuzakuImportBill> suzakuImportBills = new ArrayList<>();
        List<String> importNills = new ArrayList<>();
        if (importBillMapper.selectTotal(req)>0){
            suzakuImportBills = importBillMapper.selectAll(req);
            suzakuImportBills.forEach(importBill -> {
                        AssertQo qo = new AssertQo();
                        //AssertSelectReqParam assertSelectReqParam = new AssertSelectReqParam();
                        List<String> importBills = qo.getImportBills() == null ? Lists.newArrayList() : qo.getImportBills();
                        // assertSelectReqParam.setImportBills(billNums);
                        importBills.add(importBill.getImportBillNum());
                        qo.setImportBills(importBills);

                        List<SuzakuImportAsserts> importAsserts = importAssertsMapper.selectAssertsList(qo);

                        List<SuzakuImportAsserts> asserts = importAsserts.stream().map(importAssert -> {
                            importAssert.setAssetsState(AssertState.State_1.getValue());
                            return importAssert;
                        }).collect(Collectors.toList());

                        component.updateBatch(asserts);
            });

        }

    }

    boolean changAssertState(String billNum){
        List<String> billNums = new ArrayList<>();
        billNums.add(billNum);

        AssertQo qo = new AssertQo();
        //AssertSelectReqParam assertSelectReqParam = new AssertSelectReqParam();
        List<String> importBills = qo.getImportBills() == null ? Lists.newArrayList() : qo.getImportBills();
       // assertSelectReqParam.setImportBills(billNums);
        importBills.add(billNum);
        qo.setImportBills(importBills);
        qo.setAssertsState(0);

        List<SuzakuImportAsserts> importAsserts = importAssertsMapper.selectAssertsList(qo);

        List<SuzakuImportAsserts> asserts = importAsserts.stream().map(importAssert -> {
            importAssert.setAssetsState(AssertState.State_1.getValue());
            return importAssert;
        }).collect(Collectors.toList());

        component.updateBatch(asserts);
        return asserts.size() > 0;
    }


    private String setBlliNum(){

        StringBuffer billNumStr = new StringBuffer(preStr);

        //处理年月
        String yearTemp = StringUtils.toString(DateUtils.getTodayYear());
        String year = yearTemp.substring(yearTemp.length() - 2);
        billNumStr.append(year);
        String month = StringUtils.toString(DateUtils.getTodayMonth());
        billNumStr.append(month);
        billNumStr.append("000");

        //递增数据
        LambdaQueryWrapper<SuzakuImportBill> queryWrapper = new LambdaQueryWrapper<>();
        Integer integer = importBillMapper.selectCount(queryWrapper);
        atomicInteger = new AtomicInteger(integer);
        int incre = atomicInteger.incrementAndGet();

        String increStr = StringUtils.toString(incre);
        if (increStr.length() < 3){
            //从指定索引替换0
            billNumStr.replace(billNumStr.length()-increStr.length()-1, billNumStr.length()-1,increStr);
        }

        return billNumStr.toString();
    }



}
