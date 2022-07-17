package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.entity.SuzakuManageType;
import com.ziroom.suzaku.main.service.SuzakuManageTypeService;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 管理类型
 * @author xuzeyu
 */
@RestController
@RequestMapping("/manage/type")
@Api(value = "管理类型接口", tags = "管理类型接口")
public class SuzakuManageTypeController {

    @Autowired
    private SuzakuManageTypeService suzakuManageTypeService;


    /**
     * 查询管理类型列表
     */
    @RequestMapping(value = "/getManagerType", method = RequestMethod.GET)
    public ModelResponse<List<SuzakuManageType>> getManagerType(){
        List<SuzakuManageType> manageTypes = suzakuManageTypeService.getManagerType();
        return ModelResponseUtil.ok(manageTypes);
    }

}
