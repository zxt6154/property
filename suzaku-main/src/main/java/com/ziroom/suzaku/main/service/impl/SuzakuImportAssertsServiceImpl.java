package com.ziroom.suzaku.main.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.common.ExportUploadUtil;
import com.ziroom.suzaku.main.common.PageData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.AssertState;
import com.ziroom.suzaku.main.constant.enums.ManagerType;
import com.ziroom.suzaku.main.dao.SuzakuImportBillMapper;
import com.ziroom.suzaku.main.dao.SuzakuSkuMapper;
import com.ziroom.suzaku.main.entity.SuzakuClassifyCustom;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.model.dto.SuzakuImportAssertsDto;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.model.dto.resp.Assert;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.model.qo.ImportAssertQo;
import com.ziroom.suzaku.main.model.vo.AssertSkuVo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.SkuSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuClassifyCustomService;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.service.SuzakuImportBillService;
import com.ziroom.suzaku.main.utils.DateUtils;
import com.ziroom.suzaku.main.utils.DozerUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
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
public class SuzakuImportAssertsServiceImpl extends ServiceImpl<SuzakuImportAssertsMapper, SuzakuImportAsserts> implements SuzakuImportAssertsService {


    @Resource
    private SuzakuImportAssertsMapper importAssertsMapper;

    @Resource
    private SuzakuSkuMapper skuMapper;

    @Autowired
    private BeanMapper beanMapper;

    @Resource
    private SuzakuImportBillMapper suzakuImportBillMapper;


    @Autowired
    private SuzakuClassifyCustomService suzakuClassifyCustomService;

    @Resource
    private SuzakuSkuMapper suzakuSkuMapper;

    //excel批量导入物料

    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    @Autowired
    private SuzakuImportBillService suzakuImportBillService;
//
//    @Override
//    public List<SuzakuImportAsserts> updateAssetsState(List<SuzakuImportAsserts> asserts) {                // asserts
//
//        return true;
//    }
    @Override
    public PageData<AssertSkuVo> listByBillNum(AssertSelectReqParam assertSelectReqParam) {

        Integer pageSize = assertSelectReqParam.getPageSize();
        Integer total = importAssertsMapper.total(assertSelectReqParam);
        int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;

        List<SuzakuImportAsserts> taskList = Lists.newArrayList();

        if (total > 0) {
            taskList = importAssertsMapper.getPageAssertsByBillIds(assertSelectReqParam);
        }

        List<String> skuIds = taskList.stream().map(asserts -> {
            //校验成功
            return asserts.getSku();
        }).collect(Collectors.toList());

        //sku
        List<SuzakuSkuEntity> skuEntities = skuMapper.batchSelect(skuIds);

        //连结sku和资产
        List<AssertSkuVo> assertSkus = taskList.stream().map(asserts -> {
            AssertSkuVo assertSkuVo = new AssertSkuVo();
            BeanUtils.copyProperties(asserts, assertSkuVo);
            skuEntities.stream().forEach(suzakuSkuEntity -> {
                //SKU的
                if (suzakuSkuEntity.getSkuId().equals(asserts.getSku())) {
                    BeanUtils.copyProperties(suzakuSkuEntity, assertSkuVo);
                }
            });
            return assertSkuVo;
        }).collect(Collectors.toList());


        PageData<AssertSkuVo> pageData = new PageData<>(); //AssertSkuVo
        pageData.setData(assertSkus);
        pageData.setPages(page);
        pageData.setTotal(total);
        pageData.setPageNum(assertSelectReqParam.getOffset());
        pageData.setPageSize(pageSize);
        return pageData;
    }

    @Override
    public PageData<SuzakuImportAssertsDto> getAssertsList(AssertQo qo) {
        if (qo.getOffset() == null) {
            qo.setOffset(1);
        }
        if (qo.getPageSize() == null) {
            qo.setPageSize(20);
        }
        Page<SuzakuImportAsserts> page = new Page<>(qo.getOffset(), qo.getPageSize());
        //查询入库单审核通过的
        List<SuzakuImportBill> importBills = suzakuImportBillMapper.getListByType(qo.getManagerType());
        List<String> importBillIds = importBills.stream().map(SuzakuImportBill::getImportBillNum).collect(Collectors.toList());
        qo.setImportBills(importBillIds);
        //属性组拼装
        if(Objects.nonNull(qo.getAttributes()) && !qo.getAttributes().isEmpty()){
            //拼装规格描述
            List<String> attributeStrList = Lists.newArrayList();
            List<SuzakuClassifyCustomDto> classifyCustoms = suzakuClassifyCustomService.getClassifyCustomByClassifyId(qo.getCategoryId());
            Map<Long, String> customMap = classifyCustoms.stream().collect(Collectors.toMap(SuzakuClassifyCustomDto::getId, SuzakuClassifyCustomDto::getCustomName));
            qo.getAttributes().forEach((k,v)->{
                StringBuilder attributeStr = new StringBuilder();
                attributeStrList.add(attributeStr.append(customMap.get(Long.parseLong(k))).append(":").append(v).toString());
            });

            String attributrStr = StringUtils.join(attributeStrList, ",");
            qo.setAttributeStr(attributrStr);
        }
        IPage<SuzakuImportAsserts> iPage = importAssertsMapper.selectAssertsList(page,qo);
        List<SuzakuImportAssertsDto> importAssertsDtos = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(iPage.getRecords())){
            List<SuzakuImportAsserts> records = iPage.getRecords();
//            List<String> skuIdList = records.stream().map(SuzakuImportAsserts::getSku).collect(Collectors.toList());
//            List<SuzakuSkuEntity> skuInfoList = suzakuSkuMapper.getSkuInfoList(skuIdList);
//            Map<String, SuzakuSkuEntity> skuInfoMap = skuInfoList.stream().collect(Collectors.toMap(SuzakuSkuEntity::getSkuId, a -> a));
            importAssertsDtos = records.stream().map(m->{
                SuzakuImportAssertsDto suzakuAssertDto = new SuzakuImportAssertsDto();
                suzakuAssertDto.setId(String.valueOf(m.getId()));
                suzakuAssertDto.setAssertsCode(m.getAssertsCode());
                suzakuAssertDto.setOldAssertsCode(m.getOldAssertsCode());
                suzakuAssertDto.setSku(m.getSku());
                suzakuAssertDto.setSnCode(m.getSnCode());
                suzakuAssertDto.setMacImic(m.getMacImic());
                suzakuAssertDto.setPurchasePrice(m.getPurchasePrice());
                suzakuAssertDto.setSkuName(m.getSkuName());
                suzakuAssertDto.setSkuDesc(m.getSkuEntity().getSkuDesc());
                suzakuAssertDto.setCategoryId(m.getCategoryId());
                suzakuAssertDto.setUsePeople(m.getUsePeople());
                suzakuAssertDto.setCategory(m.getCategory());
                suzakuAssertDto.setBrandName(m.getBrandName());
                suzakuAssertDto.setSupplierName(m.getSupplierName());
                suzakuAssertDto.setSkuType(m.getSkuEntity().getSkuType());
                suzakuAssertDto.setUnit(m.getSkuEntity().getUnit());
                suzakuAssertDto.setPeriod(m.getSkuEntity().getPeriod());
                suzakuAssertDto.setThreshold(m.getSkuEntity().getThreshold());
                suzakuAssertDto.setOperator(m.getOperator());
                suzakuAssertDto.setStationNo(m.getStationNo());
                suzakuAssertDto.setStationName(m.getStationName());
                suzakuAssertDto.setAttributeStr(m.getAttributeStr());
                suzakuAssertDto.setUseDepartment(m.getUseDepartment());
                if (m.getPurchaseDate() != null){
                    suzakuAssertDto.setPurchaseDate(DateUtils.localDateTimeToString(m.getPurchaseDate(),FORMAT_DATETIME));
                }
                //设置资产状态
                if (m.getAssetsState() != null){
                    Optional<AssertState> assertState = Optional.ofNullable(AssertState.getByCode(m.getAssetsState()));
                    assertState.ifPresent(state -> suzakuAssertDto.setAssetsState(state.getLabel()));
                }
                if (m.getMaintainOverdue() != null){
                    suzakuAssertDto.setMaintainOverdue(DateUtils.localDateTimeToString(m.getMaintainOverdue(),FORMAT_DATETIME));
                }
                //设置资产类型
                LambdaQueryWrapper<SuzakuImportBill> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SuzakuImportBill::getImportBillNum,m.getImportBillNum());
                SuzakuImportBill one = suzakuImportBillService.getOne(queryWrapper);
                if (ObjectUtil.isNotEmpty(one) &&  null != one.getManagerType()){
                    Optional<ManagerType> managerType = Optional.ofNullable(ManagerType.getByCode(one.getManagerType()));
                    managerType.ifPresent(manager -> suzakuAssertDto.setManagerType(manager.getLabel()));
                }
                if (m.getCreateTime() != null){
                    suzakuAssertDto.setCreateTime(DateUtils.localDateTimeToString(m.getCreateTime(),FORMAT_DATETIME));
                }
                if (m.getUpdateTime() != null){
                    suzakuAssertDto.setUpdateTime(DateUtils.localDateTimeToString(m.getUpdateTime(),FORMAT_DATETIME));
                }
                return suzakuAssertDto;
            }).collect(Collectors.toList());
        }
        PageData<SuzakuImportAssertsDto> pageData = new PageData<>();
        pageData.setData(importAssertsDtos);
        pageData.setPages(iPage.getPages());
        pageData.setTotal(iPage.getTotal());
        pageData.setPageNum(qo.getOffset());
        pageData.setPageSize(qo.getPageSize());
        return pageData;
    }

    @Override
    public List<SuzakuImportAssertsDto> assertList(AssertQo qo) {
        //查询入库单审核通过的
        List<SuzakuImportBill> importBills = suzakuImportBillMapper.getListByType(qo.getManagerType());
        List<String> importBillIds = importBills.stream().map(SuzakuImportBill::getImportBillNum).collect(Collectors.toList());
        qo.setImportBills(importBillIds);
        List<SuzakuImportAsserts> asserts = importAssertsMapper.selectAssertsList(qo);
        return beanMapper.mapList(asserts,SuzakuImportAssertsDto.class);
    }


    @Override
    public void updateBatch(List<SuzakuImportAsserts> lists) {
        this.updateBatchById(lists);
    }

}
