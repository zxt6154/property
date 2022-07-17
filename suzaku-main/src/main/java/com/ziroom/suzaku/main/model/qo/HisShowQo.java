package com.ziroom.suzaku.main.model.qo;

import com.ziroom.suzaku.main.entity.SuzakuDealBill;
import com.ziroom.suzaku.main.param.PageCommonParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HisShowQo extends PageCommonParam implements Serializable {
    private static final long serialVersionUID = 1L;
    private SuzakuDealBill dealBill;
}
