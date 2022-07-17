package com.ziroom.suzaku.main.constant;

/**
 * @author libingsi
 * @date 2021/6/22 9:27
 */
public enum  ErrorCode {
    /**
     * 网络请求超时或者请求失败
     */
    NET_REQUEST_SUSPEND(500002,"网络请求超时或者请求失败"),
    ERROR_001(001, "该用户不存在"),
    ERROR_002(002, "入库模板下载异常"),
    ERROR_003(003, "该分类下存在资产，禁止删除。"),

    ERROR_004(004, "您的excel导入时间格式有误，正确格式 YYYY-MM-DD HH:mm:ss");

    /**
     * 错误代码
     */
    private int errCode;
    /**
     * 错误信息
     */
    private String errMessage;

    ErrorCode(int errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

}
