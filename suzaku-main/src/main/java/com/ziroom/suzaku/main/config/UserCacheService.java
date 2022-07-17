package com.ziroom.suzaku.main.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.client.ehr.service.EhrService;
import com.ziroom.suzaku.main.client.uc.service.UcService;
import com.ziroom.suzaku.main.model.SuzakuUserInfoModel;
import com.ziroom.suzaku.main.task.JobEhrUserInfoSyncTimingTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * 初始化缓存用户数据
 * @author xuzeyu
 */
@Service
@Slf4j
public class UserCacheService {

    @Autowired
    private EhrService ehrService;

    @Autowired
    private UcService ucService;

    @Resource(name="EhrUserInfoCache")
    private Cache ehrUserInfoCache;

    @Resource(name = "SyncCommonThreadPool")
    private ExecutorService executorService;

    @PostConstruct
    @Order(2)
    public void init() {
        try{
            Thread.sleep(10*1000);
            executorService.execute(new JobEhrUserInfoSyncTimingTask(ehrService, ucService, ehrUserInfoCache));
        } catch (Exception e){
            log.error("[UserCacheService] init exception", e);
        }
    }

    /**
     * 获取所有用户数据
     */
    public List<SuzakuUserInfoModel> getAllUserInfoList() {
        return new ArrayList<>(ehrUserInfoCache.asMap().values());
    }

}
