package com.ziroom.suzaku.main.enums;

/**
 * 业务异常枚举
 * @author xuzeyu
 */
public enum SuzakuServiceExceptionEnum {

    ERROR_CODE("-1", "操作失败"),

    /**
     * 参数为空
     */
    REQUEST_CHECKPARAM_CODE("50001", "请求参数检查错误"),
    ;
    private final String code;
    private final String desc;

    SuzakuServiceExceptionEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


}
