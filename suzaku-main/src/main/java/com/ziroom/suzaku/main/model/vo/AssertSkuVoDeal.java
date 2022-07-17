package com.ziroom.suzaku.main.model.vo;

import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.entity.SuzakuSkuEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssertSkuVoDeal {

    private SuzakuImportAsserts importAssert;

    private SuzakuSkuEntity skuEntity;
}
