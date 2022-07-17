package com.ziroom.suzaku.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.entity.SuzakuAssertsLog;
import com.ziroom.suzaku.main.dao.SuzakuAssertsLogMapper;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertsLogDto;
import com.ziroom.suzaku.main.service.SuzakuAssertsLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-25
 */
@Service
public class SuzakuAssertsLogServiceImpl extends ServiceImpl<SuzakuAssertsLogMapper, SuzakuAssertsLog> implements SuzakuAssertsLogService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private SuzakuAssertsLogMapper mapper;

    @Override
    public List<SuzakuAssertsLogDto> getAssertsLog(String id) {
        LambdaQueryWrapper<SuzakuAssertsLog> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuAssertsLog::getAssertsCode,id);
        List<SuzakuAssertsLog> suzakuAssertsLogs = mapper.selectList(queryWrapper);
        return beanMapper.mapList(suzakuAssertsLogs,SuzakuAssertsLogDto.class);
    }

    @Override
    public void saveSuzakuAssertsLog(SuzakuAssertsLog entity) {
        SuzakuAssertsLog log = new SuzakuAssertsLog();
        log.setAssertsCode(entity.getAssertsCode());
        log.setAssertsName(entity.getAssertsName());
        log.setOperator(entity.getOperator());
        log.setOperatorIp(entity.getOperatorIp());
        log.setRemark(entity.getRemark());
        log.setCreateTime(LocalDateTime.now());
        log.setUpdateTime(LocalDateTime.now());
        this.save(log);
    }
}
