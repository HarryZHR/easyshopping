package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.GoodsImg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsImgDao {
    /**
     * 商品的图片
     * @param goodsImg 保存的商品图片
     */
    void saveGoodsImg(GoodsImg goodsImg);

    /**
     * 通过商品id获取相应的商品图片
     * @param id 商品的id
     * @return 商品的图片
     */
    GoodsImg getGoodsImgById(Integer id);
}
