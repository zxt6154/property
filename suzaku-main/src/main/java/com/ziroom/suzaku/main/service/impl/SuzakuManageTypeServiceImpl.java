package com.ziroom.suzaku.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.dao.SuzakuManageTypeMapper;
import com.ziroom.suzaku.main.entity.SuzakuManageType;
import com.ziroom.suzaku.main.service.SuzakuManageTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 管理类型
 * @author xuzeyu
 */
@Service
public class SuzakuManageTypeServiceImpl extends ServiceImpl<SuzakuManageTypeMapper, SuzakuManageType> implements SuzakuManageTypeService {

    @Autowired
    SuzakuManageTypeMapper suzakuManageTypeMapper;


    /**
     * 查询管理类型列表
     */
    @Override
    public List<SuzakuManageType> getManagerType() {
        return suzakuManageTypeMapper.getManagerType();
    }
}
