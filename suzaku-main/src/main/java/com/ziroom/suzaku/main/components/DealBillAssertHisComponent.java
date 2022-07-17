package com.ziroom.suzaku.main.components;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.AssertState;
import com.ziroom.suzaku.main.constant.enums.DealState;
import com.ziroom.suzaku.main.constant.enums.DealType;
import com.ziroom.suzaku.main.entity.SuzakuAssertsLog;
import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.model.qo.HisShowQo;
import com.ziroom.suzaku.main.model.vo.DealBillAssertsVo;
import com.ziroom.suzaku.main.service.*;
import com.ziroom.suzaku.main.utils.IpUtils;
import com.ziroom.suzaku.main.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DealBillAssertHisComponent {

    @Autowired
    private SuzakuDealHistoryService historyService;

    @Autowired
    private SuzakuDealBillService dealBillService;

    @Autowired
    private SuzakuImportAssertsService importAssertsService;

    @Autowired
    private AssertsSkuService assertsSkuService;

    @Autowired
    private UserHolder userHolder;

    @Autowired
    private SuzakuAssertsLogService logService;

    final static String remarkMesCreate = "资产处置登记，设备状态：处置中";

    final static String remarkMesPass = "处置审核通过 设备状态： ";

    final static String remarkMesRejected = "处置审核驳回";


    @Transactional(rollbackFor = Exception.class)
    public void createDealBill(DealBillAssertsVo dealBillAssertsVo){

        //添加处置单
        String dealNumStr = dealBillService.add(dealBillAssertsVo);
        //添加历史数据表
        historyService.saveWhileCreateDealBill(dealNumStr, dealBillAssertsVo.getAsserts());
        //更新资产状态
        dealBillAssertsVo.getAsserts().forEach(v->{
            LambdaQueryWrapper<SuzakuImportAsserts> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SuzakuImportAsserts::getAssertsCode, v.getAssertsCode());
            Optional<SuzakuImportAsserts> serviceOne = Optional.ofNullable(importAssertsService.getOne(wrapper));
            serviceOne.ifPresent(asserts -> {
                asserts.setAssetsState(AssertState.State_6.getValue());
                asserts.setUpdateTime(LocalDateTime.now());
                asserts.setGroupId(2);
                asserts.setOperator(userHolder.getUserInfo().getName());
                importAssertsService.updateById(asserts);
            });
        });
        //记录日志
        insertLog(dealBillAssertsVo.getAsserts(), remarkMesCreate);
    }

    /**
     * 记录行为日志
     * @param asserts
     */
    private void insertLog(List<SuzakuImportAsserts> asserts, String remarkMes){
           asserts.forEach(as -> {
               SuzakuAssertsLog assertsLog = new SuzakuAssertsLog();
               assertsLog.setOperator(userHolder.getUserInfo().getName());
               assertsLog.setAssertsCode(as.getAssertsCode());
               assertsLog.setAssertsName(as.getSkuName());
               Optional<String> localIp = Optional.ofNullable(IpUtils.getLocalIp());
               localIp.ifPresent(assertsLog::setOperatorIp);
               assertsLog.setRemark(remarkMes);
               logService.saveSuzakuAssertsLog(assertsLog);
           });
    }


    @Transactional(rollbackFor = Exception.class)
    public String checkPass(SuzakuDealBill suzakuDealBill){
        List<SuzakuImportAsserts> assertsList = forCheck(suzakuDealBill);
        //需要做事务处理！
        //审核通过则 更新资产状态
        Integer dealState = suzakuDealBill.getDealType();
        String label = DealType.getByCode(dealState).getLabel();
        assertsList.forEach(v->{
            LambdaQueryWrapper<SuzakuImportAsserts> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SuzakuImportAsserts::getAssertsCode, v.getAssertsCode());
            Optional<SuzakuImportAsserts> serviceOne = Optional.ofNullable(importAssertsService.getOne(wrapper));
            serviceOne.ifPresent(asserts -> {
                asserts.setAssetsState(AssertState.getByLabel(label).getValue());
                asserts.setUpdateTime(LocalDateTime.now());
                asserts.setOperator(userHolder.getUserInfo().getName());
                importAssertsService.updateById(asserts);
            });
        });
        String mes = " ";
        dealBillService.checkPass(suzakuDealBill);
        String dealNum = suzakuDealBill.getDealNum();
        boolean check = historyService.check(dealNum, DealState.PASS.getValue());
        if (check == true){
            mes = dealBillService.checkPass(suzakuDealBill);
            this.insertLog(assertsList, remarkMesPass+DealType.getByCode(suzakuDealBill.getDealType()));
        }
        return mes;
    }

    @Transactional(rollbackFor = Exception.class)
    public String checkReject(SuzakuDealBill suzakuDealBill){

        List<SuzakuImportAsserts> assertsList = forCheck(suzakuDealBill);
        //审核驳回，闲置
        assertsList.forEach(v->{
            LambdaQueryWrapper<SuzakuImportAsserts> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SuzakuImportAsserts::getAssertsCode, v.getAssertsCode());
            Optional<SuzakuImportAsserts> serviceOne = Optional.ofNullable(importAssertsService.getOne(wrapper));
            serviceOne.ifPresent(asserts -> {
                asserts.setAssetsState(AssertState.State_1.getValue());
                asserts.setUpdateTime(LocalDateTime.now());
                asserts.setOperator(userHolder.getUserInfo().getName());
                importAssertsService.updateById(asserts);
            });
        });

        String mes = " ";
        String dealNum = suzakuDealBill.getDealNum();
        boolean check = historyService.check(dealNum, DealState.REJECT.getValue());
        if (check == true){
            mes = dealBillService.checkReject(suzakuDealBill);
            insertLog(assertsList, remarkMesRejected);
        }
        return mes;
    }

    public PageData<SuzakuImportAsserts> show(HisShowQo hisShowQo){
        List<String> assertsCodes = historyService.selAssrtsCode(hisShowQo);
        AssertsSkuQo assertsSku = new AssertsSkuQo();
        assertsSku.setAssertsCodes(assertsCodes);
        assertsSku.setPageSize(hisShowQo.getPageSize());
        return assertsSkuService.getAssertList(assertsSku);
    }

    /**
     * 查询处置单对应的资产
     * @param dealBill
     * @return
     */
    public List<SuzakuImportAsserts> forCheck(SuzakuDealBill dealBill){
        HisShowQo qo = new HisShowQo();
        qo.setDealBill(dealBill);
        List<String> assertsCodes = historyService.selAssrtsCode(qo);
        if (CollectionUtil.isEmpty(assertsCodes)){
            return new ArrayList<>();
        }
        List<SuzakuImportAsserts> assertsList =  new ArrayList<>();
        assertsCodes.forEach(v ->{
            LambdaQueryWrapper<SuzakuImportAsserts> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SuzakuImportAsserts::getAssertsCode, v);
            SuzakuImportAsserts serviceOne = importAssertsService.getOne(wrapper);
            assertsList.add(serviceOne);
        });
        return assertsList;
    }

    public void updateBatch(List<SuzakuImportAsserts> asserts){
        importAssertsService.updateBatch(asserts);
    }

}
