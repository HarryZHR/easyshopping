package edu.cslg.easyshopping.util;

import edu.cslg.easyshopping.pojo.Complain;

import java.util.ArrayList;
import java.util.List;

public class ComplainUtil {
    public static Complain setComplainImgs(Complain complain){
        String img = complain.getImg();
        String[] imgArr = img.split("_");
        List<String> imgs = new ArrayList<>();
        for(String imgStr : imgArr){
            imgs.add(imgStr);
        }
        complain.setImgs(imgs);
        return complain;
    }
}
