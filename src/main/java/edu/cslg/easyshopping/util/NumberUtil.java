package edu.cslg.easyshopping.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str 判断的字符串
     * @return 返回判断结果
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }
}
