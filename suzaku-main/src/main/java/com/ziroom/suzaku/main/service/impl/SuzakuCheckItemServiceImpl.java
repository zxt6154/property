package com.ziroom.suzaku.main.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ziroom.suzaku.main.common.BeanMapper;
import com.ziroom.suzaku.main.common.PageData;
import com.ziroom.suzaku.main.constant.enums.*;
import com.ziroom.suzaku.main.entity.SuzakuCheckItem;
import com.ziroom.suzaku.main.dao.SuzakuCheckItemMapper;
import com.ziroom.suzaku.main.entity.SuzakuImportAsserts;
import com.ziroom.suzaku.main.model.dto.CheckSkuDto;
import com.ziroom.suzaku.main.model.dto.SuzakuCheckItemDto;
import com.ziroom.suzaku.main.model.po.SuzakuCheckItemPo;
import com.ziroom.suzaku.main.model.qo.CheckItemQo;
import com.ziroom.suzaku.main.model.vo.CheckSkuVo;
import com.ziroom.suzaku.main.service.SuzakuCheckItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ziroom.suzaku.main.service.SuzakuImportAssertsService;
import com.ziroom.suzaku.main.utils.DateUtils;
import com.ziroom.suzaku.main.utils.DozerUtil;
import com.ziroom.suzaku.main.utils.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libingsi
 * @since 2021-11-15
 */
@Service
public class SuzakuCheckItemServiceImpl extends ServiceImpl<SuzakuCheckItemMapper, SuzakuCheckItem> implements SuzakuCheckItemService {


    @Resource
    private SuzakuCheckItemMapper checkItemMapper;

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private SuzakuImportAssertsService assertsService;

    @Autowired
    private UserHolder userHolder;


    @Override
    public PageData<SuzakuCheckItemDto> pageCheckItem(CheckItemQo qo) {
        if (qo.getOffset() == null) {
            qo.setOffset(1);
        }
        if (qo.getPageSize() == null) {
            qo.setPageSize(20);
        }
        Page<SuzakuCheckItem> page = new Page<>(qo.getOffset(), qo.getPageSize());
        IPage<SuzakuCheckItemPo> iPage = checkItemMapper.selectCheckItemPage(page,qo);
        PageData<SuzakuCheckItemDto> checkItemDtoPageData = DozerUtil.mapPageData(iPage, SuzakuCheckItemDto.class, beanMapper);
        if (CollectionUtil.isNotEmpty(checkItemDtoPageData.getData())){
            checkItemDtoPageData.getData().forEach(v->{
                if (StrUtil.isNotEmpty(v.getAdminStatus())){
                    Optional<AdminCheckState> adminCheckState = Optional.ofNullable(AdminCheckState.getByCode(Integer.valueOf(v.getAdminStatus())));
                    adminCheckState.ifPresent(state -> v.setAdminStatus(state.getLabel()));
                }
                if (StrUtil.isNotEmpty(v.getStaffStatus())){
                    Optional<StaffCheckState> staffCheckState = Optional.ofNullable(StaffCheckState.getByCode(Integer.valueOf(v.getStaffStatus())));
                    staffCheckState.ifPresent(type -> v.setStaffStatus(type.getLabel()));
                }
            });
        }
        return checkItemDtoPageData;
    }

    @Override
    public CheckSkuDto getCheckItemInfo(String id) {
        if (StrUtil.isEmpty(id)){
            return null;
        }
        SuzakuCheckItem checkItem = new LambdaQueryChainWrapper<>(baseMapper).eq(SuzakuCheckItem::getId,id).one();
        CheckSkuDto dto = beanMapper.map(checkItem,CheckSkuDto.class);
        LambdaQueryWrapper<SuzakuImportAsserts> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuImportAsserts::getAssertsCode,checkItem.getAssertCode());
        SuzakuImportAsserts asserts = assertsService.getOne(queryWrapper);
        dto.setSnCode(asserts.getSnCode());
        dto.setMacImic(asserts.getMacImic());
        dto.setPurchasePrice(asserts.getPurchasePrice());
        if (null != asserts.getPurchaseDate()){
            dto.setPurchaseDate(DateUtils.localDateTimeToString(asserts.getPurchaseDate()));
        }
        if (null != asserts.getMaintainOverdue()){
            dto.setMaintainOverdue(DateUtils.localDateTimeToString(asserts.getMaintainOverdue()));
        }
        if (StrUtil.isNotEmpty(dto.getStaffStatus())){
            Optional<StaffCheckState> staffCheckState = Optional.ofNullable(StaffCheckState.getByCode(Integer.valueOf(dto.getStaffStatus())));
            staffCheckState.ifPresent(type -> dto.setStaffStatus(type.getLabel()));
        }
        if (StrUtil.isNotEmpty(dto.getAdminStatus())){
            Optional<AdminCheckState> adminCheckState = Optional.ofNullable(AdminCheckState.getByCode(Integer.valueOf(dto.getAdminStatus())));
            adminCheckState.ifPresent(state -> dto.setAdminStatus(state.getLabel()));
        }
        return dto;
    }


    @Override
    public void updateCheckItemStatus(CheckSkuVo vo) {
        if (StrUtil.isEmpty(vo.getId()) || StrUtil.isEmpty(vo.getAdminStatus())){
            return;
        }
        SuzakuCheckItem item = checkItemMapper.selectById(vo.getId());
        if (ObjectUtil.isEmpty(item) ){
            return;
        }
        switch (vo.getAdminStatus()){
            //盘点正常
            case "2":
                item.setAdminStatus(AdminCheckState.SUCCESS.getValue());
                item.setUpdateUser(userHolder.getUserInfo().getName());
                item.setUpdateTime(LocalDateTime.now());
                break;
            //盘点异常
            case "3":
                item.setAdminStatus(AdminCheckState.FAIL.getValue());
                item.setUpdateUser(userHolder.getUserInfo().getName());
                item.setUpdateTime(LocalDateTime.now());
                break;
            //盘盈无效
            case "4":
                item.setAdminStatus(AdminCheckState.INVALID.getValue());
                item.setUpdateUser(userHolder.getUserInfo().getName());
                item.setUpdateTime(LocalDateTime.now());
                break;
            //盘盈有效
            case "5":
                item.setAdminStatus(AdminCheckState.VALID.getValue());
                item.setUpdateUser(userHolder.getUserInfo().getName());
                item.setUpdateTime(LocalDateTime.now());
                break;
        }
        checkItemMapper.updateById(item);
    }


    @Override
    public void updateCheckItem(CheckSkuVo vo) {
        updateCheckItemStatus(vo);
        LambdaQueryWrapper<SuzakuImportAsserts> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SuzakuImportAsserts::getAssertsCode,vo.getAssertCode());
        SuzakuImportAsserts asserts = assertsService.getOne(queryWrapper);
        asserts.setSkuName(vo.getSkuName());
        asserts.setSnCode(vo.getSnCode());
        asserts.setMacImic(vo.getMacImic());
        Optional<String> purchaseDate = Optional.ofNullable(vo.getPurchaseDate());
        purchaseDate.ifPresent(pDate -> asserts.setPurchaseDate(DateUtils.stringToLocalDateTime(pDate)));
        Optional<String> maintainOverdue = Optional.ofNullable(vo.getMaintainOverdue());
        maintainOverdue.ifPresent(mDate -> asserts.setMaintainOverdue(DateUtils.stringToLocalDateTime(mDate)));
        asserts.setPurchasePrice(vo.getPurchasePrice());
        asserts.setGroupId(1);
        assertsService.updateById(asserts);
    }
}
