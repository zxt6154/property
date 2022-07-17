package com.ziroom.suzaku.main;

import com.ziroom.framework.apollo.spring.annotation.EnableApolloConfig;
import com.ziroom.tech.boot.RetrofitServiceScan;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.ziroom.tech.sia", "com.ziroom.suzaku.*"})
@RetrofitServiceScan("com.ziroom.suzaku.main")
@ComponentScan(basePackages = { "com.ziroom" })
@MapperScan("com.ziroom.suzaku.*.dao")
//@EnableApolloConfig
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
