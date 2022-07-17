package com.ziroom.suzaku.main.enums;

/**
 * 领借单状态
 * @author xuzeyu
 */
public enum ApplyFormStatusEnum {

    REGISTER_NOCONFIRED(1, "待确认"),
    REGISTER_CANCEL(2, "已取消"),
    REGISTER_CONFIRED(3, "已确认"),
    REGISTER_RETURN(4, "已归还"),
    ;
    private final Integer type;
    private final String desc;

    ApplyFormStatusEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static String getType(Integer type) {
        ApplyFormStatusEnum[] applyFormStatusEnums = values();

        for (ApplyFormStatusEnum e : applyFormStatusEnums) {
            if (e.getType()==type) {
                return e.getDesc();
            }
        }
        return null;
    }

}
