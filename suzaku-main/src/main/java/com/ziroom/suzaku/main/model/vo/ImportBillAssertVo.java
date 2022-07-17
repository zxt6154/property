package com.ziroom.suzaku.main.model.vo;

import com.ziroom.suzaku.main.entity.SuzakuImportBill;
import com.ziroom.suzaku.main.model.dto.resp.CreateBill;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImportBillAssertVo {

    private CreateBill suzakuImportBill;

    private List<AssertSkuVo> asserts;
}
