package edu.cslg.easyshopping.util;

import edu.cslg.easyshopping.pojo.Goods;
import edu.cslg.easyshopping.pojo.Standard;

import java.util.List;

public class GoodsCountUtil {
    public static List<Goods> setGoodsListCount(List<Goods> goodsList){
        for (Goods goods: goodsList) {
            setGoodsCount(goods);
        }
        return goodsList;
    }

    public static Goods setGoodsCount(Goods goods){
        List<Standard> standards = goods.getStandards();
        Integer allCount = 0;
        for (Standard standard: standards) {
            allCount += standard.getCount();
        }
        goods.setAllCount(allCount);
        return goods;
    }
}
