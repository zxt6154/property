package com.ziroom.suzaku.main.converter;

import com.ziroom.suzaku.main.entity.*;
import com.ziroom.suzaku.main.model.dto.SuzakuSkuDto;
import com.ziroom.suzaku.main.param.ApplyFormAddReqParam;
import com.ziroom.suzaku.main.param.ApplyRemandReqParam;
import com.ziroom.suzaku.main.param.SkuCreateReqParam;
import com.ziroom.suzaku.main.utils.UUIDUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * service 转换器
 * @author xuzeyu
 */
public class ServiceConverter {

    /**
     * skuParam to skuEntity
     * @return
     */
    public static Function<SkuCreateReqParam, SuzakuSkuEntity> suzakuSkuParam2SkuEntity() {
        return param -> {
            SuzakuSkuEntity suzakuSkuEntity = new SuzakuSkuEntity();
            suzakuSkuEntity.setSkuId(param.getSkuId());
            suzakuSkuEntity.setSkuName(param.getSkuName());
            suzakuSkuEntity.setSkuDesc(param.getSkuDesc());
            suzakuSkuEntity.setCategoryId(param.getCategoryId());
            suzakuSkuEntity.setBrandName(param.getBrandName());
            suzakuSkuEntity.setSupplierName(param.getSupplierName());
            suzakuSkuEntity.setSkuType(param.getSkuType());
            suzakuSkuEntity.setUnit(param.getUnit());
            suzakuSkuEntity.setPeriod(param.getPeriod());
            suzakuSkuEntity.setThreshold(param.getThreshold());
            suzakuSkuEntity.setOperatorName(param.getOperatorName());
            return suzakuSkuEntity;
        };
    }

    /**
     * skuParam to skuEntity
     * @return
     */
    public static Function<SuzakuSkuEntity, SuzakuSkuDto> suzakuSkuEntity2SkuDto() {
        return skuEntity -> {
            SuzakuSkuDto suzakuSkuDto = new SuzakuSkuDto();
            suzakuSkuDto.setSkuName(skuEntity.getSkuName());
            suzakuSkuDto.setSkuDesc(skuEntity.getSkuDesc());
            suzakuSkuDto.setCategoryId(skuEntity.getCategoryId());
            suzakuSkuDto.setCategoryName(skuEntity.getCategoryName());
            suzakuSkuDto.setBrandName(skuEntity.getBrandName());
            suzakuSkuDto.setSupplierName(skuEntity.getSupplierName());
            suzakuSkuDto.setSkuType(skuEntity.getSkuType());
            suzakuSkuDto.setUnit(skuEntity.getUnit());
            suzakuSkuDto.setPeriod(skuEntity.getPeriod());
            suzakuSkuDto.setThreshold(skuEntity.getThreshold());
            return suzakuSkuDto;
        };
    }

    /**
     * applyForm to applyEntity
     * @return
     */
    public static Function<ApplyFormAddReqParam, SuzakuRegisterFormEntity> suzakuApplyForm2ApplyEntity() {
        return applyFormAddReqParam -> {
            SuzakuRegisterFormEntity registerFormEntity = new SuzakuRegisterFormEntity();
            if(applyFormAddReqParam.getType()==1){
                registerFormEntity.setOrderId("LY"+ UUIDUtil.getUID());
            }else {
                registerFormEntity.setOrderId("JY"+UUIDUtil.getUID());
            }
            registerFormEntity.setOrderType(applyFormAddReqParam.getType());
            registerFormEntity.setOrderDesc(applyFormAddReqParam.getDesc());
            registerFormEntity.setReturnDate(applyFormAddReqParam.getReturnDate());
            registerFormEntity.setUserName(applyFormAddReqParam.getUserName());
            registerFormEntity.setUserNo(applyFormAddReqParam.getUserNo());
            registerFormEntity.setDeparment(applyFormAddReqParam.getDepartment());
            registerFormEntity.setOperateName(applyFormAddReqParam.getOperateName());
            registerFormEntity.setManagerType(applyFormAddReqParam.getManagerType());
            registerFormEntity.setStationName(applyFormAddReqParam.getStationName());
            registerFormEntity.setStatus(3);
            return registerFormEntity;
        };
    }

    /**
     * applyFormItem to applyEntityItem
     * @return
     */
    public static Function<ApplyFormAddReqParam.AssertModel, SuzakuRegisterFormItemEntity> suzakuApplyFormItem2ApplyItemEntity() {
        return assertModel -> {
            SuzakuRegisterFormItemEntity suzakuRegisterFormItemEntity = new SuzakuRegisterFormItemEntity();
            suzakuRegisterFormItemEntity.setApplyOrderId(assertModel.getApplyOrderId());
            suzakuRegisterFormItemEntity.setAssertsCode(assertModel.getAssertCode());
            suzakuRegisterFormItemEntity.setOldAssertsCode(assertModel.getOldAssertCode());
            suzakuRegisterFormItemEntity.setSkuId(assertModel.getSkuId());
            suzakuRegisterFormItemEntity.setSkuName(assertModel.getSkuName());
            suzakuRegisterFormItemEntity.setCategoryId(assertModel.getCategoryId());
            suzakuRegisterFormItemEntity.setCategory(assertModel.getCategoryName());
            suzakuRegisterFormItemEntity.setBrandName(assertModel.getBrandName());
            suzakuRegisterFormItemEntity.setSupplierName(assertModel.getSupplierName());
            return suzakuRegisterFormItemEntity;
        };
    }


    /**
     * ApplyFormAddReqParam to List<SuzakuImportAsserts>
     * @return
     */
    public static Function<ApplyFormAddReqParam, List<SuzakuImportAsserts>> suzakuApplyForm2AssertEntity() {
        return applyFormAddReqParam -> {
            return applyFormAddReqParam.getAssertModels().stream().map(m->{
                SuzakuImportAsserts suzakuImportAsserts = new SuzakuImportAsserts();
                suzakuImportAsserts.setAssertsCode(m.getAssertCode());
                suzakuImportAsserts.setAssetsState(applyFormAddReqParam.getType()==1?3:4);
                suzakuImportAsserts.setGroupId(1);
                suzakuImportAsserts.setUsePeople(applyFormAddReqParam.getUserName());
                suzakuImportAsserts.setUseDepartment(applyFormAddReqParam.getDepartment());
                return suzakuImportAsserts;
            }).collect(Collectors.toList());
        };
    }


}
