package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.entity.SuzakuCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ziroom.suzaku.main.model.dto.SuzakuAssertDto;
import com.ziroom.suzaku.main.model.dto.SuzakuCheckDto;
import com.ziroom.suzaku.main.model.qo.CheckQo;
import com.ziroom.suzaku.main.param.AssertSelectReqParam;
import com.ziroom.suzaku.main.param.LaunchCheckReqParam;

/**
 * <p>
 * 盘点任务 服务类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
public interface SuzakuCheckService extends IService<SuzakuCheck> {


    /**
     * 获取资产盘点计划信息
     * @param qo
     * @return
     */
    PageData<SuzakuCheckDto> pageCheck(CheckQo qo);

    /**
     * 发起资产盘点计划
     * @param launchCheckReqParam
     */
    void launchCheck(LaunchCheckReqParam launchCheckReqParam);

    /**
     * 查询盘点资产
     * @param assertSelectReqParam
     * @return
     */
    PageData<SuzakuAssertDto> pageGetAssert(AssertSelectReqParam assertSelectReqParam);

    /**
     * 定时更新盘点任务状态 未开始-》进行中
     */
    void startCheck();
}
