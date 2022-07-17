package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuCheckItem;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.CheckSkuDto;
import com.ziroom.suzaku.main.model.dto.SuzakuCheckItemDto;
import com.ziroom.suzaku.main.model.qo.CheckItemQo;
import com.ziroom.suzaku.main.model.vo.CheckItemNukeVo;
import com.ziroom.suzaku.main.model.vo.CheckSkuVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
public interface SuzakuCheckItemService extends IService<SuzakuCheckItem> {

    /**
     * 获取资产盘点明细信息
     * @param qo
     * @return
     */
    PageData<SuzakuCheckItemDto> pageCheckItem(CheckItemQo qo);


    /**
     * 获取盘点结果信息
     * @param id
     * @return
     */
    CheckSkuDto getCheckItemInfo(String id);

    /**
     * 审核盘点单明细
     * @param vo
     */
    void updateCheckItemStatus(CheckSkuVo vo);

    /**
     * 审核盘点单明细状态及资产信息
     * @param checkSkuVo
     */
    void updateCheckItem(CheckSkuVo checkSkuVo);
}
