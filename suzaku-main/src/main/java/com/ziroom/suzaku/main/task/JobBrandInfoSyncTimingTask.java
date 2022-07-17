package com.ziroom.suzaku.main.task;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.entity.SuzakuBrandEntity;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.suzaku.main.event.BrandInfoSyncFailEvent;
import com.ziroom.suzaku.main.event.EhrUserInfoSyncFailEvent;
import com.ziroom.suzaku.main.service.SuzakuBrandService;
import com.ziroom.suzaku.main.utils.DistinctUtils;
import com.ziroom.tech.queue.DelayQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 品牌信息同步
 * @author xuzeyu
 */
@Slf4j
public class JobBrandInfoSyncTimingTask implements Runnable {

    private SuzakuBrandService suzakuBrandService;

    private Cache BRANDCACHE;

    public JobBrandInfoSyncTimingTask(SuzakuBrandService suzakuBrandService, Cache brandCache) {
        this.suzakuBrandService = suzakuBrandService;
        this.BRANDCACHE = brandCache;
    }

    @Override
    public void run() {
        try{
            //清空品牌缓存
            BRANDCACHE.invalidateAll();

            List<SuzakuBrandEntity> brandsList = suzakuBrandService.getBrandsByCat(null);
            brandsList = brandsList.stream().filter(DistinctUtils.distinctByKey(SuzakuBrandEntity::getBrandName)).collect(Collectors.toList());

            //缓存品牌信息
            if(CollectionUtils.isNotEmpty(brandsList)){
                brandsList.forEach(brandInfo -> BRANDCACHE.put(brandInfo.getId(), brandInfo));
            }

        } catch (Exception e){
            log.error("[JobBrandInfoSyncTimingTask] run exception", e);
            log.info("[JobBrandInfoSyncTimingTask] BrandInfoSyncFailEvent insurance");
            DelayQueueUtil.getInstance().put(new BrandInfoSyncFailEvent(DelayJobEnum.SYNC_BRAND_FAIL_EVENT.getType(), null));
        } finally {
            log.info("[JobBrandInfoSyncTimingTask] run end");
        }

    }

}
