package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuDepot;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.SuzakuDepotDto;
import java.util.List;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-01
 */
public interface SuzakuDepotService extends IService<SuzakuDepot> {

    void addDepotInfo(List<SuzakuDepot> suzakuDepots);

    /**
     * 获取仓库信息
     * @return
     */
    List<SuzakuDepotDto> getDepot();
}
