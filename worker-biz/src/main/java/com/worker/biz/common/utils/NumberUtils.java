package com.worker.biz.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 工号类
 *
 *  @author
 * @date: 2023-11-4 02:31
 */
public class NumberUtils {

    /**
     * 生成工号
     *
     * @return 工号
     */
    public static String generateWorkerNumber() {
        // 使用时间戳生成部分工号
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestampPart = dateFormat.format(new Date());

        // 生成一部分随机数
        String randomPart = generateRandomNumber();

        // 最终订单号由时间戳和随机数拼接而成
        return timestampPart + randomPart;
    }

    public static String generateRandomNumber() {
        Random random = new Random();
        // 生成 4 位的随机数
        int randomNumber = 1000 + random.nextInt(9000);
        return String.valueOf(randomNumber);
    }
}
