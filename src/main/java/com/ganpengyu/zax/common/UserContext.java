package com.ganpengyu.zax.common;

/**
 * 用户信息上下文
 *
 * @author Pengyu Gan
 * CreateDate 2025/3/11
 */
public class UserContext {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setContext(String context) {
        contextHolder.set(context);
    }

    public static String getUsername() {
        return contextHolder.get();
    }

    public static void removeContext() {
        contextHolder.remove();
    }

}
