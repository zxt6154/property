package com.ziroom.suzaku.main.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.ManagerType;
import com.ziroom.suzaku.main.constant.enums.RemandState;
import com.ziroom.suzaku.main.entity.*;
import com.ziroom.suzaku.main.dao.SuzakuRemandMapper;
import com.ziroom.suzaku.main.enums.AssertStatusEnum;
import com.ziroom.suzaku.main.message.model.wechat.WechatMsgAppTextModel;
import com.ziroom.suzaku.main.message.service.WechatAppMsgSendService;
import com.ziroom.suzaku.main.model.dto.SuzakuRemandDto;
import com.ziroom.suzaku.main.model.po.SuzakuRemandPo;
import com.ziroom.suzaku.main.model.qo.RemandQo;
import com.ziroom.suzaku.main.model.vo.SuzakuRemandItemVo;
import com.ziroom.suzaku.main.param.ApplyRemandReqParam;
import com.ziroom.suzaku.main.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.utils.DozerUtil;
import com.ziroom.suzaku.main.utils.IpUtils;
import com.ziroom.suzaku.main.utils.UUIDUtil;
import com.ziroom.suzaku.main.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.asn1.cmp.OOBCertHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.ziroom.suzaku.main.constant.SuzakuConstant.REMAND;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-10-26
 */
@Service
@Slf4j
public class SuzakuRemandServiceImpl extends ServiceImpl<SuzakuRemandMapper, SuzakuRemand> implements SuzakuRemandService {

    @Resource
    private SuzakuRemandMapper suzakuRemandMapper;

    @Autowired
    private SuzakuRemandItemService suzakuRemandItemService;

    @Autowired
    private SuzakuImportAssertsService importAssertsService;

    @Autowired
    private SuzakuAssertsLogService logService;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private UserHolder userHolder;

    @Autowired
    private SuzakuDepotService suzakuDepotService;

    @Value("${wechat.model.no2}")
    private String model;

    @Value("${send.notice.switch}")
    private boolean sendSwitch;

    @Autowired
    private SuzakuNoticeLogService noticeLogService;

    @Autowired
    private WechatAppMsgSendService wechatAppMsgSendService;

    @Autowired
    private SuzakuImportAssertsService assertsService;

    @Value("${cmdb.url}")
    private String cmdbEmailUrl;

    @Override
    public PageData<SuzakuRemandDto> pageRemand(RemandQo qo) {
        if (qo.getOffset() == null) {
            qo.setOffset(1);
        }
        if (qo.getPageSize() == null) {
            qo.setPageSize(20);
        }
        Page<SuzakuRemand> page = new Page<>(qo.getOffset(), qo.getPageSize());
        IPage<SuzakuRemandPo> iPage = suzakuRemandMapper.selectRemandPage(page,qo);
        PageData<SuzakuRemandDto> suzakuRemandDtoPageData = DozerUtil.mapPageData(iPage, SuzakuRemandDto.class, beanMapper);
        if (CollectionUtil.isNotEmpty(suzakuRemandDtoPageData.getData())){
            suzakuRemandDtoPageData.getData().forEach(v->{
                if (StrUtil.isNotEmpty(v.getRemandState())){
                    Optional<RemandState> remandState = Optional.ofNullable(RemandState.getByCode(Integer.valueOf(v.getRemandState())));
                    remandState.ifPresent(state -> v.setRemandState(state.getLabel()));
                }
                if (StrUtil.isNotEmpty(v.getRemandType())){
                    Optional<ManagerType> managerType = Optional.ofNullable(ManagerType.getByCode(Integer.valueOf(v.getRemandType())));
                    managerType.ifPresent(manager -> v.setRemandType(manager.getLabel()));
                }

                if (StrUtil.isNotEmpty(v.getRemandDepot())){
                    SuzakuDepot byId = suzakuDepotService.getById(v.getRemandDepot());
                    v.setRemandDepot(byId.getDepotName());
                }

            });
        }
        return suzakuRemandDtoPageData;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRemand(ApplyRemandReqParam applyRemandReqParam) {
        if(StrUtil.isNotEmpty(applyRemandReqParam.getId())){
            updateRemand(applyRemandReqParam);
            return;
        }
        SuzakuRemand remand = new SuzakuRemand();
        remand.setId(UUIDUtil.getId());
        remand.setRemandId(REMAND + UUIDUtil.getUID());
        remand.setRemandState(String.valueOf(RemandState.REMAND_PENDING.getValue()));
        remand.setRemandRemark(applyRemandReqParam.getRemandRemark());
        remand.setRemandDepot(applyRemandReqParam.getRemandDepot());
        remand.setRemandType(applyRemandReqParam.getRemandType());
        remand.setRemandStoreCode(applyRemandReqParam.getRemandStoreCode());
        remand.setRemandStoreName(applyRemandReqParam.getRemandStoreName());
        remand.setRemandAdmin(userHolder.getUserInfo().getName());
        remand.setOperateName(userHolder.getUserInfo().getName());
        remand.setRemandDate(LocalDateTime.now());
        remand.setCreateTime(LocalDateTime.now());
        remand.setUpdateTime(LocalDateTime.now());
        suzakuRemandMapper.insert(remand);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                if (CollectionUtil.isNotEmpty(applyRemandReqParam.getSuzakuRemandItemVos())){
                    createRemandItem(applyRemandReqParam.getSuzakuRemandItemVos());
                }
            }
            private void createRemandItem(List<SuzakuRemandItemVo> suzakuRemandItemVos) {
                suzakuRemandItemVos.forEach(v->{
                    SuzakuRemandItem suzakuRemandItem = new SuzakuRemandItem();
                    suzakuRemandItem.setId(UUIDUtil.getId());
                    suzakuRemandItem.setRemandId(remand.getRemandId());
                    suzakuRemandItem.setSkuId(v.getSkuId());
                    suzakuRemandItem.setSkuName(v.getSkuName());
                    suzakuRemandItem.setAssertCode(v.getAssertCode());
                    suzakuRemandItem.setUsePeople(v.getUsePeople());
                    suzakuRemandItem.setOldAssertCode(v.getOldAssertCode());
                    suzakuRemandItem.setUpdateTime(LocalDateTime.now());
                    suzakuRemandItem.setCreateTime(LocalDateTime.now());
                    suzakuRemandItemService.save(suzakuRemandItem);
                    //更新资产状态 -》 退库中
                    LambdaQueryWrapper<SuzakuImportAsserts> queryWrapper = new LambdaQueryWrapper<>();
                    queryWrapper.eq(SuzakuImportAsserts::getAssertsCode,v.getAssertCode());
                    SuzakuImportAsserts one = importAssertsService.getOne(queryWrapper);
                    one.setAssetsState(AssertStatusEnum.ASSERT_RETURN.getType());
                    one.setUpdateTime(LocalDateTime.now());
                    one.setGroupId(1);
                    importAssertsService.updateById(one);
                    //记录日志
                    String remark ="资产退库登记，设备状态：退库中";
                    insertLog(v.getAssertCode(),v.getSkuName(),remark);
                    sendWeChatNotice(v);
                });
            }

            /**
             * 退还企微通知
             * @param v
             */
            private void sendWeChatNotice(SuzakuRemandItemVo v) {
                LambdaQueryWrapper<SuzakuImportAsserts> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SuzakuImportAsserts::getAssertsCode,v.getAssertCode());
                SuzakuImportAsserts asserts = assertsService.getOne(queryWrapper);
                HashMap<String,String> data = new HashMap<>(8);
                String name = v.getUsePeople().substring(0, v.getUsePeople().indexOf("("));
                data.put("userName",name);
                data.put("email",v.getUsePeople());
                data.put("brandName",v.getBrandName());
                data.put("skuType",asserts.getSkuType());
                data.put("attributeStr",asserts.getAttributeStr());
                data.put("assertsCode",v.getAssertCode());
                data.put("snCode",asserts.getSnCode());
                data.put("type", String.valueOf(AssertStatusEnum.ASSERT_RETURN.getType()));
                WechatMsgAppTextModel wechatMsgAppTextModel = new WechatMsgAppTextModel();
                wechatMsgAppTextModel.setData(data);
                String toUser = v.getUsePeople().substring(0, v.getUsePeople().indexOf("@"));
                int index= toUser.indexOf("(");
                String to = toUser.substring(index+1);
                wechatMsgAppTextModel.setToUser(Collections.singletonList(to));
                wechatMsgAppTextModel.setModelCode(model);
                if (sendSwitch){
                    wechatAppMsgSendService.textMsgSend(wechatMsgAppTextModel);
                    saveNoticeLog(name,v,wechatMsgAppTextModel);
                    sendEmail(wechatMsgAppTextModel);
                }
            }

            /**
             * 记录发送通知日志
             * @param name
             * @param v
             * @param wechatMsgAppTextModel
             */
            private void saveNoticeLog(String name, SuzakuRemandItemVo v, WechatMsgAppTextModel wechatMsgAppTextModel) {
                SuzakuNoticeLog log = new SuzakuNoticeLog();
                log.setAssertcode(v.getAssertCode());
                log.setAssertname(v.getSkuName());
                log.setBrandname(v.getBrandName());
                log.setUsername(name);
                log.setType(String.valueOf(AssertStatusEnum.ASSERT_RETURN.getType()));
                log.setUserInfo(v.getUsePeople());
                log.setCreateUser(userHolder.getUserInfo().getName());
                log.setContent(JSON.toJSONString(wechatMsgAppTextModel));
                log.setCreateTime(LocalDateTime.now());
                log.setUpdateTime(LocalDateTime.now());
                noticeLogService.save(log);
            }

            /**
             * 发送邮件
             * @param wechatMsgAppTextModel
             */
            private void sendEmail(WechatMsgAppTextModel wechatMsgAppTextModel) {
                try {
                    String body = HttpRequest.post(cmdbEmailUrl)
                            .header("Content-Type", "application/json")
                            .body(JSON.toJSONString(wechatMsgAppTextModel))
                            .execute()
                            .body();
                    log.info("发送结果：" + JSON.toJSONString(body));
                } catch (HttpException e) {
                    log.error("发送邮件失败" + e.getMessage());
                }
            }
        });
    }

    /**
     * 审核通过
     * 只有审核通过资产状态才能变为闲置
     * @param applyRemandReqParam
     */
    private void updateRemand(ApplyRemandReqParam applyRemandReqParam) {
        SuzakuRemand remand = suzakuRemandMapper.selectById(applyRemandReqParam.getId());
        switch (applyRemandReqParam.getRemandState()) {
            case "2":
                remand.setRemandState(String.valueOf(RemandState.REMAND_AUDITED.getValue()));
                break;
            case "3":
                remand.setRemandState(String.valueOf(RemandState.REMAND_FAIL.getValue()));
                break;
            default:
                remand.setRemandState(String.valueOf(RemandState.REMAND_PENDING.getValue()));
                break;
        }
        remand.setId(applyRemandReqParam.getId());
        remand.setUpdateTime(LocalDateTime.now());
        suzakuRemandMapper.updateById(remand);
        updateAssertsStatus(applyRemandReqParam.getRemandState(),applyRemandReqParam.getAssertCode());
    }

    /**
     * 更新退库审核状态
     * @param state
     * @param assertCode
     */
    private void updateAssertsStatus(String state,String assertCode) {
        String remark ="";
        LambdaQueryWrapper<SuzakuImportAsserts> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SuzakuImportAsserts::getAssertsCode, assertCode);
        SuzakuImportAsserts serviceOne = importAssertsService.getOne(wrapper);
        switch (state) {
            case "2":
                serviceOne.setAssetsState(AssertStatusEnum.ASSERT_LEAVE.getType());
                serviceOne.setUsePeople("");
                serviceOne.setUseDepartment("");
                serviceOne.setUpdateTime(LocalDateTime.now());
                importAssertsService.updateById(serviceOne);
                remark = "退库设备通过，设备状态由退库中变更为闲置";
                insertLog(assertCode,serviceOne.getSkuName(),remark);
                break;
            case "3":
                serviceOne.setAssetsState(AssertStatusEnum.ASSERT_RETURN.getType());
                serviceOne.setUpdateTime(LocalDateTime.now());
                importAssertsService.updateById(serviceOne);
                remark = "退库设备驳回，设备状态：退库中";
                insertLog(assertCode,serviceOne.getSkuName(),remark);
                break;
            default:
                break;
        }
    }


    /**
     * 记录行为日志
     * @param assertCode
     * @param skuName
     * @param remark
     */
    private void insertLog(String assertCode, String skuName, String remark) {
        SuzakuAssertsLog assertsLog = new SuzakuAssertsLog();
        assertsLog.setOperator(userHolder.getUserInfo().getName());
        assertsLog.setAssertsCode(assertCode);
        assertsLog.setAssertsName(skuName);
        Optional<String> localIp = Optional.ofNullable(IpUtils.getLocalIp());
        localIp.ifPresent(assertsLog::setOperatorIp);
        assertsLog.setRemark(remark);
        logService.saveSuzakuAssertsLog(assertsLog);
    }

}
