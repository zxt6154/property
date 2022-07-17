package com.ziroom.suzaku.main.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.entity.SuzakuBrandEntity;
import com.ziroom.suzaku.main.service.SuzakuBrandService;
import com.ziroom.suzaku.main.task.JobBrandInfoSyncTimingTask;
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
 * 初始化缓存品牌数据
 * @author xuzeyu
 */
@Service
@Slf4j
public class BrandCacheService {

    @Autowired
    private SuzakuBrandService suzakuBrandService;

    @Resource(name="BrandInfoCache")
    private Cache brandCache;

    @Resource(name = "SyncCommonThreadPool")
    private ExecutorService executorService;

    @PostConstruct
    @Order(3)
    public void init() {
        try{
            Thread.sleep(10*1000);
            executorService.execute(new JobBrandInfoSyncTimingTask(suzakuBrandService, brandCache));
        } catch (Exception e){
            log.error("[BrandCacheService] init exception", e);
        }
    }

    /**
     * 获取品牌数据
     */
    public List<SuzakuBrandEntity> getBrandInfoList() {
        return new ArrayList<>(brandCache.asMap().values());
    }

}
