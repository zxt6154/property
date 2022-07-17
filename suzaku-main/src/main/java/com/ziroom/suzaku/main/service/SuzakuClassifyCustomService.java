package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuClassifyCustom;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;

import java.util.List;

/**
 * <p>
 * 资产分类扩展属性表 服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
public interface SuzakuClassifyCustomService extends IService<SuzakuClassifyCustom> {


    List<SuzakuClassifyCustomDto> getClassifyCustomByClassifyId(String classifyId);

    SuzakuClassifyCustomDto getClassifyCustomById(String Id);

}
