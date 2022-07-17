package com.ziroom.suzaku.main.handler;

import com.ziroom.suzaku.main.entity.SuzakuAssertsLog;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.suzaku.main.service.SuzakuAssertsLogService;
import com.ziroom.tech.queue.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资产状态变更记录处理类
 *
 * @author xuzeyu
 */
@Component
@Slf4j
public class AssertStatusRecordAsyncEventHandler implements EventHandler {

    @Autowired
    private SuzakuAssertsLogService logService;

    @Override
    public Integer support() {
        return DelayJobEnum.ASYNC_APPLYFORM_EVENT.getType();
    }

    @Override
    public void handleMessage(Object data) {
        log.info("ApplyFormRecordAsyncEventHandler start");
        SuzakuAssertsLog suzakuAssertsLog = (SuzakuAssertsLog) data;
        logService.saveSuzakuAssertsLog(suzakuAssertsLog);
        log.info("ApplyFormRecordAsyncEventHandler end.");
    }
}
