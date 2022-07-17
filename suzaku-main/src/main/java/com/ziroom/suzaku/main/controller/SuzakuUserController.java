package com.ziroom.suzaku.main.controller;

import com.ziroom.suzaku.main.utils.UserHolder;
import com.ziroom.tech.model.ModelResponse;
import com.ziroom.tech.util.ModelResponseUtil;
import com.ziroom.zcloud.sso.ZCloudUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 * @author xuzeyu
 */
@RestController
@RequestMapping("/user")
public class SuzakuUserController {

    @Autowired
    private UserHolder userHolder;

    /**
     * 获取用户信息
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ModelResponse<ZCloudUserInfo> getUserInfo(){
        ZCloudUserInfo userInfo = userHolder.getUserInfo();
        return ModelResponseUtil.ok(userInfo);
    }
}
