package com.ziroom.suzaku.base.base;

/**
 * @author libingsi
 * @date 2021/6/22 15:56
 */
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    protected String msg;
    protected int code = 500;


    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public BaseException(int code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public BaseException(int code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
