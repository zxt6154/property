package com.ziroom.suzaku.main.job;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.client.ehr.service.EhrService;
import com.ziroom.suzaku.main.client.uc.service.UcService;
import com.ziroom.suzaku.main.service.SuzakuBrandService;
import com.ziroom.suzaku.main.task.JobBrandInfoSyncTimingTask;
import com.ziroom.suzaku.main.task.JobEhrUserInfoSyncTimingTask;
import com.ziroom.tech.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;

/**
 * suzaku信息数据同步job
 * @author xuzeyu
 */
@Component
@Slf4j
@ConditionalOnExpression("${suzaku.job.enable:false}")
public class SuzakuSyncTiming {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EhrService ehrService;

    @Autowired
    private UcService ucService;

    @Autowired
    private SuzakuBrandService suzakuBrandService;

    @Resource(name="BrandInfoCache")
    private Cache brandCache;

    @Resource(name="EhrUserInfoCache")
    private Cache ehrUserInfoCache;

    @Resource(name = "SyncCommonThreadPool")
    private ExecutorService executorService;

    private final String SUZAKU_USERINFO_LOCK = "tech:suzaku:lock:ehr";

    private final String SUZAKU_BRANDINFO_LOCK = "tech:suzaku:lock:brand";


    /**
     * 同步用户信息
     * 每天0点执行一次
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void runSyncEhrUserInfoDataTiming() {
        try{
            Boolean aBoolean = redisUtil.tryLockLua(SUZAKU_USERINFO_LOCK, "mr.xu", 60);
            if(aBoolean){
                log.info("[runSyncEhrUserInfoDataTiming] start");
                executorService.execute(new JobEhrUserInfoSyncTimingTask(ehrService, ucService, ehrUserInfoCache));
            }
        }catch (Exception e){
            log.error("[runSyncEhrUserInfoDataTiming] exception", e);
        }
    }

    /**
     * 同步品牌信息
     * 每天0点执行一次
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void runSyncBrandInfoDataTiming() {
        try{
            Boolean aBoolean = redisUtil.tryLockLua(SUZAKU_BRANDINFO_LOCK, "mr.xu", 60);
            if(aBoolean){
                log.info("[runSyncBrandInfoDataTiming] start");
                executorService.execute(new JobBrandInfoSyncTimingTask(suzakuBrandService, brandCache));
            }
        }catch (Exception e){
            log.error("[runSyncBrandInfoDataTiming] exception", e);
        }
    }

}

