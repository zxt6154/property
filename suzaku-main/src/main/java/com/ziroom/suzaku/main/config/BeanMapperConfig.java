package com.ziroom.suzaku.main.config;

import com.github.dozermapper.core.Mapper;
import com.ziroom.suzaku.main.common.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author libingsi
 * @date 2021/6/22 19:22
 */
@Configuration
public class BeanMapperConfig {

    @Autowired
    private Mapper mapper;

    @Bean
    public BeanMapper beanMapper(Mapper mapper)
    {
        return new BeanMapper(mapper);
    }
}
