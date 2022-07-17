package com.ziroom.suzaku.main.event;

import com.ziroom.tech.queue.BaseDelayed;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 资产状态变更 事件
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class AssertStatusRecordAsyncEvent extends BaseDelayed {

    public AssertStatusRecordAsyncEvent(Integer type, Object data) {
        super(new Date(new Date().getTime() + 1000*60*1), type, data);
    }
}
