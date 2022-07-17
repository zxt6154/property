package com.ziroom.suzaku.main.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.converter.ServiceConverter;
import com.ziroom.suzaku.main.dao.SuzakuClassifyCustomValueMapper;
import com.ziroom.suzaku.main.dao.SuzakuSkuMapper;
import com.ziroom.suzaku.main.entity.SuzakuClassifyCustomValueEntity;
import com.ziroom.suzaku.main.entity.SuzakuClassifyProperty;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.exception.SuzakuBussinesException;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.model.dto.SuzakuSkuDto;
import com.ziroom.suzaku.main.param.SkuCreateReqParam;
import com.ziroom.suzaku.main.param.SkuSelectReqParam;
import com.ziroom.suzaku.main.service.SuzakuClassifyCustomService;
import com.ziroom.suzaku.main.service.SuzakuClassifyPropertyService;
import com.ziroom.suzaku.main.service.SuzakuSkuService;
import com.ziroom.suzaku.main.utils.UUIDUtil;
import com.ziroom.suzaku.main.utils.UserHolder;
import com.ziroom.tech.enums.ResponseEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 资产sku基础数据
 * @author xuzeyu
 */
@Service
public class SuzakuSkuServiceImpl  implements SuzakuSkuService {

    @Autowired
    private SuzakuClassifyCustomValueMapper classifyCustomValueMapper;

    @Autowired
    private SuzakuSkuMapper suzakuSkuMapper;

    @Autowired
    private UserHolder userHolder;

    @Autowired
    private SuzakuClassifyCustomService suzakuClassifyCustomService;

    @Autowired SuzakuClassifyPropertyService suzakuClassifyPropertyService;

    @Override
    @Transactional
    public void create(SkuCreateReqParam skuCreateReqParam) {

        //属性组过滤
        if(!skuCreateReqParam.getAttributes().isEmpty()){
            Map<String, String> filterAttributes = skuCreateReqParam.getAttributes().entrySet().stream().filter(m -> StringUtils.isNotBlank(m.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));
            skuCreateReqParam.setAttributes(filterAttributes);
        }
       /* if(skuCreateReqParam.getAttributes().isEmpty()){
            throw new SuzakuBussinesException(ResponseEnum.RESPONSE_ERROR_CODE.getCode(), "请填写sku分类扩展信息");
        }*/

        //校验sku是否存在
        SuzakuSkuEntity suzakuSkuEntity = suzakuSkuMapper.getByName(skuCreateReqParam);
        /*SkuSelectReqParam skuSelectReqParam = new SkuSelectReqParam();
        skuSelectReqParam.setBrandName(skuCreateReqParam.getBrandName());
        skuSelectReqParam.setSupplierName(skuCreateReqParam.getSupplierName());
        skuSelectReqParam.setType(skuCreateReqParam.getSkuType());
        if(!skuCreateReqParam.getAttributes().isEmpty()){
            skuSelectReqParam.setAttributeDesc(joinAttributeDesc(skuCreateReqParam));
        }
        SuzakuSkuEntity suzakuSkuEntity1 = suzakuSkuMapper.getBySelectParam(skuSelectReqParam);*/

        if(Objects.nonNull(suzakuSkuEntity)){
            throw new SuzakuBussinesException(ResponseEnum.RESPONSE_ERROR_CODE.getCode(), "sku已经存在");
        }

        //商品规格关联
        String skuId = UUIDUtil.getUID();
        tagAttribute(skuCreateReqParam, skuId);

        //转换
        SuzakuSkuEntity skuEntity = ServiceConverter.suzakuSkuParam2SkuEntity().apply(skuCreateReqParam);
        skuEntity.setSkuId(skuId);
        skuEntity.setAttributeDesc(joinAttributeDesc(skuCreateReqParam));
        List<SuzakuClassifyProperty> classifyInfos = suzakuClassifyPropertyService.getClassifyInfos(skuCreateReqParam.getCategoryName());
        List<String> categoryNames = classifyInfos.stream().map(SuzakuClassifyProperty::getClassifyName).collect(Collectors.toList());
        skuEntity.setCategoryName(StringUtils.join(categoryNames, "/"));
        skuEntity.setCategoryTree(StringUtils.join(skuCreateReqParam.getCategoryName(), "/"));
        skuEntity.setOperatorName(userHolder.getUserInfo().getName());

        //资产入库
        suzakuSkuMapper.insert(skuEntity);

    }

   //, String customId, String customName     no change no need to update
    Map<String, String> ifChangeFlag(Map<String, String> attributesMap, String skuId){

        //判断传入的扩展组是否和原有的扩展组一样
        Map<String, String> attributesNew = Maps.newHashMap();

        //存储改变了的值
        attributesMap.forEach((k, v) -> {

            SuzakuClassifyCustomValueEntity  suzakuClassifyCustomValueEntity = classifyCustomValueMapper.ifChange(k, v, skuId);

            if(com.ziroom.suzaku.main.utils.StringUtils.isEmpty(suzakuClassifyCustomValueEntity)){  //为空，说明扩展组中此条数据改变了、、、不为空说明未变化
                attributesNew.put(k, v);
            }

        });

        return attributesNew;
    }
    //对比新老attributes  前提
    void compareAttr(String skuId,  Map<String, String> newAttrMap, List<SuzakuClassifyCustomValueEntity> classifyCustomValues){

//        List customIds = Lists.newArrayList();
        List ids = Lists.newArrayList();
        if(classifyCustomValues.size() > 0){
            //如果原来有扩展信息组信息
            List<SuzakuClassifyCustomValueEntity> ccves = classifyCustomValues.stream().map(cve -> {
                //String skusNew = "";
                if(cve.getSkus().contains(",")){
                    String[] skus = cve.getSkus().split(",");
                    String skusNew = Arrays.stream(skus).filter(skuIdNew -> !skuIdNew.equals(skuId)).collect(Collectors.joining(","));
                    cve.setSkus(skusNew);
                    classifyCustomValueMapper.update(cve);
                } else {
                    ids.add(cve.getId());
//                    customIds.add(cve.getCustomId());
                }
                return cve;
            }).collect(Collectors.toList());

        }

        //如编辑的扩展信息组有值，新增
        List<SuzakuClassifyCustomValueEntity> ccveList = new ArrayList<>();
        List collect = Lists.newArrayList();

        if(!newAttrMap.isEmpty()){
            newAttrMap.forEach((key,value) -> {
                SuzakuClassifyCustomValueEntity ccve = new SuzakuClassifyCustomValueEntity();
                ccve.setSkus(skuId);
                ccve.setCustomId(key);
                ccve.setCustomValue(value);
                //有对应扩展组 -更新    无对应扩展组   -新增
                SuzakuClassifyCustomValueEntity entity = classifyCustomValueMapper.ifChange(key, null, skuId);

                if(com.ziroom.suzaku.main.utils.StringUtils.isEmpty(entity)){
                    if(ids.size() > 0){
                        classifyCustomValueMapper.delBatch(ids);
                    }
                    classifyCustomValueMapper.insert(ccve);
                } else {
                    //无对应扩展组，但是其他的扩展组和此扩展其他属性一样
                    SuzakuClassifyCustomValueEntity entityc = classifyCustomValueMapper.ifChange(key, value, null);
                    if(!com.ziroom.suzaku.main.utils.StringUtils.isEmpty(entityc)){
                        String concatskuId = entityc.getSkus().concat(",").concat(skuId);
                        ccve.setSkus(concatskuId);
                    }
                    ccve.setId(entity.getId());
                    classifyCustomValueMapper.update(ccve);
                     }
                });
            } else {
                if(ids.size() > 0){
                    classifyCustomValueMapper.delBatch(ids);
                }
            }

    }
    void updateAttr(SkuCreateReqParam skuCreateReqParam){ //关于Attr的更新

            //获取skuId对应的扩展组信息
            List<SuzakuClassifyCustomValueEntity> classifyCustomValues = classifyCustomValueMapper.selByskuId(skuCreateReqParam.getSkuId());
            //获取sku的
            SuzakuSkuEntity skuEntityBySkuId = suzakuSkuMapper.getSuzakuSkuEntityBySkuId(skuCreateReqParam.getSkuId());
            //获取更新的
            Map<String, String> attributesMap = skuCreateReqParam.getAttributes();

            //如果都为空 - 未改变    有改变，改变数据表相应数据
            if(!(com.ziroom.suzaku.main.utils.StringUtils.isEmpty(skuEntityBySkuId.getAttributeDesc()) && attributesMap.isEmpty())){
                if(!attributesMap.isEmpty()){
                    Map<String, String> stringStringMap = this.ifChangeFlag(skuCreateReqParam.getAttributes(), skuCreateReqParam.getSkuId());
                    skuCreateReqParam.setAttributes(stringStringMap);
                }
                this.compareAttr(skuCreateReqParam.getSkuId(), skuCreateReqParam.getAttributes(), classifyCustomValues);
            }
    }
    @Override
    @Transactional
    public void update2(SkuCreateReqParam skuCreateReqParam) {

        String skuId = skuCreateReqParam.getSkuId();
        //转换
        SuzakuSkuEntity skuEntity = ServiceConverter.suzakuSkuParam2SkuEntity().apply(skuCreateReqParam);
        skuEntity.setSkuId(skuId);
        skuEntity.setAttributeDesc(joinAttributeDesc(skuCreateReqParam));
        //获取skuId
        this.updateAttr(skuCreateReqParam);

        List<SuzakuClassifyProperty> classifyInfos = suzakuClassifyPropertyService.getClassifyInfos(skuCreateReqParam.getCategoryName());
        List<String> categoryNames = classifyInfos.stream().map(SuzakuClassifyProperty::getClassifyName).collect(Collectors.toList());
        skuEntity.setCategoryName(StringUtils.join(categoryNames, "/"));
        skuEntity.setCategoryTree(StringUtils.join(skuCreateReqParam.getCategoryName(), "/"));
        skuEntity.setOperatorName(userHolder.getUserInfo().getName());

        //资产入库
        suzakuSkuMapper.update(skuEntity);
    }



    @Override
    public SuzakuSkuDto getSuzakuSkuDtoBySkuId(String skuId) {
        SuzakuSkuEntity suzakuSkuEntity = suzakuSkuMapper.getSuzakuSkuEntityBySkuId(skuId);
        //获取规格信息
        List<SuzakuClassifyCustomDto> classifyCustoms = suzakuClassifyCustomService.getClassifyCustomByClassifyId(suzakuSkuEntity.getCategoryId());
        List<String> customId = classifyCustoms.stream().map(m -> String.valueOf(m.getId())).collect(Collectors.toList());
        List<SuzakuClassifyCustomValueEntity> classifyCustomValueEntities = classifyCustomValueMapper.getClassifyCustom(customId, skuId);
        classifyCustomValueEntities = classifyCustomValueEntities.stream().filter(m->{
            if(m.getSkus().contains(skuId)){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        Map<String, String> attributeMap = classifyCustomValueEntities.stream()
                .collect(Collectors.toMap(SuzakuClassifyCustomValueEntity::getCustomId, SuzakuClassifyCustomValueEntity::getCustomValue));
        SuzakuSkuDto suzakuSkuDto = ServiceConverter.suzakuSkuEntity2SkuDto().apply(suzakuSkuEntity);
        List<String> catTree = Arrays.asList(suzakuSkuEntity.getCategoryTree().split("/"));
        suzakuSkuDto.setCatTree(catTree);
        suzakuSkuDto.setAttibuteMap(attributeMap);
        return suzakuSkuDto;
    }

    @Override
    public PageData<SuzakuSkuEntity> pageSkus(SkuSelectReqParam skuSelectReqParam) {
        Integer pageSize = skuSelectReqParam.getPageSize();
        Integer total = suzakuSkuMapper.skusTotal(skuSelectReqParam);
        int page = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        List<SuzakuSkuEntity> taskList = Lists.newArrayList();
        if (total > 0) {
            taskList = suzakuSkuMapper.pageSkus(skuSelectReqParam);
        }

        PageData<SuzakuSkuEntity> pageData = new PageData<>();
        pageData.setData(taskList);
        pageData.setPages(page);
        pageData.setTotal(total);
        pageData.setPageNum(skuSelectReqParam.getOffset());
        pageData.setPageSize(skuSelectReqParam.getPageSize());
        return pageData;
    }

    @Override
    public List<SuzakuSkuEntity> getSkuInfoList(List<String> skus) {
        return suzakuSkuMapper.getSkuInfoList(skus);
    }


    /**
     * 标记sku
     */
    private void tagAttribute(SkuCreateReqParam skuCreateReqParam, String skuId){
        //获取规格组
        Map<String, String> attributes = skuCreateReqParam.getAttributes();
        if(!attributes.isEmpty()){
            List<String> attributeIds = new ArrayList<>(attributes.keySet());

            //保存规格属性信息
            //查询规格属性id是否存在
            List<SuzakuClassifyCustomValueEntity> existClassifyCustoms = classifyCustomValueMapper.getClassifyCustom(attributeIds, null);
            Map<String, String> existClassifyCustomsMap = existClassifyCustoms.stream().map(m -> {
                Map<String, String> map = new HashMap<>();
                map.put(m.getCustomId() + m.getCustomValue(), Objects.isNull(m.getSkus())?"":m.getSkus());
                map.put("id" + m.getCustomId() + m.getCustomValue(), m.getId().toString());
                return map;
            }).flatMap(m -> m.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a));
            Map<String, String> notExistsAttribute = new HashMap<>();
            Map<String, String> existAttribute = new HashMap<>();
            attributes.forEach((k,v)-> {
                if (!Objects.isNull(existClassifyCustomsMap.get(k+v))) {
                    existAttribute.put(k,v);
                } else {
                    notExistsAttribute.put(k,v);
                }
            });

            //标记规格所属sku
            if(!existAttribute.isEmpty()){
                //更新
                List<SuzakuClassifyCustomValueEntity> suzakuClassifyCustomValueEntityList = Lists.newArrayList();
                existAttribute.forEach((k,v)->{
                    SuzakuClassifyCustomValueEntity classifyCustom = new SuzakuClassifyCustomValueEntity();
                    String skus = existClassifyCustomsMap.get(k + v);
                    String newSkus;
                    if(StringUtils.isNotBlank(skus)){
                        List<String> skuList = new ArrayList<>(Arrays.asList(skus.split(",")));
                        skuList.add(skuId);
                        newSkus = StringUtils.join(skuList, ",");
                    } else {
                        newSkus = skuId;
                    }
                    String id = existClassifyCustomsMap.get("id" + k + v);
                    classifyCustom.setId(Long.parseLong(id));
                    classifyCustom.setSkus(newSkus);
                    suzakuClassifyCustomValueEntityList.add(classifyCustom);
                });
                classifyCustomValueMapper.tagSku(suzakuClassifyCustomValueEntityList);
            }

            if(!notExistsAttribute.isEmpty()){
                List<SuzakuClassifyCustomValueEntity> classifyCustoms = Lists.newArrayList();
                notExistsAttribute.forEach((k,v)->{
                    SuzakuClassifyCustomValueEntity suzakuClassifyCustomValueEntity = new SuzakuClassifyCustomValueEntity();
                    suzakuClassifyCustomValueEntity.setCustomId(k);
                    suzakuClassifyCustomValueEntity.setCustomValue(v);
                    suzakuClassifyCustomValueEntity.setSkus(skuId);
                    classifyCustoms.add(suzakuClassifyCustomValueEntity);
                });
                classifyCustomValueMapper.batchInsert(classifyCustoms);
            }

        }

    }

    /**
     * 拼装规格描述
     */
    private String joinAttributeDesc(SkuCreateReqParam skuCreateReqParam){
        Map<String, String> attributes = skuCreateReqParam.getAttributes();
        //拼装规格描述
        List<String> attributeStrList = Lists.newArrayList();
        List<SuzakuClassifyCustomDto> classifyCustoms = suzakuClassifyCustomService.getClassifyCustomByClassifyId(skuCreateReqParam.getCategoryId());
        Map<Long, String> customMap = classifyCustoms.stream().collect(Collectors.toMap(SuzakuClassifyCustomDto::getId, SuzakuClassifyCustomDto::getCustomName));
        attributes.forEach((k,v)->{
            StringBuilder attributeStr = new StringBuilder();
            attributeStrList.add(attributeStr.append(customMap.get(Long.parseLong(k))).append(":").append(v).toString());
        });

        return StringUtils.join(attributeStrList, ",");
    }

    /**
     * 比较原始记录和新纪录
     */
    private Map<String,Boolean> complete(SkuCreateReqParam skuCreateReqParam, SuzakuSkuEntity skuEntity){
        Map<String, Boolean> updatedMap = new HashMap<>();
        if(!skuCreateReqParam.getSkuName().equals(skuEntity.getSkuName())){
            updatedMap.put("sku",true);
        }
        if(!skuCreateReqParam.getBrandName().equals(skuEntity.getBrandName()) || !skuCreateReqParam.getSupplierName().equals(skuEntity.getSupplierName())){
            updatedMap.put("common", true);
        }
        if(Objects.nonNull(skuCreateReqParam.getAttributes()) && !skuCreateReqParam.getAttributes().isEmpty()){
            String newDesc = joinAttributeDesc(skuCreateReqParam);
            if(!newDesc.equals(skuEntity.getAttributeDesc())){
                updatedMap.put("attribute", true);
            }
        }

        return updatedMap;
    }

    @Override
    public List<SuzakuSkuEntity> haveSkuByCategoryId(String categoryId) {
        return suzakuSkuMapper.selectSkuByCategoryId(categoryId);
    }

    @Override
    public List<String> searchSkuFuzzy(String skuName) {
        List<SuzakuSkuEntity> byNameFuzzy = suzakuSkuMapper.getByNameFuzzy(skuName);
        List<String> skuIds = byNameFuzzy.stream().map(sku -> {
            return sku.getSkuId();
        }).collect(Collectors.toList());
        return skuIds;
    }

    @Override
    public List<SuzakuSkuEntity> searchSku(String skuName) {
        List<SuzakuSkuEntity> byNameFuzzy = suzakuSkuMapper.getByNameFuzzy(skuName);
        return byNameFuzzy;
    }



    @Override
    @Transactional
    public void update(SkuCreateReqParam skuCreateReqParam) {

        //获取原始记录
        SuzakuSkuEntity skuEntityBySkuId = suzakuSkuMapper.getSuzakuSkuEntityBySkuId(skuCreateReqParam.getSkuId());
        //比较原始记录 和 修改后的记录
        Map<String, Boolean> updatedMap = complete(skuCreateReqParam, skuEntityBySkuId);
        if(updatedMap.size() >= 1){
            //检验是否有sku已经存在
            boolean skuCompleted = updatedMap.containsKey("sku");
            if(skuCompleted){
                SuzakuSkuEntity suzakuSkuEntity = suzakuSkuMapper.getByName(skuCreateReqParam);
                if(Objects.nonNull(suzakuSkuEntity)){
                    throw new SuzakuBussinesException(ResponseEnum.RESPONSE_ERROR_CODE.getCode(), "sku已经存在");
                }
            }
            boolean commonUpdated = updatedMap.containsKey("common");
            if(commonUpdated){
                SkuSelectReqParam skuSelectReqParam = new SkuSelectReqParam();
                skuSelectReqParam.setBrandName(skuCreateReqParam.getBrandName());
                skuSelectReqParam.setSupplierName(skuCreateReqParam.getSupplierName());

                SuzakuSkuEntity suzakuSkuEntity = suzakuSkuMapper.getBySelectParam(skuSelectReqParam);

                if(Objects.nonNull(suzakuSkuEntity)){
                    throw new SuzakuBussinesException(ResponseEnum.RESPONSE_ERROR_CODE.getCode(), "sku已经存在");
                }
                //规格改变 规格信息暂不支持更新
                boolean attributeUpdated = updatedMap.containsKey("attribute");
                if(attributeUpdated){
                    skuSelectReqParam.setAttributeDesc(joinAttributeDesc(skuCreateReqParam));
                } else {
                    skuSelectReqParam.setAttributeDesc(skuEntityBySkuId.getAttributeDesc());
                }
                if(attributeUpdated){
                    //编辑规格
                    Map<String, String> attributes = skuCreateReqParam.getAttributes();
                    List<String> attributeIds = new ArrayList<>(attributes.keySet());

                    //编辑规格属性信息
                    List<SuzakuClassifyCustomValueEntity> classifyCustomValueEntities = classifyCustomValueMapper.getClassifyCustom(attributeIds, null);
                    classifyCustomValueEntities = classifyCustomValueEntities.stream().filter(m->{
                        if(Objects.nonNull(m.getSkus()) && m.getSkus().contains(skuCreateReqParam.getSkuId())){
                            return true;
                        }
                        return false;
                    }).collect(Collectors.toList());

                    classifyCustomValueEntities.forEach(m->{
                        List<String> skuList = Arrays.asList(m.getSkus().split(","));
                        List<String> newSkuList = skuList.stream().filter(sku -> !sku.equals(skuCreateReqParam.getSkuId())).collect(Collectors.toList());
                        String newSkus = StringUtils.join(newSkuList, ",");
                        m.setSkus(newSkus);
                    });

                    //编辑规格
                    classifyCustomValueMapper.batchUpdate(classifyCustomValueEntities);
                    tagAttribute(skuCreateReqParam, skuCreateReqParam.getSkuId());

                }
            }
        }

        //编辑sku
        SuzakuSkuEntity skuEntity = ServiceConverter.suzakuSkuParam2SkuEntity().apply(skuCreateReqParam);
        skuEntity.setOperatorName(userHolder.getUserInfo().getName());
        if(Objects.nonNull(skuCreateReqParam.getAttributes()) && !skuCreateReqParam.getAttributes().isEmpty()){
            skuEntity.setAttributeDesc(joinAttributeDesc(skuCreateReqParam));
        }
        suzakuSkuMapper.update(skuEntity);

    }
}
