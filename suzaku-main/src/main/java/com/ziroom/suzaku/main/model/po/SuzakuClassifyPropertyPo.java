package com.ziroom.suzaku.main.model.po;

import com.ziroom.suzaku.main.entity.SuzakuClassifyProperty;
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
public class SuzakuClassifyPropertyPo extends SuzakuClassifyProperty implements Serializable {

   private  List<SuzakuClassifyCustomPo> classifyCustomDtoList;

}
