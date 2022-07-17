package com.ziroom.suzaku.main.exception;

import com.ziroom.suzaku.main.constant.ErrorCode;

import java.io.IOException;

/**
 * @author xuzeyu
 */
public class SuzakuBussinesException extends RuntimeException {

    private String code;
    private String errorMessage;

    public SuzakuBussinesException(Throwable e) {
        super(e);
    }

    public SuzakuBussinesException(String errorMessage) {
        super(errorMessage);
    }

    public SuzakuBussinesException(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
