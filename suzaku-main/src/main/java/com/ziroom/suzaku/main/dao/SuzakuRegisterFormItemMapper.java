package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuRegisterFormItemEntity;
import com.ziroom.suzaku.main.param.ApplyFormItemSelectReqParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产领借登记明细
 * @author xuzeyu
 */
public interface SuzakuRegisterFormItemMapper {

    void insert(@Param(value="suzakuRegisterFormItemEntityList")List<SuzakuRegisterFormItemEntity> suzakuRegisterFormItemEntityList);

    Integer total(ApplyFormItemSelectReqParam applyFormItemSelectReqParam);

    List<SuzakuRegisterFormItemEntity> getItemsBySelectParam(ApplyFormItemSelectReqParam applyFormItemSelectReqParam);

}
