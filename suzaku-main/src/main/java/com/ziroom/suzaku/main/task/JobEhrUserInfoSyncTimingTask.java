package com.ziroom.suzaku.main.task;

import com.github.benmanes.caffeine.cache.Cache;
import com.ziroom.suzaku.main.client.ehr.model.EhrUserInfoRespModel;
import com.ziroom.suzaku.main.client.ehr.param.EhrUserInfoReqParam;
import com.ziroom.suzaku.main.client.ehr.service.EhrService;
import com.ziroom.suzaku.main.client.uc.service.UcService;
import com.ziroom.suzaku.main.enums.DelayJobEnum;
import com.ziroom.suzaku.main.event.EhrUserInfoSyncFailEvent;
import com.ziroom.suzaku.main.model.SuzakuUserInfoModel;
import com.ziroom.tech.queue.DelayQueueUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * ehr用户信息同步
 * @author xuzeyu
 */
@Slf4j
public class JobEhrUserInfoSyncTimingTask implements Runnable {

    private EhrService ehrService;

    private UcService ucService;

    private Cache USERCACHE;

    public JobEhrUserInfoSyncTimingTask(EhrService ehrService, UcService ucService, Cache ehrUserInfoCache) {
        this.ehrService = ehrService;
        this.ucService = ucService;
        this.USERCACHE = ehrUserInfoCache;
    }

    @Override
    public void run() {
        try{

            //首次查询
            Integer totalCount = ehrService.getTotalCount();
            int page = totalCount % 100 == 0 ? totalCount / 100 : totalCount / 100 + 1;

            EhrUserInfoReqParam ehrUserInfoReqParam = new EhrUserInfoReqParam();

            //查询ehr用户
            List<SuzakuUserInfoModel> suzakuUserInfoModelList = IntStream.rangeClosed(1, page).mapToObj(curPage -> {
                ehrUserInfoReqParam.setPage(curPage);
                ehrUserInfoReqParam.setSize(100);
                List<EhrUserInfoRespModel> ehrUserinfoList = ehrService.getUserSimple(ehrUserInfoReqParam);

                return ehrUserinfoList.stream().map(m->{
                    SuzakuUserInfoModel suzakuUserInfoModel = new SuzakuUserInfoModel();
                    suzakuUserInfoModel.setUserName(m.getFullName() + "(" + m.getEmail() + ")");
                    suzakuUserInfoModel.setEmail(m.getEmail());
                    return suzakuUserInfoModel;
                }).collect(Collectors.toList());
            }).flatMap(Collection::stream).collect(toList());

            //查询uc用户
            //List<UcUserInfoRespModel> ucUserInfoList = ucService.getUserInfoList();

            //缓存用户信息
            if(CollectionUtils.isNotEmpty(suzakuUserInfoModelList)){
                //清空用户缓存
                USERCACHE.invalidateAll();
                suzakuUserInfoModelList.forEach(ehrInfo -> USERCACHE.put(ehrInfo.getUserName() + ehrInfo.getEmail(), ehrInfo));
            }

        } catch (Exception e){
            log.error("[JobEhrUserInfoSyncTimingTask] run exception", e);
            log.info("[JobEhrUserInfoSyncTimingTask] EhrUserInfoSyncFailEvent insurance");
            DelayQueueUtil.getInstance().put(new EhrUserInfoSyncFailEvent(DelayJobEnum.SYNC_EHR_FAIL_EVENT.getType(), null));
        } finally {
            log.info("[JobEhrUserInfoSyncTimingTask] run end");
        }

    }

}
