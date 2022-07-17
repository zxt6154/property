package com.ziroom.suzaku.main.config;

import com.ziroom.tech.http.HttpClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http调用配置类
 * @author xuzeyu
 */
@Configuration
public class HttpConfig {

    @Bean
    public HttpClientConfig httpClientConfig(){
        return new HttpClientConfig("com.ziroom.suzaku.main");
    }

}
