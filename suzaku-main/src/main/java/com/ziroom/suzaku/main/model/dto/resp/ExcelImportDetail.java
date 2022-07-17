package com.ziroom.suzaku.main.model.dto.resp;

import com.ziroom.suzaku.main.model.dto.req.ExcelImportAssertReq;
import com.ziroom.suzaku.main.model.vo.AssertSkuVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//上传资产明细
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelImportDetail {

    private String success;

    private String faild;

    private String all;
    //successFlag用来填写校验失败信息

    private List<AssertSkuVo> assertSkuVo;

    private List<ExcelImportAssertReq> assertSkuVoFaild;

}
