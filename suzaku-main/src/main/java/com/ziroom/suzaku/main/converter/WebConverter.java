package com.ziroom.suzaku.main.converter;

import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.model.vo.SuzakuClassifyCustomVo;
import com.ziroom.suzaku.main.model.vo.SuzakuSkuVo;

import java.util.function.Function;

/**
 * VO 转换器
 * @author xuzeyu
 */
public class WebConverter {

    /**
     *  classifyCustomDto to classifyCustomVo
     * @return
     */
    public static Function<SuzakuClassifyCustomDto, SuzakuClassifyCustomVo> suzakuClassifyCustomDto2ClassifyCustomVO() {
        return customDto -> {
            SuzakuClassifyCustomVo suzakuClassifyCustomVo = new SuzakuClassifyCustomVo();
            suzakuClassifyCustomVo.setRequired(customDto.getRequiredFlag()==0);
            suzakuClassifyCustomVo.setId(customDto.getId());
            suzakuClassifyCustomVo.setClassifyId(customDto.getClassifyId());
            suzakuClassifyCustomVo.setCustomName(customDto.getCustomName());
            return suzakuClassifyCustomVo;
        };
    }

    /**
     *  suzakuSkuEntity to suzakuSkuVo
     * @return
     */
    public static Function<SuzakuSkuEntity, SuzakuSkuVo> suzakuSuzakuSkuEntity2SuzakuSkuVo() {
        return skuEntity -> {
            SuzakuSkuVo suzakuSkuVo = new SuzakuSkuVo();
            suzakuSkuVo.setSkuName(skuEntity.getSkuName());
            suzakuSkuVo.setSkuDesc(skuEntity.getSkuDesc());
            suzakuSkuVo.setCategoryId(skuEntity.getCategoryId());
            suzakuSkuVo.setCategoryName(skuEntity.getCategoryName());
            suzakuSkuVo.setBrandName(skuEntity.getBrandName());
            suzakuSkuVo.setSupplierName(skuEntity.getSupplierName());
            suzakuSkuVo.setSkuType(skuEntity.getSkuType());
            suzakuSkuVo.setUnit(skuEntity.getUnit());
            suzakuSkuVo.setPeriod(skuEntity.getPeriod());
            suzakuSkuVo.setThreshold(skuEntity.getThreshold());
            suzakuSkuVo.setAttributeDesc(skuEntity.getAttributeDesc());
            return suzakuSkuVo;
        };
    }


}
