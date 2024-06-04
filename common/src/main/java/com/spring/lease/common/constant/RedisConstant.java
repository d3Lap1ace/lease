package com.spring.lease.common.constant;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 4/6/2024 16:56 周二
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */


public class RedisConstant {
    public static final String ADMIN_LOGIN_PREFIX = "admin:login:";
    public static final Integer ADMIN_LOGIN_CAPTCHA_TTL_SEC = 60;
    public static final String APP_LOGIN_PREFIX = "app:login:";
    public static final Integer APP_LOGIN_CODE_RESEND_TIME_SEC = 60;
    public static final Integer APP_LOGIN_CODE_TTL_SEC = 60 * 10;
}
