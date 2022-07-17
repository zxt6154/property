package com.ziroom.suzaku.main.job;

import com.ziroom.suzaku.main.service.SuzakuCheckService;
import com.ziroom.tech.sia.hunter.annotation.OnlineTask;
import com.ziroom.tech.sia.hunter.helper.JSONHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author libingsi
 * @date 2021/11/13 14:31
 */
@RestController
@RequestMapping("/task")
@Slf4j
public class StartCheckJobController {


    @Autowired
    private SuzakuCheckService suzakuCheckService;

//    @OnlineTask(description = "每天10点半更新盘点数据，开始盘点任务")
    @RequestMapping(value = "/handler/startCheck", method = RequestMethod.POST)
    @CrossOrigin(methods = RequestMethod.POST, origins = "*")
    @ResponseBody
    public String startCheck(@RequestBody String json) {
        Map<String, String> info = new HashMap<>(4);
        log.info("开始扫描未开始的盘点任务 》》开始执行,当前时间：{}》》》》》》》》》》" , System.currentTimeMillis());
        suzakuCheckService.startCheck();
        log.info("盘点任务扫描完成 》》完成执行,完成时间：{}》》》》》》》》》" , System.currentTimeMillis());
        info.put("status", "success");
        info.put("result", "ok");
        return JSONHelper.toString(info);
    }


}
