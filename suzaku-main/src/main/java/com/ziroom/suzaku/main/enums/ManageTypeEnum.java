package com.ziroom.suzaku.main.enums;

import io.swagger.models.auth.In;

/**
 * 管理类型枚举
 * @author xuzeyu
 */
public enum ManageTypeEnum {

    MANAGE__TYPE_IT(1, "IT资产"),
    MANAGE_TYPE_SHOP(2, "门店"),
    MANAGE_TYPE_STATION(3, "工作站"),
    MANAGE_TYPE_BUSINESSER(4, "职能行政"),
    ;
    private final Integer type;
    private final String desc;

    ManageTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(Integer type){
        ManageTypeEnum[] manageTypeEnums = values();

        for (ManageTypeEnum e : manageTypeEnums) {
            if (e.getType()==type) {
                return e.getDesc();
            }
        }
        return type.toString();
    }


}
