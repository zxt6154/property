package com.ziroom.suzaku.main.listeners;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.components.EhrComponent;
import com.ziroom.suzaku.main.config.OperatorContext;
import com.ziroom.suzaku.main.constant.enums.AssertState;
import com.ziroom.suzaku.main.constant.enums.ManagerType;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.dao.SuzakuImportBillMapper;
import com.ziroom.suzaku.main.dao.SuzakuRegisterFormItemMapper;
import com.ziroom.suzaku.main.dao.SuzakuRegisterFormMapper;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.entity.SuzakuRegisterFormEntity;
import com.ziroom.suzaku.main.entity.SuzakuRegisterFormItemEntity;
import com.ziroom.suzaku.main.enums.ApplyFormStatusEnum;
import com.ziroom.suzaku.main.model.dto.req.BorrowRecipientExcelReq;
import com.ziroom.suzaku.main.model.dto.req.EhrEmpListReq;
import com.ziroom.suzaku.main.model.dto.resp.UserResp;
import com.ziroom.suzaku.main.utils.StringUtils;
import com.ziroom.suzaku.main.utils.UUIDUtil;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BorrowOrRecipientListener extends AnalysisEventListener<BorrowRecipientExcelReq> {

    @Autowired
    private SuzakuImportAssertsMapper importAssertsMapper;

    @Autowired
    private SuzakuImportBillMapper importBillMapper;

    @Autowired
     private SuzakuRegisterFormItemMapper registerFormItemMapper;

    @Autowired
    private SuzakuRegisterFormMapper registerFormMapper;

    @Getter
    private List<BorrowRecipientExcelReq> listTrue;
    @Getter
    private List<BorrowRecipientExcelReq> listFail;

    @Getter
    private Set<String> setAssertCode;


    @Resource
    private EhrComponent ehrComponent;

    private final static String FORMAT_DATETIME_2 = "yyyy-MM-dd HH:mm:ss";

     public void init(){
         this.listTrue = Lists.newArrayList();
         this.listFail = Lists.newArrayList();
         this.setAssertCode = new HashSet<>();
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if(listTrue.size() > 0){
            //批量更新资产
            List<SuzakuRegisterFormItemEntity> rfItems = listTrue.stream().map(trueData -> {
                String assertsCode = trueData.getAssertsCode();
                SuzakuImportAsserts entity = importAssertsMapper.selByCode(assertsCode);
                Integer value = AssertState.getByLabel(trueData.getBorrowerOrRecipient()).getValue();
                //
                String borrowerRecipientUid = trueData.getBorrowerRecipientUid();
                SuzakuRegisterFormEntity registerFormEntity = this.combineRegisterForm(entity);
                registerFormEntity.setOrderId(value == 3 ? "LY" + UUIDUtil.getUID() : "JY" + UUIDUtil.getUID());
                registerFormEntity.setOrderType(value);
                registerFormEntity.setOrderDesc(trueData.getReason());
                registerFormEntity.setReturnDate(trueData.getReturnDate());
                registerFormEntity.setUserNo(borrowerRecipientUid);

                registerFormEntity.setManagerType(trueData.getManageTypeId());
                String strUid = "[" + borrowerRecipientUid +"," + OperatorContext.getOperator() + "]";

                //excel表格uid对应的人
                String center = ehrComponent.getEhrUserDetail(strUid).get(0).getCenter();
                String name1 = ehrComponent.getEhrUserDetail(strUid).get(0).getName();
                String email1 = ehrComponent.getEhrUserDetail(strUid).get(0).getEmail();

                //操作人
                String name2 = ehrComponent.getEhrUserDetail(strUid).get(1).getName();

                registerFormEntity.setDeparment(center);
                registerFormEntity.setUserName(name1 + "(" + email1 + ")") ;
                registerFormEntity.setOperateName(name2);
                //
                SuzakuRegisterFormItemEntity registerFormItemEntity = this.combineRegisterFormItem(entity);
                registerFormItemEntity.setApplyOrderId(registerFormEntity.getOrderId());
                registerFormMapper.insert(registerFormEntity);
                entity.setAssetsState(value);
                entity.setUsePeople(name1 + "(" + email1 + ")");
                entity.setUseDepartment(center);
                entity.setGroupId(1);
                importAssertsMapper.updateById(entity);

                return registerFormItemEntity;
            }).collect(Collectors.toList());
            //批量插入
            registerFormItemMapper.insert(rfItems);
        }
    }

    SuzakuRegisterFormItemEntity combineRegisterFormItem(SuzakuImportAsserts entity){

        SuzakuRegisterFormItemEntity registerFormItemEntity = new SuzakuRegisterFormItemEntity();

        registerFormItemEntity.setAssertsCode(entity.getAssertsCode());
        registerFormItemEntity.setBrandName(entity.getBrandName());
        registerFormItemEntity.setCategoryId(entity.getCategoryId());
        registerFormItemEntity.setCategory(entity.getCategory());
        registerFormItemEntity.setSkuName(entity.getSkuName());
        registerFormItemEntity.setSupplierName(entity.getSupplierName());
        registerFormItemEntity.setOldAssertsCode(entity.getOldAssertsCode());
        registerFormItemEntity.setSkuId(entity.getSku());
        registerFormItemEntity.setCreateName(LocalDateTimeUtil.format(LocalDateTime.now(), FORMAT_DATETIME_2));

        return registerFormItemEntity;

        // registerFormMapper.insert(registerFormEntity);
    }

    SuzakuRegisterFormEntity combineRegisterForm(SuzakuImportAsserts entity){

        SuzakuRegisterFormEntity registerFormEntity = new SuzakuRegisterFormEntity();
        registerFormEntity.setStationName(entity.getStationName());
        registerFormEntity.setStationNo(entity.getStationNo());
        registerFormEntity.setStatus(ApplyFormStatusEnum.REGISTER_CONFIRED.getType());
        //创建时间
        registerFormEntity.setCreateName(LocalDateTimeUtil.format(LocalDateTime.now(), FORMAT_DATETIME_2));
        return registerFormEntity;
    }

    @Override
    public void invoke(BorrowRecipientExcelReq data, AnalysisContext context) {

        Map<String, String> lists = new HashMap<>();
        lists.put("assertsCode", data.getAssertsCode());
        lists.put("manageType", data.getManageType());
        lists.put("borrowerRecipientUid", data.getBorrowerRecipientUid());
        lists.put("borrowerOrRecipient", data.getBorrowerOrRecipient());
        lists.put("date", data.getDate());
        lists.put("reason", data.getReason());

        String failInfos = this.checkNotNull(lists);
        if(data.getBorrowerOrRecipient().equals("借用")&&StringUtils.isBlank(data.getReturnDate())){
            data.setFailInfo("借用资产必须填写归还时间 -15天/30天/长期");
            listFail.add(data);
        } else {
            //判断是否有为空的必填项
            if(!failInfos .equals("null")){
                data.setFailInfo(failInfos);
                listFail.add(data);
            } else { //没有则判断是否有重复assertCode
                this.ifRepeated(data);
            }
        }

    }

    String checkNotNull(Map<String, String> maps){

        StringBuilder sb = new StringBuilder("null");
        maps.forEach((field, value) -> {

            if(StringUtils.isBlank(value)){
                sb.append(field + "不能为空");
            }
        });
        return sb.toString();
    }

    //判断是否有重复数据
    void ifRepeated(BorrowRecipientExcelReq data){
                if(setAssertCode.size() == 0){
                    setAssertCode.add(data.getAssertsCode());
                    this.checkIfAssertsCode(data);
                } else {
                    if(setAssertCode.contains(data.getAssertsCode())){
                        data.setFailInfo("资产条码重复！");
                        listFail.add(data);
                    } else {
                       this.checkIfAssertsCode(data);
                    }
                }
    }

    //检验资产条码信息正确与否
    void checkIfAssertsCode(BorrowRecipientExcelReq data){

        SuzakuImportAsserts assertsList = importAssertsMapper.selByCode(data.getAssertsCode());
            //此assertsCode不存在
            if(StringUtils.isEmpty(assertsList)){
                data.setFailInfo("此资产条码不存在！");
                listFail.add(data);
            } else { //如果此条资产条码存在
                checkIfUnused(assertsList, data);
            }
    }

    //是否闲置
    void checkIfUnused(SuzakuImportAsserts importAssert, BorrowRecipientExcelReq data){

        Integer assetsState = importAssert.getAssetsState();
        //如果非闲置
            if(assetsState != 1){
                data.setFailInfo("此资产非闲置！");
                listFail.add(data);
            } else {
                this.checkIfMatch(importAssert, data);
            }
    }

    //是否匹配管理类型
    void checkIfMatch(SuzakuImportAsserts importAssert, BorrowRecipientExcelReq data) {
        SuzakuImportBill importBill = importBillMapper.getByNum(importAssert.getImportBillNum());

        Integer manageId = ManagerType.getByLabel(data.getManageType()) == null ? null : ManagerType.getByLabel(data.getManageType()).getValue();

        data.setManageTypeId(manageId);

        if (manageId == null || importBill.getManagerType() != data.getManageTypeId()) {  //&& listByType != null     如何入库单的管理类型能对应起来
            data.setFailInfo("此条信息管理类型与原资产条码管理类型不匹配！");
            listFail.add(data);
        } else {
            //校验门店工作站
              this.checkIfMatchStation(data, importBill, importAssert);
        }


    }

    //是否匹配门店/工作站
    void checkIfMatchStation(BorrowRecipientExcelReq data, SuzakuImportBill importBill, SuzakuImportAsserts importAssert) {
            if(importBill.getManagerType() == 2 || importBill.getManagerType() == 3){ //门店工作站必须有值
                String station = data.getStation();
                String stationName = importAssert.getStationName();
                if(StringUtils.isBlank(data.getStation()) || !( station.equals(stationName))){
                    data.setFailInfo("此条信息门店/工作站与原门店/工作站不匹配！");
                    listFail.add(data);
                } else {
                    listTrue.add(data);
                }
            } else {
                if(StringUtils.isNotBlank(data.getStation())){
                    data.setFailInfo("此条信息门店/工作站与原门店/工作站不匹配！");
                    listFail.add(data);
                } else {
                    this.checkIfInEhr(data);
                }
            }
    }

    void checkIfInEhr(BorrowRecipientExcelReq data){

        EhrEmpListReq req = new EhrEmpListReq();
        req.setEmpCodeNameAdcode(data.getBorrowerRecipientUid());
        List<UserResp> empList = ehrComponent.getEmpList(req);
        if(empList == null || empList.size() == 0 || StringUtils.isEmpty(empList.get(0))){
            data.setFailInfo("查无此人！");
            listFail.add(data);
        } else {
             data.setBorrowerRecipientUid(empList.get(0).getCode());
             this.checkRb(data);
        }

    }

    void checkRb(BorrowRecipientExcelReq data){

            Integer value = AssertState.getByLabel(data.getBorrowerOrRecipient()) == null ? null : AssertState.getByLabel(data.getBorrowerOrRecipient()).getValue();
            if(StringUtils.isEmpty(value)){
                data.setFailInfo("领用借用字段出错。示例: 领用/借用");
                listFail.add(data);
            } else {
                listTrue.add(data);
            }
    }

}
