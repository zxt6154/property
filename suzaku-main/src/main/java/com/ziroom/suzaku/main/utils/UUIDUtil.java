package com.ziroom.suzaku.main.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author libingsi
 * @date 2021/6/22 10:59
 */
public class UUIDUtil {

    private static AtomicLong atomicTimeMills = new AtomicLong(0);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddHHmmssSSS");


    /**
     * 获取不重复的唯一id 支持并发(13位时间戳)
     * @return
     */
    public static String getUID() {
        while (true) {
            long currentMill = atomicTimeMills.get();
            Long currentTimeMillis = Long.parseLong(LocalDateTime.now().format(formatter));
            if (currentTimeMillis > currentMill && atomicTimeMills.compareAndSet(currentMill, currentTimeMillis)) {
                return currentTimeMillis.toString();
            }
        }
    }

    /**
     * 获取id
     *
     * @return 32位uuid
     */
    public static String getId() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }


    public static void main(String[] args) {
        for (int i = 0; i < 200; i++) {
            new Thread(()->{
                System.out.println(getUID());
            }).start();
        }
    }

}
