package com.ziroom.suzaku.main.controller;

import com.alibaba.fastjson.JSON;
import com.ziroom.suzaku.main.components.HaloComponent;
import com.ziroom.suzaku.main.config.OperatorContext;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.halo.AuthReq;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daijiankun
 */
@Api(value = "对接halo权限管理")
@RestController
@Slf4j
@RequestMapping(value = "/api/common/")
public class CommonController {

    @Autowired
    private HaloComponent haloComponent;

    @GetMapping("auth/v1")
    public JsonResult getCurrentAuth() {
        AuthReq authReq = new AuthReq();
        authReq.setAppId("suzaku-ui");
        authReq.setUserCode(OperatorContext.getOperator());
        log.info("GatewayApi.auth params:{}", JSON.toJSONString(authReq));
        return JsonResult.ok(haloComponent.auth(authReq));
    }

}
