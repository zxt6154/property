package com.ziroom.suzaku.main.service.impl;

import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.entity.SuzakuDepot;
import com.ziroom.suzaku.main.dao.SuzakuDepotMapper;
import com.ziroom.suzaku.main.model.dto.SuzakuDepotDto;
import com.ziroom.suzaku.main.service.SuzakuDepotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-01
 */
@Service
public class SuzakuDepotServiceImpl extends ServiceImpl<SuzakuDepotMapper, SuzakuDepot> implements SuzakuDepotService {

    @Resource
    private SuzakuDepotMapper suzakuDepotMapper;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public void addDepotInfo(List<SuzakuDepot> suzakuDepots) {
        suzakuDepotMapper.insert(suzakuDepots);
    }

    @Override
    public List<SuzakuDepotDto> getDepot() {
        List<SuzakuDepot> bookCatalogPoList = suzakuDepotMapper.selectList(null);
        return beanMapper.mapList(bookCatalogPoList,SuzakuDepotDto.class);
    }
}
