package com.ziroom.suzaku.main.dao;

import com.ziroom.suzaku.main.entity.SuzakuRegisterFormEntity;
import com.ziroom.suzaku.main.param.ApplyFormSelectReqParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 资产领借登记
 * @author xuzeyu
 */
@Mapper
public interface SuzakuRegisterFormMapper {

    Integer totalApplyForm(ApplyFormSelectReqParam selectParam);

    List<SuzakuRegisterFormEntity> pageSkus(ApplyFormSelectReqParam applyFormSelectReqParam);

    List<SuzakuRegisterFormEntity> selAll(ApplyFormSelectReqParam applyFormSelectReqParam);

    SuzakuRegisterFormEntity selOne(String orderId);

    void insert(SuzakuRegisterFormEntity suzakuRegisterFormEntity);

    //void insertBatch(List<SuzakuRegisterFormEntity> suzakuRegisterFormEntitys);


}
