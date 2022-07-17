package com.ziroom.suzaku.main.utils;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.Closeable;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.*;

/**
 * @author libingsi
 * @date 2021/6/22 10:59
 */
@Configurable
public class ThreadPoolUtil implements Closeable {

    public static final Logger logger = LoggerFactory.getLogger(ThreadPoolUtil.class);

    public static final int CORES = Runtime.getRuntime().availableProcessors();

    private static final ConcurrentHashMap<String, ThreadPoolExecutor> THREAD_POOL_EXECUTOR_CONCURRENT_HASH_MAP = new ConcurrentHashMap<>();

    public static ThreadPoolExecutor get(String name) {
        return THREAD_POOL_EXECUTOR_CONCURRENT_HASH_MAP.get(name);
    }

    public static final UncaughtExceptionHandler DEFAULT_UNCAUGHT_EXCEPTION_HANDLER =
            (t, e) -> logger.error("thread:" + t.toString() + ", " + e.getMessage(), e);

    public static class Builder {

        /** 当大于等于该值的时候根据cpu核数动态设置core和max */
        private int dynamicCount;

        /** 核心数量,默认1 */
        private int core = 1;

        /** 最大数量，默认cpu最大数量2倍 */
        private int max = core * 2;

        private int queueSize = -1;

        /** 存活时间，默认0,永久 */
        private long keepAliveTime = 0;

        /**
         * 存活时间 默认毫秒数，永久
         *
         * @see #keepAliveTime
         */
        private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

        /** 拒绝策略，默认 AbortPolicy */
        private RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();

        /** 线程池名字前缀，并且唯一标识线城池 */
        private String format;

        /**捕获异常*/
        private UncaughtExceptionHandler uncaughtExceptionHandler;

        private boolean schedular;

        public Builder setCore(int core) {
            this.core = core;
            return this;
        }

        public Builder setMax(int max) {
            this.max = max;
            return this;
        }

        public Builder setKeepAliveTime(long keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder setHandler(RejectedExecutionHandler handler) {
            this.handler = handler;
            return this;
        }

        public Builder setFormat(String format) {
            this.format = format;
            return this;
        }

        /**
         * 设置core和max根据cpu核数根据cpu核数设置
         *
         * @param dynamic 当大于等于该值的时候生效，如果小于等于0不生效
         */
        public Builder setCoresDynamic(int dynamic) {
            this.dynamicCount = dynamic;
            return this;
        }

        public Builder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.uncaughtExceptionHandler = uncaughtExceptionHandler;
            return this;
        }

        public Builder setSchedular(boolean schedular) {
            this.schedular = schedular;
            return this;
        }

        /**
         * 如果名字一样，则返回已经存在的
         *
         * @return
         */
        public synchronized ThreadPoolExecutor build() {

            String poolName = StringUtils.defaultString(format, "default-pool-thread-");
            return THREAD_POOL_EXECUTOR_CONCURRENT_HASH_MAP.computeIfAbsent(
                    poolName,
                    name -> {
                        ThreadFactoryBuilder threadFactoryBuilder =
                                new ThreadFactoryBuilder().setNamePrefix(poolName);
                        if (uncaughtExceptionHandler != null) {
                            threadFactoryBuilder.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                        }

                        if (CORES >= this.dynamicCount && this.dynamicCount > 0) {
                            core = CORES;
                            max = core * 2;
                            logger.info("thread pool {} use dynamic config, core:{}, max:{}", poolName, core, max);
                        }

                        if (core <= 0) {
                            logger.warn("thread pool {} core must be gt 0, will set to cores:{}", poolName, CORES);
                            core = CORES;
                        }

                        if (max < core) {
                            logger.warn(
                                    "thread pool {} max must be gte core, core:{}, max:{}, will set max eq core",
                                    poolName,
                                    core,
                                    max);
                        }

                        if (schedular) {
                            return new ScheduledThreadPoolExecutor(core, threadFactoryBuilder.build(), handler);
                        }

                        return new ThreadPoolExecutor(
                                core,
                                max,
                                0,
                                TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<>(queueSize == -1 ? max * 2 : queueSize),
                                threadFactoryBuilder.build(),
                                handler);
                    });
        }
    }


    @Override
    public void close() {
        THREAD_POOL_EXECUTOR_CONCURRENT_HASH_MAP.values().forEach(ThreadPoolExecutor::shutdown);
    }


}
