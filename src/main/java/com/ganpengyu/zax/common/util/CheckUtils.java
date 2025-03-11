package com.ganpengyu.zax.common.util;

import com.ganpengyu.zax.common.ZaxException;

/**
 * 逻辑检查工具
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
public class CheckUtils {

    public static void check(boolean condition, String message) {
        if (!condition) {
            throw new ZaxException(message);
        }
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

}
