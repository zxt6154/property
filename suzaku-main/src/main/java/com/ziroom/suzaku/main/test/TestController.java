package com.ziroom.suzaku.main.test;
import com.google.common.collect.Lists;
import java.util.HashMap;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.client.ehr.model.EhrUserInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.param.EhrUserInfoReqParam;
import com.ziroom.suzaku.main.client.ehr.service.EhrService;
import com.ziroom.suzaku.main.client.uc.service.UcService;
import com.ziroom.suzaku.main.config.UserCacheService;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTaskCardModel;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextCardModel;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextModel;
import com.ziroom.suzaku.main.message.service.WechatAppMsgSendService;
import com.ziroom.suzaku.main.model.SuzakuUserInfoModel;
import com.ziroom.suzaku.main.task.JobEhrUserInfoSyncTimingTask;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author xuzeyu
 */
@Controller
@RequestMapping("/http")
@Slf4j
public class TestController {

    @Autowired
    private EhrService ehrService;

    @Autowired
    private UcService ucService;

    @Resource(name="EhrUserInfoCache")
    private Cache ehrUserInfoCache;

    @Autowired
    private UserCacheService userCacheService;

    @Autowired
    private WechatAppMsgSendService wechatAppMsgSendService;

    @Resource(name = "SyncCommonThreadPool")
    private ExecutorService executorService;


    /**
     * 测试 获取用户信息
     * @return
     */
    @RequestMapping(value = "/testGetEhrUserTotal", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<Integer> testGetEhrUserTotal(){
        try{
            Integer totalCount = ehrService.getTotalCount();
            return ModelResponseUtil.ok(totalCount);
        }catch (Exception e){
            log.error("testGetEhrUserTotal exception", e);
            return ModelResponseUtil.ok(0);
        }
    }

    /**
     * 测试 分页调用 ehr 获取用户信息
     * @param total
     * @return
     */
    @RequestMapping(value = "/testGetEhrUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<List<EhrUserInfoRespModel>> testGetEhrUserInfo(Integer total){
        try{
            int page = total % 100 == 0 ? total / 100 : total / 100 + 1;
            EhrUserInfoReqParam ehrUserInfoReqParam = new EhrUserInfoReqParam();
            List<EhrUserInfoRespModel> userInfoList = IntStream.rangeClosed(1, page).mapToObj(curPage -> {
                ehrUserInfoReqParam.setPage(curPage);
                ehrUserInfoReqParam.setSize(100);
                return ehrService.getUserSimple(ehrUserInfoReqParam);
            }).flatMap(Collection::stream).collect(toList());
            return ModelResponseUtil.ok(userInfoList);
        }catch (Exception e){
            log.error("testGetEhrUserInfo exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试 获取缓存中用户
     * @return
     */
    @RequestMapping(value = "/testGetUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<List<SuzakuUserInfoModel>> testGetUserInfo(){
        try{
            List<SuzakuUserInfoModel> allUserInfoList = userCacheService.getAllUserInfoList();
            return ModelResponseUtil.ok(allUserInfoList);
        }catch (Exception e){
            log.error("testGetUserInfo exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试 获取缓存中用户数量
     * @return
     */
    @RequestMapping(value = "/testGetCacheCount", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<Integer> testCacheUser(){
        try{
            List<SuzakuUserInfoModel> allUserInfoList = userCacheService.getAllUserInfoList();
            return ModelResponseUtil.ok(allUserInfoList.size());
        }catch (Exception e){
            log.error("testCacheUser exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试 缓存用户信息
     * @return
     */
    @RequestMapping(value = "/testCacheUser", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<String> testGetCacheCount(){
        try{
            executorService.execute(new JobEhrUserInfoSyncTimingTask(ehrService, ucService, ehrUserInfoCache));
            return ModelResponseUtil.ok("success");
        }catch (Exception e){
            log.error("testGetCacheCount exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试text类型消息发送
     * @return
     */
    @RequestMapping(value = "/textSend", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<String> textSend(){
        try{
            HashMap<String,String> data = new HashMap<>();
            data.put("userName","徐泽宇");
            data.put("userNo","60033587");
            data.put("date","2021-11-05");
            data.put("manageType","IT资产");
            data.put("assert","MacBook pro");
            WechatMsgAppTextModel wechatMsgAppTextModel = new WechatMsgAppTextModel();
            wechatMsgAppTextModel.setData(data);
            wechatMsgAppTextModel.setToUser(Collections.singletonList("zhangxt3"));
            wechatMsgAppTextModel.setModelCode("WW-1636095451");
            wechatAppMsgSendService.textMsgSend(wechatMsgAppTextModel);
            return ModelResponseUtil.ok("success");
        }catch (Exception e){
            log.error("adviceManager exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试text类型消息发送
     * @return
     */
    @RequestMapping(value = "/textSend1", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<String> textSend1(){
        try{
            HashMap<String,String> data = new HashMap<>();
            data.put("content","测试消息");
            WechatMsgAppTextModel wechatMsgAppTextCardModel = new WechatMsgAppTextModel();
            wechatMsgAppTextCardModel.setData(data);
            wechatMsgAppTextCardModel.setToUser(Collections.singletonList("xuzy5"));
            wechatMsgAppTextCardModel.setModelCode("WW-1612350904");
            wechatAppMsgSendService.textMsgSend(wechatMsgAppTextCardModel);
            return ModelResponseUtil.ok("success");
        }catch (Exception e){
            log.error("adviceManager exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试textCard类型消息发送
     * @return
     */
    @RequestMapping(value = "/textCard", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<String> textCardSend(){
        try{
            WechatMsgAppTextCardModel wechatMsgAppTextCardModel = new WechatMsgAppTextCardModel();
            wechatMsgAppTextCardModel.setTitle("领奖通知");
            wechatMsgAppTextCardModel.setDescription("<div class=\\\"gray\\\">2021年11月5日</div> <div class=\\\"normal\\\">恭喜你抽中iPhone 13pro一台</div>");
            wechatMsgAppTextCardModel.setUrl("http://www.ziroom.com");
            wechatMsgAppTextCardModel.setBtntxt("更多");
            wechatMsgAppTextCardModel.setToUser(Collections.singletonList("xuzy5"));
            wechatMsgAppTextCardModel.setModelCode("WW-1612350904");
            wechatAppMsgSendService.textCardMsgSend(wechatMsgAppTextCardModel);
            return ModelResponseUtil.ok("success");
        }catch (Exception e){
            log.error("adviceManager exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试taskCard类型消息发送
     * @return
     */
    @RequestMapping(value = "/taskCard", method = RequestMethod.POST)
    @ResponseBody
    public ModelResponse<String> taskCard(){
        try{
            WechatMsgAppTaskCardModel wechatMsgAppTextCardModel = new WechatMsgAppTaskCardModel();
            wechatMsgAppTextCardModel.setTitle("赵明登的礼物申请");
            wechatMsgAppTextCardModel.setDescription("");
            wechatMsgAppTextCardModel.setUrl("");
            wechatMsgAppTextCardModel.setTaskId("");
            wechatMsgAppTextCardModel.setButtonList(null);
            wechatMsgAppTextCardModel.setToUser(Collections.singletonList("xuzy5"));
            wechatMsgAppTextCardModel.setModelCode("");
            wechatAppMsgSendService.taskCardMsgSend(wechatMsgAppTextCardModel);
            return ModelResponseUtil.ok("success");
        }catch (Exception e){
            log.error("adviceManager exception", e);
            return ModelResponseUtil.ok(null);
        }
    }

    /**
     * 测试text类型消息发送
     * @return
     */
//    @RequestMapping(value = "/textSend", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelResponse<String> textSend(){
//        try{
//            HashMap<String,String> data = new HashMap<>();
//            data.put("userName","徐泽宇");
//            data.put("userNo","60033587");
//            data.put("date","2021-11-05");
//            data.put("manageType","IT资产");
//            data.put("assert","MacBook pro");
//            WechatMsgAppTextModel wechatMsgAppTextModel = new WechatMsgAppTextModel();
//            wechatMsgAppTextModel.setData(data);
//            wechatMsgAppTextModel.setToUser(Collections.singletonList("xuzy5"));
//            wechatMsgAppTextModel.setModelCode("WW-1636095451");
//            wechatAppMsgSendService.textMsgSend(wechatMsgAppTextModel);
//            return ModelResponseUtil.ok("success");
//        }catch (Exception e){
//            log.error("adviceManager exception", e);
//            return ModelResponseUtil.ok(null);
//        }
//    }

    /**
     * 测试text类型消息发送
     * @return
     */
//    @RequestMapping(value = "/textSend1", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelResponse<String> textSend1(){
//        try{
//            HashMap<String,String> data = new HashMap<>();
//            data.put("content","测试消息");
//            WechatMsgAppTextModel wechatMsgAppTextCardModel = new WechatMsgAppTextModel();
//            wechatMsgAppTextCardModel.setData(data);
//            wechatMsgAppTextCardModel.setToUser(Collections.singletonList("xuzy5"));
//            wechatMsgAppTextCardModel.setModelCode("WW-1612350904");
//            wechatAppMsgSendService.textMsgSend(wechatMsgAppTextCardModel);
//            return ModelResponseUtil.ok("success");
//        }catch (Exception e){
//            log.error("adviceManager exception", e);
//            return ModelResponseUtil.ok(null);
//        }
//    }

    /**
     * 测试textCard类型消息发送
     * @return
     */
//    @RequestMapping(value = "/textCard", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelResponse<String> textCardSend(){
//        try{
//            WechatMsgAppTextCardModel wechatMsgAppTextCardModel = new WechatMsgAppTextCardModel();
//            wechatMsgAppTextCardModel.setTitle("领奖通知");
//            wechatMsgAppTextCardModel.setDescription("<div class=\\\"gray\\\">2021年11月5日</div> <div class=\\\"normal\\\">恭喜你抽中iPhone 13pro一台</div>");
//            wechatMsgAppTextCardModel.setUrl("http://www.ziroom.com");
//            wechatMsgAppTextCardModel.setBtntxt("更多");
//            wechatMsgAppTextCardModel.setToUser(Collections.singletonList("xuzy5"));
//            wechatMsgAppTextCardModel.setModelCode("WW-1612350904");
//            wechatAppMsgSendService.textCardMsgSend(wechatMsgAppTextCardModel);
//            return ModelResponseUtil.ok("success");
//        }catch (Exception e){
//            log.error("adviceManager exception", e);
//            return ModelResponseUtil.ok(null);
//        }
//    }

    /**
     * 测试taskCard类型消息发送
     * @return
     */
//    @RequestMapping(value = "/taskCard", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelResponse<String> taskCard(){
//        try{
//            WechatMsgAppTaskCardModel wechatMsgAppTextCardModel = new WechatMsgAppTaskCardModel();
//            wechatMsgAppTextCardModel.setTitle("赵明登的礼物申请");
//            wechatMsgAppTextCardModel.setDescription("");
//            wechatMsgAppTextCardModel.setUrl("");
//            wechatMsgAppTextCardModel.setTaskId("");
//            wechatMsgAppTextCardModel.setButtonList(null);
//            wechatMsgAppTextCardModel.setToUser(Collections.singletonList("xuzy5"));
//            wechatMsgAppTextCardModel.setModelCode("");
//            wechatAppMsgSendService.taskCardMsgSend(wechatMsgAppTextCardModel);
//            return ModelResponseUtil.ok("success");
//        }catch (Exception e){
//            log.error("adviceManager exception", e);
//            return ModelResponseUtil.ok(null);
//        }
//    }


}
