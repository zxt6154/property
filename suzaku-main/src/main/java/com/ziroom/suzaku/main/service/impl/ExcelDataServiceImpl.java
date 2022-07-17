package com.ziroom.suzaku.main.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ziroom.suzaku.main.common.ExportUploadUtil;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.dao.SuzakuSkuMapper;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.dto.req.BorrowRecipientExcelReq;
import com.ziroom.suzaku.main.model.dto.req.ExcelImportAssertReq;
import com.ziroom.suzaku.main.model.dto.resp.BorrowRecipientExcelResp;
import com.ziroom.suzaku.main.model.dto.resp.ExcelImportDetail;
import com.ziroom.suzaku.main.model.vo.AssertSkuVo;
import com.ziroom.suzaku.main.service.ExcelDataService;
import com.ziroom.suzaku.main.utils.DateUtils;
import com.ziroom.suzaku.main.utils.StringUtils;
import lombok.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelDataServiceImpl implements ExcelDataService {


    @Resource
    private SuzakuImportAssertsMapper importAssertsMapper;

    @Getter
    private List<ExcelImportAssertReq> faildList;

    @Getter
    private List<BorrowRecipientExcelReq> faildList2;
    @Getter
    private List<BorrowRecipientExcelReq> trueList2;


    @Resource
    private SuzakuSkuMapper skuMapper;

    @Autowired
    private ExportUploadUtil exportUploadUtil;

    private final static String FORMAT_DATE = "yyyy-MM-dd HH:mm:ss";


    public Map<String, Integer> listBorrowOrRecipient(MultipartFile file)  {

        Map<String, Integer> map = Maps.newHashMap();
        faildList2 = Lists.newArrayList();
        trueList2 = Lists.newArrayList();

        Map<String, List<BorrowRecipientExcelReq>> maps = exportUploadUtil.excelUpload2(file);
        faildList2.addAll(maps.get("fail"));
        trueList2.addAll(maps.get("true"));

        int allData = faildList2.size() + trueList2.size();

        map.put("all", allData);
        map.put("fail", faildList2.size());
        map.put("success", trueList2.size());
        return map;
    }

    //    @Override
    public ExcelImportDetail listImport(MultipartFile file)  {

        ExcelImportDetail excelImportDetail = new ExcelImportDetail();
        faildList = Lists.newArrayList();
        //失败，成功，全部
        Map<String, List<ExcelImportAssertReq>> assertsMap = null;
        try {
            assertsMap = ExportUploadUtil.excelUpload(file, importAssertsMapper, skuMapper);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        List<ExcelImportAssertReq> all = assertsMap.get("all");
        excelImportDetail.setAll(String.valueOf(all.size()));
        //失败
        List<ExcelImportAssertReq> faild = assertsMap.get("fail");
        //成功
        List<ExcelImportAssertReq> success = assertsMap.get("success");

        List<String> skuIds = success.stream().map(asserts -> {
            //校验成功
            return asserts.getSku();
        }).collect(Collectors.toList());


        excelImportDetail.setSuccess(String.valueOf(success.size()));
        excelImportDetail.setFaild(String.valueOf(faild.size()));

        //sku
        List<SuzakuSkuEntity> skuEntities = skuMapper.batchSelect(skuIds);

        //连结sku和资产
        List<AssertSkuVo> assertSkus = success.stream().map(asserts -> {
            AssertSkuVo assertSkuVo = new AssertSkuVo();
            BeanUtils.copyProperties(asserts, assertSkuVo);
            //处理字符串转localDateTime问题
            String purchaseDateStr = asserts.getPurchaseDate();
            LocalDateTime purchaseDate = StringUtils.isNotBlank(purchaseDateStr) ? DateUtils.dateToLozcalDateTime(DateUtils.stringToDate(DateUtils.stringToDate2(purchaseDateStr, FORMAT_DATE))) : LocalDateTime.now(); //2021/9/10

            assertSkuVo.setPurchaseDate(purchaseDate);
            String maintainOverdueStr = asserts.getMaintainOverdue();
            LocalDateTime maintainOverdue = StringUtils.isNotBlank(maintainOverdueStr) ? DateUtils.dateToLozcalDateTime(DateUtils.stringToDate(DateUtils.stringToDate2(maintainOverdueStr, FORMAT_DATE))) : LocalDateTime.now(); //2021/9/10
            assertSkuVo.setMaintainOverdue(maintainOverdue);

            skuEntities.stream().forEach(suzakuSkuEntity -> {
                //SKU的
                if (suzakuSkuEntity.getSkuId().equals(asserts.getSku())) {
                    BeanUtils.copyProperties(suzakuSkuEntity, assertSkuVo);
                }
            });

            return assertSkuVo;
        }).collect(Collectors.toList());
        excelImportDetail.setAssertSkuVo(assertSkus);

        excelImportDetail.setAssertSkuVoFaild(faild);
        //!!!!!!!!!!!!!
        faildList.addAll(excelImportDetail.getAssertSkuVoFaild());
        return excelImportDetail;
    }

}
