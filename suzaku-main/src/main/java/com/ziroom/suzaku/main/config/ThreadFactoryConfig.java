package com.ziroom.suzaku.main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池
 * @author xuzeyu
 */
@Configuration
public class ThreadFactoryConfig {

    /**
     * common
     */
    @Bean("SyncCommonThreadPool")
    public ExecutorService SyncCommonThreadPool(){
        return new ThreadPoolExecutor(2, 15,
                200L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                (r, executor) -> {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * eventHandler
     */
    @Bean("EventHandlerThreadPool")
    public ExecutorService EventHandlerThreadPool(){
        return new ThreadPoolExecutor(0, 10,
                200L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                (r, executor) -> {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    }

}
