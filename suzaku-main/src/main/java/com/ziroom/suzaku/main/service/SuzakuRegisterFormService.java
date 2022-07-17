package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.model.dto.SuzakuApplyFormDto;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.param.ApplyFormAddReqParam;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.ApplyFormSelectReqParam;

/**
 * 资产领借登记
 * @author xuzeyu
 */
public interface SuzakuRegisterFormService {

    PageData<SuzakuApplyFormDto> page(ApplyFormSelectReqParam reqParam);

    PageData<SuzakuAssertDto> pageGetLeaveAssert(AssertSelectReqParam assertSelectReqParam);

    void assertAdd(ApplyFormAddReqParam applyFormAddReqParam);
}
