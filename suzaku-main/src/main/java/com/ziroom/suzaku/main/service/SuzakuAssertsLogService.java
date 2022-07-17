package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.entity.SuzakuAssertsLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertsLogDto;
import com.ziroom.suzaku.main.model.vo.SuzakuAssertsLogVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-25
 */
public interface SuzakuAssertsLogService extends IService<SuzakuAssertsLog> {

    /**
     * 获取资产操作log
     * @param id
     * @return
     */
    List<SuzakuAssertsLogDto> getAssertsLog(String id);

    /**
     * 保存
     * @param log
     */
    void saveSuzakuAssertsLog(SuzakuAssertsLog log);
}
