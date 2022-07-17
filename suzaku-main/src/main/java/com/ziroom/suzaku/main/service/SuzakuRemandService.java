package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuRemand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.SuzakuRemandDto;
import com.ziroom.suzaku.main.model.qo.AssertQo;
import com.ziroom.suzaku.main.model.qo.RemandQo;
import com.ziroom.suzaku.main.param.ApplyRemandReqParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-26
 */
public interface SuzakuRemandService extends IService<SuzakuRemand> {

    /**
     * 获取资产退还分页信息
     * @param qo
     * @return
     */
    PageData<SuzakuRemandDto> pageRemand(RemandQo qo);

    /**
     * 资产退还登记
     * @param applyRemandReqParam
     */
    void saveRemand(ApplyRemandReqParam applyRemandReqParam);

}
