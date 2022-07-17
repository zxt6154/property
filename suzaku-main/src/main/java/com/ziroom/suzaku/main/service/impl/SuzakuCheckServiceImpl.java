package com.ziroom.suzaku.main.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.*;
import com.ziroom.suzaku.main.dao.*;
import com.ziroom.suzaku.main.entity.*;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuCheckDto;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.model.po.SuzakuCheckPo;
import com.ziroom.suzaku.main.model.qo.CheckQo;
import com.ziroom.suzaku.main.model.vo.SuzakuCheckItemVo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.LaunchCheckReqParam;
import com.ziroom.suzaku.main.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.ziroom.suzaku.main.constant.SuzakuConstant.CHECK;

/**
 * <p>
 * 盘点任务 服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
@Service
@Slf4j
public class SuzakuCheckServiceImpl extends ServiceImpl<SuzakuCheckMapper, SuzakuCheck> implements SuzakuCheckService {

    @Autowired
    private UserHolder userHolder;

    @Resource
    private SuzakuCheckMapper checkMapper;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private SuzakuCheckItemService suzakuRemandItem;

    @Autowired
    private SuzakuDepotService suzakuDepotService;

    @Autowired
    private SuzakuStationService suzakuStationService;


    @Autowired
    private SuzakuClassifyCustomService suzakuClassifyCustomService;

    @Resource
    private  SuzakuCheckItemMapper checkItemMapper;

    @Resource
    private SuzakuImportBillMapper suzakuImportBillMapper;

    @Resource
    private SuzakuImportAssertsMapper suzakuImportAssertsMapper;

    @Resource
    private SuzakuSkuMapper suzakuSkuMapper;

    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";


    @Override
    public PageData<SuzakuCheckDto> pageCheck(CheckQo qo) {
        if (qo.getOffset() == null) {
            qo.setOffset(1);
        }
        if (qo.getPageSize() == null) {
            qo.setPageSize(20);
        }
        Page<SuzakuCheck> page = new Page<>(qo.getOffset(), qo.getPageSize());
        if (StrUtil.isNotEmpty(qo.getStartTime())){
            String startTime = DateUtils.timeStampDate(qo.getStartTime());
            qo.setStartTime(startTime);
        }
        if (StrUtil.isNotEmpty(qo.getEndTime())){
            String endTime = DateUtils.timeStampDate(qo.getEndTime());
            qo.setEndTime(endTime);
        }
        IPage<SuzakuCheckPo> iPage = checkMapper.selectCheckPage(page,qo);
        PageData<SuzakuCheckDto> suzakuRemandDtoPageData = DozerUtil.mapPageData(iPage, SuzakuCheckDto.class, beanMapper);
        if (CollectionUtil.isNotEmpty(suzakuRemandDtoPageData.getData())){
            suzakuRemandDtoPageData.getData().forEach(v->{
                if (StrUtil.isNotEmpty(v.getCheckStatus())){
                    Optional<CheckState> checkState = Optional.ofNullable(CheckState.getByCode(Integer.valueOf(v.getCheckStatus())));
                    checkState.ifPresent(state -> v.setCheckStatus(state.getLabel()));
                }
                if (StrUtil.isNotEmpty(v.getCheckType())){
                    Optional<CheckType> checkType = Optional.ofNullable(CheckType.getByCode(Integer.valueOf(v.getCheckType())));
                    checkType.ifPresent(type -> v.setCheckType(type.getLabel()));
                }
                if (StrUtil.isNotEmpty(v.getCheckDepot())){
                    SuzakuDepot byId = suzakuDepotService.getById(v.getCheckDepot());
                    v.setCheckDepotName(byId.getDepotName());
                }
                if (null != v.getManageType()){
                    Optional<ManagerType> managerType = Optional.ofNullable(ManagerType.getByCode(v.getManageType()));
                    managerType.ifPresent(manager -> v.setManageTypeName(manager.getLabel()));
                }

            });
        }
        return suzakuRemandDtoPageData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void launchCheck(LaunchCheckReqParam launchCheckReqParam) {
        if(StrUtil.isNotEmpty(launchCheckReqParam.getId())){
            updateCheck(launchCheckReqParam);
            return;
        }
        SuzakuCheck check = new SuzakuCheck();
        check.setCheckId(CHECK + UUIDUtil.getUID());
        check.setCheckStatus(CheckState.NOTSTARTED.getValue());
        check.setDepartment(launchCheckReqParam.getDepartment());
        check.setCheckName(launchCheckReqParam.getCheckName());
        check.setDepartment(launchCheckReqParam.getDepartment());
        check.setCategoryId(launchCheckReqParam.getCategoryId());
        check.setManageType(launchCheckReqParam.getManageType());
        check.setCheckDepot(launchCheckReqParam.getCheckDepot());
        check.setStoreName(launchCheckReqParam.getStoreCode());
        if (StrUtil.isNotEmpty(launchCheckReqParam.getStoreCode())){
            LambdaQueryWrapper<SuzakuStation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SuzakuStation::getStationName,launchCheckReqParam.getStoreCode());
            SuzakuStation suzakuStation = suzakuStationService.getOne(queryWrapper);
            check.setStoreCode(String.valueOf(suzakuStation.getId()));
        }
        String startTime = DateUtils.timeStampDate(launchCheckReqParam.getStartTime());
        check.setStartTime(DateUtils.stringToLocalDateTime(startTime));
        String endTime = DateUtils.timeStampDate(launchCheckReqParam.getEndTime());
        check.setEndTime(DateUtils.stringToLocalDateTime(endTime));
        check.setCheckType(CheckType.MINGPAN.getValue());
        check.setCreateUser(userHolder.getUserInfo().getName());
        check.setCreateTime(LocalDateTime.now());
        check.setUpdateTime(LocalDateTime.now());
        checkMapper.insert(check);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                if (CollectionUtil.isNotEmpty(launchCheckReqParam.getCheckItemVos())){
                    createCheckItem(launchCheckReqParam.getCheckItemVos());
                }
            }
            private void createCheckItem(List<SuzakuCheckItemVo> checkItemVos) {
                checkItemVos.forEach(v->{
                    SuzakuCheckItem checkItem = new SuzakuCheckItem();
                    checkItem.setCheckId(check.getCheckId());
                    checkItem.setAssertCode(v.getAssertCode());
                    checkItem.setSkuName(v.getSkuName());
                    if (StrUtil.isEmpty(v.getUsePeople())){
                        checkItem.setUsePeople(userHolder.getUserInfo().getName());
                    }else {
                        checkItem.setUsePeople(v.getUsePeople());
                    }
                    checkItem.setStaffStatus(StaffCheckState.NOTMADE.getValue());
                    checkItem.setAdminStatus(AdminCheckState.UNTREATED.getValue());
                    checkItem.setCheckDepartment(v.getCheckDepartment());
                    checkItem.setCreateUser(userHolder.getUserInfo().getName());
                    checkItem.setCreateTime(LocalDateTime.now());
                    checkItem.setUpdateTime(LocalDateTime.now());
                    suzakuRemandItem.save(checkItem);
                });
                if (StrUtil.isNotEmpty(launchCheckReqParam.getCategoryId())){
                    check.setCategoryName(checkItemVos.get(0).getCategoryName());
                }
                check.setBeforeQuantity(checkItemVos.size());
                checkMapper.updateById(check);
            }
        });
    }

    /**
     * 分页查询资产信息
     */
    @Override
    public PageData<SuzakuAssertDto> pageGetAssert(AssertSelectReqParam assertSelectReqParam) {
        Integer pageSize = assertSelectReqParam.getPageSize();

        //查询入库单审核通过的 指定管理类型的资产
        List<SuzakuImportBill> importBills = suzakuImportBillMapper.getListByType(assertSelectReqParam.getType());
        if(CollectionUtils.isEmpty(importBills)){
            return new PageData<>();
        }

        //获取入库单编号
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

        //盘点资产排除处置状态的资产
        List<String> notInState = new ArrayList<>();
        notInState.add(String.valueOf(AssertState.State_6.getValue()));
        notInState.add(String.valueOf(AssertState.State_8.getValue()));
        notInState.add(String.valueOf(AssertState.State_9.getValue()));
        notInState.add(String.valueOf(AssertState.State_10.getValue()));
        assertSelectReqParam.setNotInAssertsStateList(notInState);


        List<SuzakuAssertDto> assertDtos = Lists.newArrayList();
        List<String> exceptExsit = checkItemMapper.selectList(null).stream().map(SuzakuCheckItem::getAssertCode).collect(Collectors.toList());
        assertSelectReqParam.setExceptExsit(exceptExsit);
        Integer total = suzakuImportAssertsMapper.total(assertSelectReqParam);
        int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        if (total > 0) {
            List<SuzakuImportAsserts> asserts = suzakuImportAssertsMapper.getPageAssertsByBillIds(assertSelectReqParam);

            assertDtos = asserts.stream().map(m->{
                SuzakuAssertDto suzakuAssertDto = new SuzakuAssertDto();
                suzakuAssertDto.setAssertCode(m.getAssertsCode());
                suzakuAssertDto.setOldAssertCode(m.getOldAssertsCode());
                suzakuAssertDto.setSkuId(m.getSku());
                suzakuAssertDto.setSN(m.getSnCode());
                suzakuAssertDto.setMac(m.getMacImic());
                suzakuAssertDto.setPurchasePrice(m.getPurchasePrice());
                suzakuAssertDto.setSkuName(m.getSkuName());
                suzakuAssertDto.setSkuDesc(m.getSkuEntity().getSkuDesc());
                suzakuAssertDto.setCategoryId(m.getCategoryId());
                suzakuAssertDto.setCategoryName(m.getCategory());
                suzakuAssertDto.setBrandName(m.getBrandName());
                suzakuAssertDto.setSupplierName(m.getSupplierName());
                suzakuAssertDto.setSkuType(m.getSkuEntity().getSkuType());
                suzakuAssertDto.setUnit(m.getSkuEntity().getUnit());
                suzakuAssertDto.setPeriod(m.getSkuEntity().getPeriod());
                suzakuAssertDto.setThreshold(m.getSkuEntity().getThreshold());
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
     * 更新数据
     * @param launchCheckReqParam
     */
    private void updateCheck(LaunchCheckReqParam launchCheckReqParam) {
        SuzakuCheck check = checkMapper.selectById(launchCheckReqParam.getId());
        check.setCheckStatus(Integer.valueOf(launchCheckReqParam.getCheckStatus()));
        check.setUpdateTime(LocalDateTime.now());
        check.setUpdateUser(userHolder.getUserInfo().getName());
        checkMapper.updateById(check);
        LambdaQueryWrapper<SuzakuCheckItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuCheckItem::getCheckId,check.getCheckId());
        List<SuzakuCheckItem> checkItems = checkItemMapper.selectList(queryWrapper);
        if (CollectionUtil.isNotEmpty(checkItems)){
            checkItems.forEach(v-> {
                v.setAdminStatus(AdminCheckState.CANCEL.getValue());
                v.setUpdateUser(userHolder.getUserInfo().getName());
                v.setUpdateTime(LocalDateTime.now());
                checkItemMapper.updateById(v);
            });
        }
    }


    /**
     * 定时更新盘点任务状态 未开始-》进行中
     */
    @Override
    public void startCheck(){
        List<SuzakuCheck> suzakuChecks = checkMapper.selectList(null);
        List<SuzakuCheck> checks = suzakuChecks.stream().filter(v -> CheckState.NOTSTARTED.getValue().equals(v.getCheckStatus())).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(checks)){
            return;
        }
        checks.forEach(v -> {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
            String format = sdf.format(now);
            String startDate = DateUtils.localDateTimeToString(v.getStartTime(), FORMAT_DATETIME);
            boolean isNow = DateUtils.isNow(format, startDate);
            if (isNow){
                log.info("定时任务：当天：{} 未开始的盘点任务id：{}，name:{}",startDate,v.getCheckId(),v.getCheckName());
                v.setCheckStatus(CheckState.INPROGRESS.getValue());
                v.setUpdateTime(LocalDateTime.now());
                checkMapper.updateById(v);
            }
        });
    }
}
