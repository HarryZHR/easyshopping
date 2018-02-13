package edu.cslg.easyshopping.util;

import java.util.Random;

/**
 * 产生六位随机验证码
 * @author HarryZhang
 */
public class ValidationCode {
    public static String getRandom(){
        Random random = new Random();
        StringBuilder s = new StringBuilder();
        while(s.length() < 6){
            int num = random.nextInt(10);
            s.append(num);
        }
        return s.toString();
    }
}
