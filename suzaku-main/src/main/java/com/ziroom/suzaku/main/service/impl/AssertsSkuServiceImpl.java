package com.ziroom.suzaku.main.service.impl;
import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.dao.SuzakuDealHistoryMapper;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.dao.SuzakuImportBillMapper;
import com.ziroom.suzaku.main.entity.SuzakuCheckItem;
import com.ziroom.suzaku.main.entity.SuzakuDealHistory;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.model.qo.AssertsSkuQo;
import com.ziroom.suzaku.main.model.vo.AssertSkuVo;
import com.ziroom.suzaku.main.service.AssertsSkuService;
import com.ziroom.suzaku.main.service.SuzakuSkuService;
import com.ziroom.suzaku.main.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssertsSkuServiceImpl implements AssertsSkuService {

    @Resource
    private SuzakuImportAssertsMapper importAssertsMapper;

    @Autowired
    private SuzakuSkuService skuService;

    @Resource
    private SuzakuImportBillMapper importBillMapper;

    @Resource
    private SuzakuDealHistoryMapper suzakuDealHistoryMapper;

    @Override
    public List<SuzakuImportAsserts> assertsList(AssertsSkuQo assertsSkuQo) {

        //skuName fuzzy search to find skuIds

        if (StringUtils.isNotBlank(assertsSkuQo.getSkuName())){
            String skuName = assertsSkuQo.getSkuName();
            List<String> skuIds = skuService.searchSkuFuzzy(skuName);
            assertsSkuQo.setSkuIds(skuIds);
        }

        List<String> codes = assertsSkuQo.getAssertsCodes();
        if (StringUtils.isNotBlank(assertsSkuQo.getAssertsCode())){
            String assertsCode = assertsSkuQo.getAssertsCode();
            codes.add(assertsCode);
        }
        assertsSkuQo.setAssertsCodes(codes);

        int total = importAssertsMapper.assoSearchTotal(assertsSkuQo);

        List<SuzakuImportAsserts> assertList = Lists.newArrayList();
        if (total > 0) {
            assertList = importAssertsMapper.searchAsso(assertsSkuQo);
        }
        return assertList;
    }



    @Override
    public PageData<SuzakuImportAsserts> assertJoinSku(AssertsSkuQo assertsSkuQo) {

        List<SuzakuImportAsserts> assertsList = new ArrayList<>();
        List<String> billNums = assertsSkuQo.getImportBillNums() == null ? Lists.newArrayList() : assertsSkuQo.getImportBillNums();
        //根据manageType获取所有入库单num
        List<SuzakuImportAsserts> taskList = Lists.newArrayList();

        PageData<SuzakuImportAsserts> pageData = new PageData<>();
        Integer pageSize = assertsSkuQo.getPageSize();
        //条件 查询入库单、、、
        List<String> bullNumList = this.billNum( assertsSkuQo.getManageType(), assertsSkuQo.getRemandDepot());
        if(bullNumList == null){
            return pageData;
        }
            billNums.addAll(bullNumList);
            assertsSkuQo.setImportBillNums(billNums);

        //条件查询sku
        List<String> skuIds = this.skuName(assertsSkuQo.getSkuName());
        if(skuIds == null){
            return pageData;
        }
        assertsSkuQo.setSkuIds(skuIds);

        //如果入库单中没有此manageType对应的数则、、、
          List<String> codes = assertsSkuQo.getAssertsCodes() == null ? Lists.newArrayList() : assertsSkuQo.getAssertsCodes();
          if (StringUtils.isNotBlank(assertsSkuQo.getAssertsCode())){
              String assertsCode = assertsSkuQo.getAssertsCode();
              codes.add(assertsCode);
          }
          assertsSkuQo.setAssertsCodes(codes);

          int total = importAssertsMapper.assoSearchTotal(assertsSkuQo);

            if (total > 0) {
                taskList = importAssertsMapper.assoSearch(assertsSkuQo);
            }
            pageData.setData(taskList);
            if(assertsSkuQo.getIndex() == null){
                pageData.setPageNum(1);
                pageData.setPages(1);
                pageData.setPageSize(total);
                pageData.setTotal(total);
            } else {
                int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
                pageData.setPageNum(assertsSkuQo.getOffset());
                pageData.setPages(page);
                pageData.setPageSize(pageSize);
                pageData.setTotal(total);
            }
        return pageData;
    }



    /**
     * 查询辅助  此查询条件 一定会有
     * @param manageType
     * @return
     */
    private List<String> billNum(Integer manageType, String remandDepot){
        List<String> billNums = new ArrayList<>();
        List<SuzakuImportBill> listByType = importBillMapper.getListByTypeAndR(manageType, remandDepot);
        List<String> importBillNums = listByType.stream().map(SuzakuImportBill::getImportBillNum).collect(Collectors.toList());
        if(importBillNums.size() > 0){
           return importBillNums;
        }
        return billNums;
    }

    /**
     * skuName fuzzy search to find skuIds
     * @param skuName
     * @return
     */
    private List<String> skuName(String skuName){
        List<String> skuIds = new ArrayList<>();
       if(skuName != null){
           List<String> skuIdList = skuService.searchSkuFuzzy(skuName);
           if(skuIdList.size() > 0){
               return skuIdList;
           }
       } else {
           skuIds = new ArrayList<>();
       }
        return skuIds;
    }


    @Override
    public PageData<SuzakuImportAsserts> getAssertList(AssertsSkuQo assertsSku) {
        PageData<SuzakuImportAsserts> pageData = new PageData<>();
        Integer pageSize = assertsSku.getPageSize();
        List<SuzakuImportAsserts> asserts = importAssertsMapper.assoSearch(assertsSku);
        if (CollectionUtil.isEmpty(asserts)){
            return new PageData<>();
        }
        int total = asserts.size();
        int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        pageData.setData(asserts);
        pageData.setPageNum(assertsSku.getOffset());
        pageData.setPages(page);
        pageData.setTotal(total);
        pageData.setPageSize(pageSize);
        return pageData;
    }
}
