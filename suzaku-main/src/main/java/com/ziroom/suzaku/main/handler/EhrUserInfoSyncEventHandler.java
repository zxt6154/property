package com.ziroom.suzaku.main.handler;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.client.ehr.service.EhrService;
import com.ziroom.suzaku.main.client.uc.service.UcService;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.suzaku.main.task.JobEhrUserInfoSyncTimingTask;
import com.ziroom.tech.queue.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * Ehr用户信息同步异常处理类
 *
 * @author xuzeyu
 */
@Component
@Slf4j
public class EhrUserInfoSyncEventHandler implements EventHandler {

    @Autowired
    private EhrService ehrService;

    @Autowired
    private UcService ucService;

    @Resource(name="EhrUserInfoCache")
    private Cache ehrUserInfoCache;

    @Resource(name = "EventHandlerThreadPool")
    private ExecutorService executorService;

    @Override
    public Integer support() {
        return DelayJobEnum.SYNC_EHR_FAIL_EVENT.getType();
    }

    @Override
    public void handleMessage(Object data) {
        log.info("EhrUserInfoSyncEventHandler start");
        executorService.execute(new JobEhrUserInfoSyncTimingTask(ehrService, ucService, ehrUserInfoCache));
        log.info("EhrUserInfoSyncEventHandler end.");
    }
}
