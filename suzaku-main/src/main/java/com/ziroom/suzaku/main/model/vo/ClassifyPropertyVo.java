package com.ziroom.suzaku.main.model.vo;

import com.ziroom.suzaku.main.entity.SuzakuClassifyProperty;
import com.ziroom.suzaku.main.model.po.SuzakuClassifyCustomPo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 资产分类表
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Getter
@Setter
public class ClassifyPropertyVo extends SuzakuClassifyProperty implements Serializable {

   private  List<SuzakuClassifyCustomVo> customVos;

}
