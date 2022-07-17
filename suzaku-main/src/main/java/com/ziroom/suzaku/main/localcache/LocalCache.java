package com.ziroom.suzaku.main.localcache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.ziroom.suzaku.main.entity.SuzakuBrandEntity;
import com.ziroom.suzaku.main.model.SuzakuUserInfoModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.TimeUnit;

/**
 * suzaku数据缓存
 * @author xuzeyu
 */
@Configuration
public class LocalCache {

    @Bean("EhrUserInfoCache")
    public Cache<String, SuzakuUserInfoModel> userInfoCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.DAYS)
                .maximumSize(3000)
                .build();
    }

    @Bean("BrandInfoCache")
    public Cache<Long, SuzakuBrandEntity> brandInfoCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.DAYS)
                .maximumSize(3000)
                .build();
    }

}
