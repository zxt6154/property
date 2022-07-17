package com.ziroom.suzaku.main.utils;


import com.ziroom.suzaku.main.constant.ErrorCode;
import com.ziroom.suzaku.main.exception.SuzakuException;
import com.ziroom.zcloud.sso.ZCloudUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author libingsi
 * @Date 2021/10/22 11:30
 **/
@Component
@Slf4j
public class UserHolder {

    public ZCloudUserInfo getUserInfo(){
        try {
            Optional<ZCloudUserInfo> currentUser = ZCloudUserInfo.current();
            if (!currentUser.isPresent()) {
                throw new SuzakuException(ErrorCode.ERROR_001);
            }
            return currentUser.get();
        } catch (SuzakuException e) {
            log.error("用户信息异常：" + e.getMessage());
            throw new SuzakuException(ErrorCode.ERROR_001);
        }
    }

}
