package com.ziroom.suzaku.main.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziroom.suzaku.main.base.BaseTest;
import com.ziroom.suzaku.main.dao.SuzakuSkuMapper;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.entity.*;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.model.qo.ImportAssertQo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuClassifyPropertyService;
import com.ziroom.suzaku.main.service.SuzakuCompanyMappingService;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import com.ziroom.suzaku.main.service.SuzakuManageTypeService;
import com.ziroom.suzaku.main.utils.DateUtils;
import com.ziroom.suzaku.main.utils.StringUtils;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


class SuzakuImportAssertsControllerTest extends BaseTest {

    @Autowired
    private MockMvc mvc;

    private static final String prefix = "/api/v1/asserts";

    private AtomicInteger atomicInteger;

    @Resource
    private SuzakuSkuMapper suzakuSkuMapper;

    @Autowired
    private SuzakuManageTypeService suzakuManageTypeService;

    @Resource
    private SuzakuCompanyMappingService companyMappingService;

    @Resource
    private SuzakuImportAssertsService suzakuImportAssertsService;

    @Resource
    private SuzakuImportAssertsMapper importAssertsMapper;

    @Autowired
    private SuzakuClassifyPropertyService suzakuClassifyPropertyService;

    @Test
    void getAssertsList() {

        AssertsSkuQo assertsSkuQo = new AssertsSkuQo();
        List<String> lists = new ArrayList();

        lists.add("BJI020102210002");
        lists.add("BJI020102210003");
        lists.add("BJI020102210004");
        lists.add("BJM020101210001");
        lists.add("BJM020101210002");
        lists.add("BJM020101210003");

        assertsSkuQo.setAssertsCodes(lists);

        List<SuzakuImportAsserts> pageAssertsByBillIds = importAssertsMapper.assoSearch(assertsSkuQo);

        for(int i = 0; i< pageAssertsByBillIds.size()-1 ;i++){
            System.out.println("````````````````"+pageAssertsByBillIds.get(i));
        }

    }

    @Test
    void assertList() throws Exception {
        ImportAssertQo qo = new ImportAssertQo();
        List<String> set = new ArrayList<>();
        set.add("1");
        set.add("2");
        qo.setAssertsStateList(set);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post(prefix + "/assertList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(qo))
                .header("X-ZCLOUD-TOKEN","eyJraWQiOiJ6Y2xvdWQtc3NvLWRlZmF1bHQiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJ6Y2xvdWQiLCJzdWIiOiJ6Y2xvdWQtc3NvIiwidXNlckluZm8iOnsiam9iTmFtZSI6IkphdmHlt6XnqIvluIgiLCJkZXB0TmFtZSI6IuS4muWKoeW5s-WPsOe7hCIsInVpZCI6IjYwMDMzNDU3IiwiYXZhdGFyVXJsIjoiaHR0cHM6XC9cL2VocnN0YXRpYy56aXJvb20uY29tXC82MDAzMzQ1Ny5qcGciLCJuaWNrTmFtZSI6IuadjuenieWXoyIsInVzZXJUeXBlIjoyLCJ1c2VyTmFtZSI6ImxpYnMxIiwiZW1haWwiOiJsaWJzMUB6aXJvb20uY29tIn0sImF1dGgiOnsic3lzIjoiQ0FTIiwidG9rZW4iOiJOakF3TXpNME5UY3RZV05qYjNWdWRDNXRZVzVoWjJWdFpXNTBMbUZ3YVMweE5rVXlNMFF3UWpJeVJUUXlNa00yT0VVd05EWXdOVFEzUlRZMU9VWTBOeTB4TmpNME5UUXdOalV5T0RVMkxURT0ifSwiZXhwIjoxNjM0Nzk5ODUxLCJpYXQiOjE2MzQwMDYyNjMsInVzZXJuYW1lIjoibGliczEiLCJzdGF0dXMiOjF9.ckKr1B7RXdyodMXWwoYugMShtciffkzFf4QwLEwbHs1A8j7zHs2YiUM9OmT2Sdsfvj3eC8OJ5N4A5pVocCp-X5C0HWN4tAPfc8s6XJMIXLLdty99JhfyP_TOOq6og5kpRiUpiUhz9ASSoO7Z8yCCWhX0WuQFc3v1cwGX4jrkwupReVSsO3q8ONUpWFS5voeoirlD1wWQk6805F07eCO9ESwrIl30CFwRidQ9an-0C80AoR38T1rrs4HC_QavQ9yrmtbh-NyGivhoJB-e2ZemXM8tcRgHvYR65guKK9GjAY2erQyI3wCKGvgcMKaubToIxpMBRowwopEBo3TxVy7l9w"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        JsonResult r = JSON.parseObject(result.getResponse().getContentAsString(),JsonResult.class);
        TestCase.assertEquals(r.getCode(),0);

    }


    @Test
    void createCode(){
        String sku = "1103103450772";
        SuzakuImportBill bill = new SuzakuImportBill();
        bill.setImportCompany("8003");
        bill.setManagerType(1);
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
        String path = arr[2];
        //拼接
        StringBuffer billNumStr = new StringBuffer();
        billNumStr.append(cityCode);
        billNumStr.append(typeCode);
        billNumStr.append(path);
        String yearTemp = StringUtils.toString(DateUtils.getTodayYear());
        String year = yearTemp.substring(yearTemp.length() - 2);
        billNumStr.append(year);
        System.out.println("key："+billNumStr);
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
    }
}