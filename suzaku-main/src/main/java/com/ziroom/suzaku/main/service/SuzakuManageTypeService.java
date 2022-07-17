package com.ziroom.suzaku.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.entity.SuzakuManageType;
import java.util.List;

/**
 * 管理类型
 * @author xuzeyu
 */
public interface SuzakuManageTypeService extends IService<SuzakuManageType> {

    List<SuzakuManageType> getManagerType();
}
