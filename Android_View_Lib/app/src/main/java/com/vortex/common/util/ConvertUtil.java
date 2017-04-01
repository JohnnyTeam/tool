package com.vortex.common.util;

import java.math.BigDecimal;

/**
 * 转换工具类
 *
 * @author Johnny.xu
 *         date 2017/3/29
 */
public class ConvertUtil {

    private ConvertUtil() {}

    /**
     * 字符串转换（将空字符串转换成默认提示）
     * @param str 字符串
     * @param prompt 提示文字
     * @return 转换后的文字
     */
    public static String convertDefaultString(String str, String prompt) {
        if (StringUtils.isEmpty(str)) {
            return prompt;
        } else {
            return str;
        }
    }

    /**
     * 字符串转换（将空字符串转换成默认提示），如果为空则转换成'暂无'
     * @param str 字符串
     * @return 转换后的文字
     */
    public static String convertDefaultString(String str) {
        return convertDefaultString(str, "暂无");
    }

    /**
     * 转换数字数据
     * @param num 数
     * @param index 保留几位小数，如保留整数则为0
     * @return BigDecimal对象，直接调用其doubleValue()、floatValue()、intValue()、toPlainString()
     */
    public static BigDecimal reserveDecimalsFormat(double num, int index) {
        BigDecimal bg = new BigDecimal(num);
        return bg.setScale(index, BigDecimal.ROUND_HALF_UP);
    }
}
