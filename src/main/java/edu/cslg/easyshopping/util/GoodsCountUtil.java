package edu.cslg.easyshopping.util;

import edu.cslg.easyshopping.pojo.Goods;
import edu.cslg.easyshopping.pojo.Standard;
import edu.cslg.easyshopping.service.GoodsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsCountUtil {
    public static List<Goods> setGoodsListCount(List<Goods> goodsList){
        for (Goods goods: goodsList) {
            setGoodsCount(goods,"onlyCount");
        }
        return goodsList;
    }

    /**
     * 设置商品数量
     * @param goods 商品
     * @param type 仅获取总库存还是获取商品所有信息
     * @return 返回商品
     */
    public static Goods setGoodsCount(Goods goods, String type){
        List<String> sizes = new ArrayList<>();
        Map<String,String> colorMap = new HashMap<>();
        List<Standard> standards = goods.getStandards();
        Integer allCount = 0;
        if (standards != null && standards.size() > 0) {

            for (Standard standard: standards) {
                allCount += standard.getCount();
            }
        }
        goods.setAllCount(allCount);
        if(!"onlyCount".equals(type)) {
            for (Standard standard : standards){
                // 获取商品的颜色图片
                if(!colorMap.keySet().contains(standard.getColorImg())){
                    colorMap.put(standard.getColorImg(),standard.getColor());
                }
                // 获取商品的尺寸
                if(!sizes.contains(standard.getSize())){
                    sizes.add(standard.getSize());
                }
            }
            List<String> goodsImgDetails = new ArrayList<>();
            // 商品的细节图
            String[] goodsImgDetailArr = goods.getGoodsImg().getDetailImg().split("_");
            for (String goodsImgDetail : goodsImgDetailArr) {
                if(!goodsImgDetails.contains(goodsImgDetail)){
                    goodsImgDetails.add(goodsImgDetail);
                }
            }
            goods.setGoodsImgDetail(goodsImgDetails);
            goods.setColorMap(colorMap);
            goods.setSizes(sizes);
        }
        return goods;
    }
}
