package com.ziroom.suzaku.main.event;

import com.ziroom.tech.queue.BaseDelayed;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * ehr用户信息同步异常 处理事件
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class EhrUserInfoSyncFailEvent extends BaseDelayed {

    public EhrUserInfoSyncFailEvent(Integer type, Object data) {
        super(new Date(new Date().getTime() + 1000*60*5), type, data);
    }
}
