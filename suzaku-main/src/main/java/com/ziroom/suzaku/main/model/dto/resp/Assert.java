package com.ziroom.suzaku.main.model.dto.resp;

import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@ApiModel(value="新增物料对象集合", description="")
public class Assert extends SuzakuImportAsserts{

}
