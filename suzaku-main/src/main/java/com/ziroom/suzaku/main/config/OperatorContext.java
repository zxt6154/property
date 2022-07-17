package com.ziroom.suzaku.main.config;

import com.ziroom.zcloud.sso.ZCloudUserInfo;

import java.util.Optional;

public class OperatorContext {

    private static final ThreadLocal<String> operator = new ThreadLocal<>();

    public static void setOperator() {
        Optional<ZCloudUserInfo> currentUser = ZCloudUserInfo.current();
        currentUser.ifPresent(zCloudUserInfo -> operator.set(zCloudUserInfo.getUid()));
    }

    public static String getOperator() {
        return operator.get();
    }
}
