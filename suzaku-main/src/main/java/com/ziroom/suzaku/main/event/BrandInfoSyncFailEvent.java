package com.ziroom.suzaku.main.event;

import com.ziroom.tech.queue.BaseDelayed;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 品牌信息同步异常 处理事件
 * @author xuzeyu
 */
@Data
@NoArgsConstructor
public class BrandInfoSyncFailEvent extends BaseDelayed {

    public BrandInfoSyncFailEvent(Integer type, Object data) {
        super(new Date(new Date().getTime() + 1000*60*5), type, data);
    }
}
