package com.ganpengyu.zax.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口统一返回数据模型
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
@Data
public class ZaxResult<T> implements Serializable {

    private boolean success;

    private String message;

    private T data;

    public ZaxResult(T data) {
        this.success = true;
        this.data = data;
    }

    public ZaxResult(String message) {
        this.success = false;
        this.message = message;
    }

    public static <T> ZaxResult<T> ok(T data) {
        return new ZaxResult<>(data);
    }

    public static <T> ZaxResult<T> error(String message) {
        return new ZaxResult<>(message);
    }

}
