package com.ziroom.suzaku.main.job;

import com.ziroom.tech.queue.BaseDelayed;
import com.ziroom.tech.queue.DelayQueueUtil;
import com.ziroom.tech.queue.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 延迟队列 事件处理
 * @author xuzeyu
 */
@Slf4j
@Component
public class DelayQueueJob {

    @Autowired(required = false)
    private List<EventHandler> eventHandlers;

    public static Map<Integer, EventHandler> eventHandlerMap = new HashMap<>();

    @PostConstruct
    @Order(1)
    public void init() {
        new Thread(()->{
            eventHandlers.forEach(handler -> {
                Integer type = handler.support();
                eventHandlerMap.put(type, handler);
            });
            DelayQueueUtil instance =DelayQueueUtil.getInstance();
            while (true){
                try {
                    //队列为空 等待120s
                    if(!instance.isNotEmpty()){
                        log.info("DelayQueue is empty, sleep 120s!");
                        Thread.sleep(1000*60*2);
                    }

                    if(instance.isNotEmpty()){
                        BaseDelayed delayed = instance.take();
                        //执行任务
                        EventHandler eventHandler = eventHandlerMap.get(delayed.getType());
                        if(Objects.nonNull(eventHandler)){
                            eventHandler.handleMessage(delayed.getData());
                        }

                        log.info("DelayQueue size is {}, sleep 2s!", instance.length());
                        Thread.sleep(2000);
                    }


                }  catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
}
