package com.benefit.utils;

import java.util.Random;

/**
 * @author Allen
 * @date 2025/8/14 14:27
 */
public class IDUtils {

    /**
    * @Description: 图片ID生成
    * @Param:
    * @Return:
    * @Author: Allen
    */
    public static String genImageName() {
        // 取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上三位随机数
        Random random = new Random();
        // 返回0~999的数据.
        int end3 = random.nextInt(999);
        // 如果不足三位前面补0. %d - 模拟C语言中的数学占位符.  03代表必须三位数字,不足三位使用0补齐.
        String str = millis + String.format("%03d", end3);
        return str;
    }

   /**
   * @Description: 权益id生成
   * @Param:
   * @Return:
   * @Author: Allen
   */
    public static long genItemId() {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);
        //如果不足两位前面补0
        String str = millis + String.format("%02d", end2);
        long id = new Long(str);
        return id;
    }
}
