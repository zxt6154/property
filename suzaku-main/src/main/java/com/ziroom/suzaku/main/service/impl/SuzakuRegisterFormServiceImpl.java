package com.ziroom.suzaku.main.service.impl;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.AssertState;
import com.ziroom.suzaku.main.converter.ServiceConverter;
import com.ziroom.suzaku.main.dao.*;
import com.ziroom.suzaku.main.entity.*;
import com.ziroom.suzaku.main.enums.ApplyFormStatusEnum;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.suzaku.main.enums.ManageTypeEnum;
import com.ziroom.suzaku.main.event.AssertStatusRecordAsyncEvent;
import com.ziroom.suzaku.main.exception.SuzakuBussinesException;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextModel;
import com.ziroom.suzaku.main.message.service.WechatAppMsgSendService;
import com.ziroom.suzaku.main.model.dto.SuzakuApplyFormDto;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.param.ApplyFormAddReqParam;
import com.ziroom.suzaku.main.param.ApplyFormItemSelectReqParam;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.ApplyFormSelectReqParam;
import com.ziroom.suzaku.main.service.*;
import com.ziroom.suzaku.main.utils.IpUtils;
import com.ziroom.suzaku.main.utils.UserHolder;
import com.ziroom.tech.enums.ResponseEnum;
import com.ziroom.tech.queue.DelayQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资产领借登记
 * @author xuzeyu
 */
@Slf4j
@Service
public class SuzakuRegisterFormServiceImpl  implements SuzakuRegisterFormService {

    @Autowired
    private UserHolder userHolder;

    @Autowired
    private SuzakuSkuMapper suzakuSkuMapper;

    @Autowired
    private SuzakuRegisterFormMapper suzakuRegisterFormMapper;

    @Autowired
    private SuzakuImportBillMapper suzakuImportBillMapper;

    @Autowired
    private SuzakuClassifyCustomService suzakuClassifyCustomService;

    @Autowired
    private SuzakuImportAssertsMapper suzakuImportAssertsMapper;

    @Autowired
    private SuzakuRegisterFormItemMapper suzakuRegisterFormItemMapper;

    @Autowired
    private SuzakuAssertsLogService logService;

    @Autowired
    private WechatAppMsgSendService wechatAppMsgSendService;

    @Autowired
    private SuzakuImportAssertsService assertsService;

    @Value("${wechat.model.no1}")
    private String model;

    @Value("${send.notice.switch}")
    private boolean sendSwitch;

    @Value("${cmdb.url}")
    private String cmdbEmailUrl;

    @Autowired
    private SuzakuNoticeLogService noticeLogService;



    @Override
    public PageData<SuzakuApplyFormDto> page(ApplyFormSelectReqParam reqParam) {

        PageData<SuzakuApplyFormDto> pageData = new PageData<>();

        Integer pageSize = reqParam.getPageSize();
        int page = 0;

        //查询领借单是否有值
        Integer orderTotal = suzakuRegisterFormMapper.totalApplyForm(reqParam);

        if(orderTotal <= 0){
            return pageData;
        } else {
            //查询领借单
            List<String> orderIds = suzakuRegisterFormMapper.selAll(reqParam).stream().map(SuzakuRegisterFormEntity::getOrderId).collect(Collectors.toList());
            ApplyFormItemSelectReqParam applyFormItemSelectReqParam = new ApplyFormItemSelectReqParam();
            applyFormItemSelectReqParam.setAssertsCode(reqParam.getAssertsCode());
            applyFormItemSelectReqParam.setCategoryId(reqParam.getCategoryId());
            applyFormItemSelectReqParam.setSkuName(reqParam.getSkuName());
            applyFormItemSelectReqParam.setAssertsCode(reqParam.getAssertsCode());
            applyFormItemSelectReqParam.setOrderList(orderIds);
            applyFormItemSelectReqParam.setBrandName(reqParam.getBrandName());
            applyFormItemSelectReqParam.setCategoryId(reqParam.getCategoryId());
            applyFormItemSelectReqParam.setSupplierName(reqParam.getSupplierName());
            applyFormItemSelectReqParam.setIndex((reqParam.getOffset()-1)*pageSize);
            applyFormItemSelectReqParam.setPageSize(reqParam.getPageSize());

            Integer total = suzakuRegisterFormItemMapper.total(applyFormItemSelectReqParam);
            page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
            if(total <= 0){
                return pageData;
            } else {
                List<SuzakuRegisterFormItemEntity> itemsBySelectParam = suzakuRegisterFormItemMapper.getItemsBySelectParam(applyFormItemSelectReqParam);
                List<SuzakuApplyFormDto> suzakuApplyFormDtos = itemsBySelectParam.stream().map(m->{
                    SuzakuApplyFormDto suzakuApplyFormDto = new SuzakuApplyFormDto();

                    suzakuApplyFormDto.setOrderId(m.getRegisterFormEntity().getOrderId());
                    // suzakuApplyFormDto.setOrderType(applyForm.getOrderType()==1?"领用":"借用");
                    suzakuApplyFormDto.setOrderType(AssertState.getByCode(m.getRegisterFormEntity().getOrderType()).getLabel());
                    suzakuApplyFormDto.setStatus(ApplyFormStatusEnum.getType(m.getRegisterFormEntity().getStatus()));
                    suzakuApplyFormDto.setDesc(m.getRegisterFormEntity().getOrderDesc());
                    suzakuApplyFormDto.setReturnDate(m.getRegisterFormEntity().getReturnDate());
                    suzakuApplyFormDto.setUserName(m.getRegisterFormEntity().getUserName());
                    suzakuApplyFormDto.setUserNo(m.getRegisterFormEntity().getUserNo());
                    suzakuApplyFormDto.setDeparment(m.getRegisterFormEntity().getDeparment());
                    suzakuApplyFormDto.setOperateName(m.getRegisterFormEntity().getOperateName());
                    suzakuApplyFormDto.setManagerType(m.getRegisterFormEntity().getManagerType());
                    suzakuApplyFormDto.setManageDesc(ManageTypeEnum.getDesc(m.getRegisterFormEntity().getManagerType()));
                    suzakuApplyFormDto.setStationName(m.getRegisterFormEntity().getStationName());
                    suzakuApplyFormDto.setCreateName(m.getRegisterFormEntity().getCreateName());
                    suzakuApplyFormDto.setModitiyTime(m.getRegisterFormEntity().getModitiyTime());
                    suzakuApplyFormDto.setSkuName(m.getSkuName());
                    suzakuApplyFormDto.setAssertsCode(m.getAssertsCode());
                    suzakuApplyFormDto.setOldAssertsCode(m.getOldAssertsCode());
                    return suzakuApplyFormDto;
                }).collect(Collectors.toList());

                pageData.setData(suzakuApplyFormDtos);
                pageData.setPages(page);
                pageData.setTotal(total);
                pageData.setPageNum(reqParam.getOffset());
                pageData.setPageSize(reqParam.getPageSize());
            }
        }
        return pageData;
    }




    /**
     * 分页查询登记单
     */
    public PageData<SuzakuApplyFormDto> page2(ApplyFormSelectReqParam reqParam) {
        Integer pageSize = reqParam.getPageSize();
        Integer total = suzakuRegisterFormMapper.totalApplyForm(reqParam);
        int page = 0;
        List<SuzakuApplyFormDto> suzakuApplyFormDtos = Lists.newArrayList();
        if (total > 0) {
            List<SuzakuRegisterFormEntity> list = suzakuRegisterFormMapper.pageSkus(reqParam);

            //参数拼装
            Map<String, SuzakuRegisterFormEntity> applyFormMap = list.stream().collect(Collectors.toMap(SuzakuRegisterFormEntity::getOrderId, m -> m));
            ApplyFormItemSelectReqParam applyFormItemSelectReqParam = new ApplyFormItemSelectReqParam();
            applyFormItemSelectReqParam.setSkuName(reqParam.getSkuName());
            applyFormItemSelectReqParam.setAssertsCode(reqParam.getAssertsCode());
            applyFormItemSelectReqParam.setOrderList(new ArrayList<>(applyFormMap.keySet()));
            applyFormItemSelectReqParam.setBrandName(reqParam.getBrandName());
            applyFormItemSelectReqParam.setCategoryId(reqParam.getCategoryId());
            applyFormItemSelectReqParam.setSupplierName(reqParam.getSupplierName());
            applyFormItemSelectReqParam.setIndex((reqParam.getOffset()-1)*pageSize);
            applyFormItemSelectReqParam.setPageSize(reqParam.getPageSize());


            //查询条件过滤
            total = suzakuRegisterFormItemMapper.total(applyFormItemSelectReqParam);
            page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
            if(total>0){
                List<SuzakuRegisterFormItemEntity> applyItems = suzakuRegisterFormItemMapper.getItemsBySelectParam(applyFormItemSelectReqParam);
                suzakuApplyFormDtos = applyItems.stream().map(m->{
                    SuzakuApplyFormDto suzakuApplyFormDto = new SuzakuApplyFormDto();
                    SuzakuRegisterFormEntity applyForm = applyFormMap.get(m.getApplyOrderId());
                    suzakuApplyFormDto.setOrderId(applyForm.getOrderId());
                   // suzakuApplyFormDto.setOrderType(applyForm.getOrderType()==1?"领用":"借用");
                    suzakuApplyFormDto.setOrderType(AssertState.getByCode(applyForm.getOrderType()).getLabel());
                    suzakuApplyFormDto.setStatus(ApplyFormStatusEnum.getType(applyForm.getStatus()));
                    suzakuApplyFormDto.setDesc(applyForm.getOrderDesc());
                    suzakuApplyFormDto.setReturnDate(applyForm.getReturnDate());
                    suzakuApplyFormDto.setUserName(applyForm.getUserName());
                    suzakuApplyFormDto.setUserNo(applyForm.getUserNo());
                    suzakuApplyFormDto.setDeparment(applyForm.getDeparment());
                    suzakuApplyFormDto.setOperateName(applyForm.getOperateName());
                    suzakuApplyFormDto.setManagerType(applyForm.getManagerType());
                    suzakuApplyFormDto.setManageDesc(ManageTypeEnum.getDesc(applyForm.getManagerType()));
                    suzakuApplyFormDto.setStationName(applyForm.getStationName());
                    suzakuApplyFormDto.setCreateName(applyForm.getCreateName());
                    suzakuApplyFormDto.setModitiyTime(applyForm.getModitiyTime());
                    suzakuApplyFormDto.setSkuName(m.getSkuName());
                    suzakuApplyFormDto.setAssertsCode(m.getAssertsCode());
                    suzakuApplyFormDto.setOldAssertsCode(m.getOldAssertsCode());
                    return suzakuApplyFormDto;
                }).collect(Collectors.toList());

            }
        }

        PageData<SuzakuApplyFormDto> pageData = new PageData<>();
        pageData.setData(suzakuApplyFormDtos);
        pageData.setPages(page);
        pageData.setTotal(total);
        pageData.setPageNum(reqParam.getOffset());
        pageData.setPageSize(reqParam.getPageSize());
        return pageData;
    }

    /**
     * 分页查询资产信息
     */
    @Override
    public PageData<SuzakuAssertDto> pageGetLeaveAssert(AssertSelectReqParam assertSelectReqParam) {
        Integer pageSize = assertSelectReqParam.getPageSize();

        //查询入库单审核通过的
        List<SuzakuImportBill> importBills = suzakuImportBillMapper.getListByType(assertSelectReqParam.getType());
        if(CollectionUtils.isEmpty(importBills)){
            return new PageData<>();
        }

        List<String> importBillIds = importBills.stream().map(SuzakuImportBill::getImportBillNum).collect(Collectors.toList());
        assertSelectReqParam.setImportBills(importBillIds);

        //属性组拼装
        if(Objects.nonNull(assertSelectReqParam.getAttributes()) && !assertSelectReqParam.getAttributes().isEmpty()){
            //拼装规格描述
            List<String> attributeStrList = Lists.newArrayList();
            List<SuzakuClassifyCustomDto> classifyCustoms = suzakuClassifyCustomService.getClassifyCustomByClassifyId(assertSelectReqParam.getCategory());
            Map<Long, String> customMap = classifyCustoms.stream().collect(Collectors.toMap(SuzakuClassifyCustomDto::getId, SuzakuClassifyCustomDto::getCustomName));
            assertSelectReqParam.getAttributes().forEach((k,v)->{
                StringBuilder attributeStr = new StringBuilder();
                attributeStrList.add(attributeStr.append(customMap.get(Long.parseLong(k))).append(":").append(v).toString());
            });

            String attributrStr = StringUtils.join(attributeStrList, ",");
            assertSelectReqParam.setAttributeStr(attributrStr);
        }

        Integer total = suzakuImportAssertsMapper.total(assertSelectReqParam);
        int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List<SuzakuAssertDto> assertDtos = Lists.newArrayList();
        if (total > 0) {
            //查询闲置资产
            List<SuzakuImportAsserts> asserts = suzakuImportAssertsMapper.getPageAssertsByBillIds(assertSelectReqParam);
            List<String> skuIdList = asserts.stream().map(SuzakuImportAsserts::getSku).collect(Collectors.toList());
            List<SuzakuSkuEntity> skuInfoList = suzakuSkuMapper.getSkuInfoList(skuIdList);
            Map<String, SuzakuSkuEntity> skuInfoMap = skuInfoList.stream().collect(Collectors.toMap(SuzakuSkuEntity::getSkuId, a -> a));
            assertDtos = asserts.stream().map(m->{
               SuzakuAssertDto suzakuAssertDto = new SuzakuAssertDto();
               suzakuAssertDto.setAssertCode(m.getAssertsCode());
               suzakuAssertDto.setOldAssertCode(m.getOldAssertsCode());
               suzakuAssertDto.setSkuId(m.getSku());
               suzakuAssertDto.setSN(m.getSnCode());
               suzakuAssertDto.setMac(m.getMacImic());
               suzakuAssertDto.setPurchasePrice(m.getPurchasePrice());
               suzakuAssertDto.setSkuName(m.getSkuName());
               suzakuAssertDto.setSkuDesc(skuInfoMap.get(m.getSku()).getSkuDesc());
               suzakuAssertDto.setCategoryId(m.getCategoryId());
               suzakuAssertDto.setCategoryName(m.getCategory());
               suzakuAssertDto.setBrandName(m.getBrandName());
               suzakuAssertDto.setSupplierName(m.getSupplierName());
               suzakuAssertDto.setSkuType(skuInfoMap.get(m.getSku()).getSkuType());
               suzakuAssertDto.setUnit(skuInfoMap.get(m.getSku()).getUnit());
               suzakuAssertDto.setPeriod(skuInfoMap.get(m.getSku()).getPeriod());
               suzakuAssertDto.setThreshold(skuInfoMap.get(m.getSku()).getThreshold());
               suzakuAssertDto.setAttributeDesc(m.getAttributeStr());
               suzakuAssertDto.setUsePeople(m.getUsePeople());
               suzakuAssertDto.setUseDepartment(m.getUseDepartment());
               return suzakuAssertDto;
           }).collect(Collectors.toList());

        }

        PageData<SuzakuAssertDto> pageData = new PageData<>();
        pageData.setData(assertDtos);
        pageData.setPages(page);
        pageData.setTotal(total);
        pageData.setPageNum(assertSelectReqParam.getOffset());
        pageData.setPageSize(assertSelectReqParam.getPageSize());
        return pageData;
    }

    /**
     * 资产登记
     */
    @Transactional
    @Override
    public void assertAdd(ApplyFormAddReqParam applyFormAddReqParam) {
        if(CollectionUtils.isEmpty(applyFormAddReqParam.getAssertModels())){
            throw new SuzakuBussinesException(ResponseEnum.ERROR_CODE.getCode(), "您未选择资产,请选择资产后提交");
        }
        //转换
        SuzakuRegisterFormEntity registerFormEntity = ServiceConverter.suzakuApplyForm2ApplyEntity().apply(applyFormAddReqParam);
        List<SuzakuRegisterFormItemEntity> registerFormItemEntities = applyFormAddReqParam.getAssertModels().stream().map(ServiceConverter.suzakuApplyFormItem2ApplyItemEntity()).collect(Collectors.toList());
        registerFormItemEntities.forEach(m->m.setApplyOrderId(registerFormEntity.getOrderId()));

        //登记单保存
        suzakuRegisterFormMapper.insert(registerFormEntity);
        suzakuRegisterFormItemMapper.insert(registerFormItemEntities);
        //更新资产状态
        List<SuzakuImportAsserts> assertEntityList = ServiceConverter.suzakuApplyForm2AssertEntity().apply(applyFormAddReqParam);
        suzakuImportAssertsMapper.batchUpdate(assertEntityList);


        applyFormAddReqParam.getAssertModels().forEach(m -> {
            insertLog(m);
            sendWeChatNotice(applyFormAddReqParam,m);
//            DelayQueueUtil.getInstance().put(new AssertStatusRecordAsyncEvent(DelayJobEnum.ASYNC_APPLYFORM_EVENT.getType(), assertsLog));
        });
    }

    /**
     * 记录行为日志
     * @param
     */
    public void insertLog(ApplyFormAddReqParam.AssertModel m){
        SuzakuAssertsLog assertsLog = new SuzakuAssertsLog();
        assertsLog.setOperator(userHolder.getUserInfo().getName());
        assertsLog.setAssertsCode(m.getAssertCode());
        assertsLog.setAssertsName(m.getSkuName());
        Optional<String> localIp = Optional.ofNullable(IpUtils.getLocalIp());
        localIp.ifPresent(assertsLog::setOperatorIp);
        assertsLog.setRemark("资产领用登记，设备状态：在用");
        logService.saveSuzakuAssertsLog(assertsLog);
    }

    /**
     * 领用设备发送企业微信通知
     * @param applyFormAddReqParam
     * @param m
     */
    public void sendWeChatNotice(ApplyFormAddReqParam applyFormAddReqParam, ApplyFormAddReqParam.AssertModel m){
        HashMap<String,String> data = new HashMap<>();
        LambdaQueryWrapper<SuzakuImportAsserts> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuImportAsserts::getAssertsCode,m.getAssertCode());
        SuzakuImportAsserts asserts = assertsService.getOne(queryWrapper);
        String name = applyFormAddReqParam.getUserName().substring(0, applyFormAddReqParam.getUserName().indexOf("("));
        data.put("userName",name);
        data.put("email",applyFormAddReqParam.getUserName());
        data.put("brandName",m.getBrandName());
        data.put("skuType",asserts.getSkuType());
        data.put("attributeStr",asserts.getAttributeStr());
        data.put("assertsCode",m.getAssertCode());
        data.put("snCode",asserts.getSnCode());
        data.put("type", String.valueOf(applyFormAddReqParam.getType()));
        WechatMsgAppTextModel wechatMsgAppTextModel = new WechatMsgAppTextModel();
        wechatMsgAppTextModel.setData(data);
        String toUser = applyFormAddReqParam.getUserName().substring(0, applyFormAddReqParam.getUserName().indexOf("@"));
        int index= toUser.indexOf("(");
        String to = toUser.substring(index+1);
        wechatMsgAppTextModel.setToUser(Collections.singletonList(to));
        wechatMsgAppTextModel.setModelCode(model);
        if (sendSwitch){
            wechatAppMsgSendService.textMsgSend(wechatMsgAppTextModel);
            saveNoticeLog(name,applyFormAddReqParam,m,wechatMsgAppTextModel);
            sendEmail(wechatMsgAppTextModel);
        }
    }

    /**
     * 保存通知日志
     * @param name
     * @param applyFormAddReqParam
     * @param m
     * @param wechatMsgAppTextModel
     */
    public void saveNoticeLog(String name, ApplyFormAddReqParam applyFormAddReqParam, ApplyFormAddReqParam.AssertModel m, WechatMsgAppTextModel wechatMsgAppTextModel){
        SuzakuNoticeLog log = new SuzakuNoticeLog();
        log.setAssertcode(m.getAssertCode());
        log.setAssertname(m.getSkuName());
        log.setBrandname(m.getBrandName());
        log.setUsername(name);
        log.setType(String.valueOf(applyFormAddReqParam.getType()));
        log.setUserInfo(applyFormAddReqParam.getUserName());
        log.setCreateUser(userHolder.getUserInfo().getName());
        log.setContent(JSON.toJSONString(wechatMsgAppTextModel));
        log.setCreateTime(LocalDateTime.now());
        log.setUpdateTime(LocalDateTime.now());
        noticeLogService.save(log);
    }

    /**
     * 发送邮件
     * @param wechatMsgAppTextModel
     */
    private void sendEmail(WechatMsgAppTextModel wechatMsgAppTextModel) {
        try {
            String body = HttpRequest.post(cmdbEmailUrl)
                    .header("Content-Type", "application/json")
                    .body(JSON.toJSONString(wechatMsgAppTextModel))
                    .execute()
                    .body();
            log.info("发送结果：" + JSON.toJSONString(body));
        } catch (HttpException e) {
            log.error("发送邮件失败" + e.getMessage());
        }
    }
}
