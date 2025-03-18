package com.ganpengyu.zax.common;

/**
 * 统一业务异常
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
public class ZaxException extends RuntimeException {

    public ZaxException() {
    }

    public ZaxException(String message) {
        super(message);
    }

    public ZaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZaxException(Throwable cause) {
        super(cause);
    }

    public ZaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
