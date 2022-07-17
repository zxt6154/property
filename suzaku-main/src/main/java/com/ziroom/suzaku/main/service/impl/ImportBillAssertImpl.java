package com.ziroom.suzaku.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziroom.suzaku.main.dao.SuzakuSkuMapper;
import com.ziroom.suzaku.main.entity.*;
import com.ziroom.suzaku.main.model.dto.resp.CreateBill;
import com.ziroom.suzaku.main.model.vo.ImportBillAssertVo;
import com.ziroom.suzaku.main.service.*;
import com.ziroom.suzaku.main.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ImportBillAssertImpl implements ImportBillAssert {

    @Resource
    private SuzakuImportBillService importBillService;

    @Resource
    private SuzakuImportAssertsService importAssertsService;//

    @Resource
    private SuzakuStationService suzakuStationService;

    @Resource
    private SuzakuManageTypeService suzakuManageTypeService; //companyMappingService

    @Resource
    private SuzakuCompanyMappingService companyMappingService; //

    @Resource
    private SuzakuSkuMapper suzakuSkuMapper; //suzakuImportAssertsService

    @Autowired
    private SuzakuAssertsLogService logService;

    @Autowired
    private UserHolder userHolder;

    @Resource
    private SuzakuImportAssertsService suzakuImportAssertsService; //suzakuClassifyPropertyService

    @Resource
    private SuzakuClassifyPropertyService suzakuClassifyPropertyService; //

    private static final String str = "RK";

    AtomicInteger atomicInteger = new AtomicInteger();


    @Override
    @Transactional
    //事务
    public Boolean saveAssertBill(ImportBillAssertVo billAssertVo) {

        //CreateBill newBill = importBillService.newBills(billAssertVo.getSuzakuImportBill());
        CreateBill createBill = billAssertVo.getSuzakuImportBill();
        SuzakuImportBill newBill = importBillService.newBills(createBill);

        //设置入库单号
        StringBuffer importBillNumStr = new StringBuffer(str);
        importBillNumStr.append(UUIDUtil.getUID());
        newBill.setImportBillNum(importBillNumStr.toString());

        String stationName = "-";
        if(!StringUtils.isEmpty(createBill.getStationId())){
            stationName = suzakuStationService.getStationById(createBill.getStationId()).getStationName();
        }
        createBill.setStationName(stationName);
        //组合资产条码

        BigDecimal sum = new BigDecimal(0);
        BigDecimal reduce = billAssertVo.getAsserts().stream().map(assertSkuVo -> {
            return assertSkuVo.getPurchasePrice();
        }).reduce(sum, (a, b) -> a.add(b));

        //逐条插入
        billAssertVo.getAsserts().stream().forEach(assertSkuVo -> {
            String assertCodeGenerated = createAssertCode(newBill, assertSkuVo.getSku());

            SuzakuImportAsserts asserts = SuzakuImportAsserts.builder().assertsCode(assertSkuVo.getAssertsCode())
                                            .importBillNum(newBill.getImportBillNum())  //连接入库单-入库单主键
                                            .assertsCode((StringUtils.isNotBlank(assertSkuVo.getAssertsCode())) ? assertSkuVo.getAssertsCode() : assertCodeGenerated)//资产条码
                                            .oldAssertsCode(assertSkuVo.getOldAssertsCode()) //旧资产条码
                                            .sku(assertSkuVo.getSku()) //SKU
                                            .skuName(assertSkuVo.getSkuName()) //skuName
                                            .snCode(assertSkuVo.getSnCode())//skuName
                                            .categoryId(assertSkuVo.getCategoryId())//三级分类id
                                            .category(assertSkuVo.getCategoryName())//分类名称
                                            .brandName(assertSkuVo.getBrandName())//品牌
                                            .supplierName(newBill.getSupplier())//供应商名称
                                            .attributeStr(assertSkuVo.getAttributeDesc())//规格描述
                                            .skuType(assertSkuVo.getSkuType())//型号
                                            .assetsState(0)   //资产状态，资产状态 1闲置 2代签收 3领用 4借用 5退库中 6处置中 7转移中 8报废 9遗失 10变卖
                                            .groupId(1)
                                            .useDepartment(assertSkuVo.getUseDepartment())//使用人归属部门
                                            .usePeople(assertSkuVo.getUsePeople())//使用人
                                            .macImic(assertSkuVo.getMacImic())//MAC地址/IMEI
                                            .purchasePrice(assertSkuVo.getPurchasePrice())//购置单价
                                            .purchaseDate(assertSkuVo.getPurchaseDate())//购置日期
                                            .maintainOverdue(assertSkuVo.getMaintainOverdue())//维保到期日期
                                            .stationNo(StringUtils.isEmpty(createBill.getStationId()) ? null : createBill.getStationId().toString()) //门店编码
                                            .stationName(createBill.getStationName()) //门店名称
                                            .codeKey((StringUtils.isNotBlank(assertSkuVo.getAssertsCode())) ? "" : assertCodeGenerated.substring(0,11))//资产条码
                                            .build();

                    this.insertLog(asserts.getAssertsCode(), asserts.getSkuName(), "资产入库登记，设备状态：入库中");
                    importAssertsService.save(asserts);
        });

        newBill.setImportCount((long)billAssertVo.getAsserts().size());
        newBill.setImportSum(reduce);
        newBill.setCreateDate(LocalDateTime.now());
        boolean save = importBillService.save(newBill);

        // importAssertsService.saveBatch(assertsList);
        return save;
    }

    /**
     * 记录行为日志
     * @param assertCode
     * @param skuName
     * @param remark
     */
    private void insertLog(String assertCode, String skuName, String remark) {
        SuzakuAssertsLog assertsLog = new SuzakuAssertsLog();
        assertsLog.setOperator(userHolder.getUserInfo().getName());
        assertsLog.setAssertsCode(assertCode);
        assertsLog.setAssertsName(skuName);
        Optional<String> localIp = Optional.ofNullable(IpUtils.getLocalIp());
        localIp.ifPresent(assertsLog::setOperatorIp);
        assertsLog.setRemark(remark);
        logService.saveSuzakuAssertsLog(assertsLog);
    }


    String createAssertCode(SuzakuImportBill bill, String sku){
        //SuzakuImportBill bill = billAssertVo.getSuzakuImportBill();

        //获取管理类型
        LambdaQueryWrapper<SuzakuManageType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuManageType::getId,bill.getManagerType());
        SuzakuManageType manageTypeEntity = suzakuManageTypeService.getOne(queryWrapper);
        String typeCode = manageTypeEntity.getTypeCode();
        //获取公司类型
        LambdaQueryWrapper<SuzakuCompanyMapping> companyMappingLambdaQueryWrapper = new LambdaQueryWrapper<>();
        companyMappingLambdaQueryWrapper.eq(SuzakuCompanyMapping::getCompanyCode,bill.getImportCompany());
        SuzakuCompanyMapping company = companyMappingService.getOne(companyMappingLambdaQueryWrapper);
        String cityCode = company.getCityCode();
        //获取三级分类id
        SuzakuSkuEntity skuEntity = suzakuSkuMapper.getSuzakuSkuEntityBySkuId(sku);
        LambdaQueryWrapper<SuzakuClassifyProperty> classifyProperty = new LambdaQueryWrapper<>();
        classifyProperty.eq(SuzakuClassifyProperty::getId,skuEntity.getCategoryId());
        SuzakuClassifyProperty suzakuClassifyProperty = suzakuClassifyPropertyService.getOne(classifyProperty);
        String str = suzakuClassifyProperty.getCodePath();
        String[] arr = str.split("/");
        String path = arr[2]; //
        //拼接
        StringBuffer billNumStr = new StringBuffer();
        billNumStr.append(cityCode);
        billNumStr.append(typeCode);
        billNumStr.append(path);
        String yearTemp = StringUtils.toString(DateUtils.getTodayYear());
        String year = yearTemp.substring(yearTemp.length() - 2);
        billNumStr.append(year);
        LambdaQueryWrapper<SuzakuImportAsserts> asserts = new LambdaQueryWrapper<>();
        asserts.eq(SuzakuImportAsserts::getCodeKey,billNumStr.toString());
        int count = suzakuImportAssertsService.count(asserts);
        String code = "";
        int incre = 0;
        if (count != 0){
            atomicInteger = new AtomicInteger(count);
            incre = atomicInteger.incrementAndGet();
        }else {
            atomicInteger = new AtomicInteger(0);
            incre = atomicInteger.incrementAndGet();
        }
        code = String.format("%04d", incre);
        billNumStr.append(code);
        System.out.println("code："+billNumStr);
        return billNumStr.toString();
    }
}
