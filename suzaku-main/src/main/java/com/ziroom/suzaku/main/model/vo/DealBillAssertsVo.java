package com.ziroom.suzaku.main.model.vo;

import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DealBillAssertsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private SuzakuDealBill suzakuDealBill;

    private List<SuzakuImportAsserts> asserts;
}
