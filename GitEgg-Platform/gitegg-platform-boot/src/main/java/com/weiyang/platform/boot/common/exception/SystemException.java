package com.weiyang.platform.boot.common.exception;

import com.weiyang.platform.boot.common.enums.ResultCodeEnum;
import lombok.Getter;

/**
 * @ClassName: SystemException
 * @Description: 系統處理異常
 * @author GitEgg
 * @date
 */
@Getter
public class SystemException extends RuntimeException {

    private int code;

    private String msg;

    public SystemException() {
        this.code = ResultCodeEnum.ILLEGAL_REQUEST.code;
        this.msg = ResultCodeEnum.ERROR.msg;
    }

    public SystemException(String message) {
        this.code = ResultCodeEnum.ILLEGAL_REQUEST.code;
        this.msg = message;
    }

    public SystemException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

}