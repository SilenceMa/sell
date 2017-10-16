package com.mpf.sell.constant;

/**
 * 设置redis的常量
 */
public interface RedisConstant {
    //1.设置过期时间为两小时
    Integer EXPIRE = 7200;

    //2.设置前缀
    String TOKEN_PREFIX = "token_%s";

}
