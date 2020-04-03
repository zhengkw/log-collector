package com.zhengkw.flume.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @ClassName:ETCUtil
 * @author: zhengkw
 * @description:
 * @date: 20/03/28下午 11:27
 * @version:1.0
 * @since: jdk 1.8
 */
public class ETLUtil {

    //处理启动日志
    public static boolean validStartLog(String str) {

        //先判空
        if (StringUtils.isBlank(str)) {

            return false;

        }

        //去前后空格
        String trimStr = str.trim();

        //验证是否以{}开头和结尾
        if (trimStr.startsWith("{") && trimStr.endsWith("}")) {

            return true;

        }

        return false;

    }

    //处理事件日志
    public static boolean validEventLog(String str) {

        //先判空
        if (StringUtils.isBlank(str)) {

            return false;

        }

        //去前后空格
        String trimStr = str.trim();

        //按照|进行切分
        String[] words = trimStr.split("\\|");

        //判断格式是否残缺
        if (words.length != 2) {

            return false;

        }

        //判断时间戳是否合法 a) 长度复合13位 b)都是是数字
        // NumberUtils.isDigits(): 要求字符串中必须全部为数字符号0-9
        // NumberUtils.isNumbers(): 只要是java支持的数字类型即可
        if (words[0].length() != 13 || !NumberUtils.isDigits(words[0])) {
            return false;
        }

        //验证是否以{}开头和结尾
        if (words[1].startsWith("{") && words[1].endsWith("}")) {

            return true;

        }

        return false;

    }
}
