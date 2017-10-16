package com.mpf.sell.utils;

import java.util.Date;
import java.util.Random;

public class KeyUtils {
    /*生成主键
    * 时间+随机数
    * synchronized 多线程
    * */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        Integer id = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(id);
    }
}
