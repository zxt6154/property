package com.ziroom.suzaku.main.listeners;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.dao.SuzakuSkuMapper;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.dto.req.ExcelImportAssertReq;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.utils.StringUtils;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class ExcelImportListener extends AnalysisEventListener<ExcelImportAssertReq> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelImportListener.class);

    private SuzakuImportAssertsMapper assertsMapper;

    private SuzakuSkuMapper skuMapper;

    @Getter
    private Set<String> setassertCode = new HashSet<>();
    @Getter
    private Set<String> setassertCodeOld = new HashSet<>();
    @Getter
    private Set<String> setMacImic = new HashSet<>();
    @Getter
    private Set<String> setSnCode = new HashSet<>();
    private final static String FORMAT_DATETIME_2 = "yyyy-MM-dd HH:mm:ss";
    @Getter
    public ExcelImportAssertReq ExcelImportAssertReq;

    @Getter
    private List<ExcelImportAssertReq> list = new ArrayList<>();

    public ExcelImportListener(SuzakuImportAssertsMapper assertsMapper, SuzakuSkuMapper skuMapper) {
        this.assertsMapper = assertsMapper;
        this.skuMapper = skuMapper;
    }

    @Override
    public void invoke(ExcelImportAssertReq asserts, AnalysisContext context) {
        list.add(validateAsserts(asserts));
    }


    //处理数据校验
    ExcelImportAssertReq validateAsserts(ExcelImportAssertReq asserts){
        ExcelImportAssertReq validateAsserts = new ExcelImportAssertReq();
        BeanUtils.copyProperties(asserts, validateAsserts);
        String assertsCode = validateAsserts.getAssertsCode();  //为空(自动生成)、有值(本excel页面是否重复、与数据库数据是否重复、长度超过20)
        String oldAssertsCode = validateAsserts.getOldAssertsCode();//为空  有值(本excel页面是否重复、长度超过100)
        String sku = validateAsserts.getSku();//为空、有值(校验数据库是否有匹配值)
        String macImic = validateAsserts.getMacImic();  //有值(长度超过100，与数据库数据是否重复),校验重复
        String snCode = validateAsserts.getSnCode();     //有值(长度超过100，与数据库数据是否重复),校验重复
        String purchaseDate = validateAsserts.getPurchaseDate(); //为空则自动填入当前日期
        if (StringUtils.isEmpty(purchaseDate)){
            String format = LocalDateTimeUtil.format(LocalDateTime.now(), FORMAT_DATETIME_2);
            validateAsserts.setPurchaseDate(format);
        }

        Map<String,String> mapStrNull = new HashMap<>();
        mapStrNull.put("assertsCode",assertsCode);
        mapStrNull.put("oldAssertsCode",oldAssertsCode);
        mapStrNull.put("sku",sku);
        mapStrNull.put("macImic",macImic);
        mapStrNull.put("snCode",snCode);

        String checkLength = checkLength(mapStrNull);

        String checkIfGlobalExists = checkIfGlobalExists("assertsCode", assertsCode);
        String oldAssertsCode1 = checkIfGlobal("oldAssertsCode", oldAssertsCode);
        String skuStr = checkSku("sku", sku);
        String checkIfExists = checkIfExists(mapStrNull);
        String checkNotEmpty = checkNotEmpty(mapStrNull);

        StringBuffer sb = new StringBuffer();
        if(checkNotEmpty != null){
            sb.append(checkNotEmpty);
        }
        if(checkLength != null){
            sb.append(checkLength);
        }
        if(checkIfExists != null){
            sb.append(checkIfExists);
        }
        if(StringUtils.isNotBlank(checkIfGlobalExists) && checkIfGlobalExists != null){
            sb.append(checkIfGlobalExists);
        }
        if(StringUtils.isNotBlank(oldAssertsCode1) && oldAssertsCode1 != null){
            sb.append(oldAssertsCode1);
        }
        if(StringUtils.isNotBlank(skuStr) && skuStr != null){
            sb.append(skuStr);
        }


        if (StringUtils.isNotBlank(purchaseDate) && purchaseDate.length() > 18) {
            sb.append("购置日期填写有误");
        }
        if (StringUtils.isNotBlank(validateAsserts.getMaintainOverdue()) && validateAsserts.getMaintainOverdue().length() > 18) {
            sb.append("维保日期填写有误");
        }
        String replaceStr = sb.toString().replace("assertsCode", "资产条码").replace("oldAssertsCode", "旧资产条码").replace("sku", "sku码").replace("macImic", "MAC地址/IMEI");

        validateAsserts.setSuccessFlag(replaceStr);
        return validateAsserts;
    }

    private String checkSku(String fieldKey, String fieldValue){
        StringBuffer sb =new StringBuffer();
        if(StringUtils.isNotBlank(fieldValue)){
            AssertQo assertQo = new AssertQo();
            assertQo.setAssertsCode(fieldValue);
            // assertQo.setPageSize(10000);
           if(StringUtils.isNotBlank(fieldValue)){
               SuzakuSkuEntity suzakuSkuEntityBySkuId = skuMapper.getSuzakuSkuEntityBySkuId(fieldValue);
               if(StringUtils.isEmpty(suzakuSkuEntityBySkuId)){
                   sb.append(fieldKey + "数据库不存在，不能加入|");
               }
           }
        }
        return sb.toString();
    }


    public String checkNotEmpty(Map<String, String> fieldsMap) {
        Map<String, String> fields = new HashMap<>();
        fields.putAll(fieldsMap);
        StringBuffer sb =new StringBuffer();
        fields.remove("macImic");
        fields.remove("snCode");
        fields.remove("oldAssertsCode");
        fields.remove("assertsCode"); //资产条码这时不校验是否为空   sku不能为空且数据库必须有匹配
        fields.forEach((fieldKey, fieldVale) -> {
            if(StringUtils.isEmpty(fieldVale)){
                    sb.append(fieldKey + "为空|");
            }
        });
        return sb.toString();
    }

    public String checkLength(Map<String, String> fieldsMap) {
        Map<String, String> fields = new HashMap<>();
        fields.putAll(fieldsMap);
        StringBuffer sb =new StringBuffer();
        fields.forEach((fieldKey, fieldVale) -> {
            if(StringUtils.isNotBlank(fieldVale)){
                if (fieldKey.equals("assertsCode")){
                    String s = fieldVale.length() > 20 ? ( fieldKey + "长度超过20|") : "";
                    sb.append(s);
                }
                sb.append(fieldVale.length() > 100 ? (fieldKey + "长度超过100|") : "");

            }
        });
        return sb.toString();
    }

    /**
     * 校验是否重复
     * @param
     * @return
     */
    public String checkIfExists(Map<String, String> fieldsMap) {

        Map<String, String> fields = new HashMap<>();
        fields.putAll(fieldsMap);
        StringBuffer sb =new StringBuffer();
        fields.remove("sku");
        fields.forEach((fieldKey, fieldValue)->{
            if(StringUtils.isNotBlank(fieldValue)){
                if(fieldKey.equals("assertsCode")){
                    if(setassertCode.contains(fieldValue)){
                        String s = fieldKey + "已存在|";
                        sb.append(s);
                    } else {setassertCode.add(fieldValue);}
                }
                if(fieldKey.equals("oldAssertsCode")){
                    if(setassertCodeOld.contains(fieldValue)){
                        String s = fieldKey + "已存在|";
                        sb.append(s);
                    } else {setassertCodeOld.add(fieldValue);}
                }
                if(fieldKey.equals("macImic")){
                    if(setMacImic.contains(fieldValue)){
                        String s = fieldKey + "已存在|";
                        sb.append(s);
                    } else {setMacImic.add(fieldValue);}
                }
                if(fieldKey.equals("snCode")){
                    if(setSnCode.contains(fieldValue)){
                        String s = fieldKey + "已存在|";
                        sb.append(s);
                    } else {setSnCode.add(fieldValue);}
                }
            }
        });

        return sb.toString();
    }

    public String checkIfGlobalExists(String fieldKey, String fieldValue) {
        StringBuffer sb =new StringBuffer();
        if(StringUtils.isNotBlank(fieldValue)) {
            AssertQo assertQo = new AssertQo();
            assertQo.setAssertsCode(fieldValue);
            // assertQo.setPageSize(10000);
            List<SuzakuImportAsserts> asserts = assertsMapper.selectAssertsList(assertQo);
            if(StringUtils.isNotBlank(fieldValue)){
                if (asserts.size() > 0) {
                    sb.append(fieldKey + "数据库已存在|");
                }
            }
        }
        return sb.toString();
    }
    public String checkIfGlobal(String fieldKey, String fieldValue) {
        StringBuffer sb =new StringBuffer();
        if(StringUtils.isNotBlank(fieldValue)){
            AssertQo assertQo = new AssertQo();
            assertQo.setOldAssertsCode(fieldValue);
            // assertQo.setPageSize(10000);
            List<SuzakuImportAsserts> asserts = assertsMapper.selectAssertsList(assertQo);
            if(asserts.size() > 0){
                sb.append(fieldKey + "数据库已存在|");
            }
        }
        return sb.toString();
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

}
