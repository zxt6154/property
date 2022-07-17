package com.ziroom.suzaku.main.handler;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.suzaku.main.service.SuzakuBrandService;
import com.ziroom.suzaku.main.task.JobBrandInfoSyncTimingTask;
import com.ziroom.tech.queue.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * 品牌信息同步异常处理类
 *
 * @author xuzeyu
 */
@Component
@Slf4j
public class BrandInfoSyncEventHandler implements EventHandler {

    @Autowired
    private SuzakuBrandService suzakuBrandService;

    @Resource(name="BrandInfoCache")
    private Cache brandCache;

    @Resource(name = "EventHandlerThreadPool")
    private ExecutorService executorService;

    @Override
    public Integer support() {
        return DelayJobEnum.SYNC_BRAND_FAIL_EVENT.getType();
    }

    @Override
    public void handleMessage(Object data) {
        log.info("BrandInfoSyncEventHandler start");
        executorService.execute(new JobBrandInfoSyncTimingTask(suzakuBrandService, brandCache));
        log.info("BrandInfoSyncEventHandler end.");
    }
}
