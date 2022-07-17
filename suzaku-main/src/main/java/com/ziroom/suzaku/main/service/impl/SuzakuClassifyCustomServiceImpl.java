package com.ziroom.suzaku.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.entity.SuzakuClassifyCustom;
import com.ziroom.suzaku.main.dao.SuzakuClassifyCustomMapper;
import com.ziroom.suzaku.main.model.dto.SuzakuClassifyCustomDto;
import com.ziroom.suzaku.main.service.SuzakuClassifyCustomService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 资产分类扩展属性表 服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-09
 */
@Service
public class SuzakuClassifyCustomServiceImpl extends ServiceImpl<SuzakuClassifyCustomMapper, SuzakuClassifyCustom> implements SuzakuClassifyCustomService {


    @Resource
    private SuzakuClassifyCustomMapper classifyCustomMapper;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public List<SuzakuClassifyCustomDto> getClassifyCustomByClassifyId(String classifyId) {
        LambdaQueryWrapper<SuzakuClassifyCustom> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuClassifyCustom::getClassifyId,classifyId);
        List<SuzakuClassifyCustom> suzakuClassifyCustoms = classifyCustomMapper.selectList(queryWrapper);
        return beanMapper.mapList(suzakuClassifyCustoms,SuzakuClassifyCustomDto.class);
    }

    @Override
    public SuzakuClassifyCustomDto getClassifyCustomById(String id) {
//        LambdaQueryWrapper<SuzakuClassifyCustom> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(SuzakuClassifyCustom::getId,Id);
        SuzakuClassifyCustom suzakuClassifyCustoms = classifyCustomMapper.selectById(id);
        return beanMapper.map(suzakuClassifyCustoms,SuzakuClassifyCustomDto.class);
    }

}
