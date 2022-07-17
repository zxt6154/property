package com.ziroom.suzaku.main.exception;

import com.ziroom.suzaku.base.base.BaseException;
import com.ziroom.suzaku.main.constant.ErrorCode;

/**
 * @author libingsi
 * @date 2021/6/22 13:57
 */
public class SuzakuException extends BaseException {

    public SuzakuException(String msg) {
        super(msg);
    }

    public SuzakuException(String msg, Throwable e) {
        super(msg, e);
    }

    public SuzakuException(int code, String msg) {
        super(code,msg);
    }

    public SuzakuException(int code, String msg, Throwable e) {
        super(code,msg,e);
    }

    public SuzakuException(ErrorCode errCode) {
        super(errCode.getErrCode(),errCode.getErrMessage());
    }
}
