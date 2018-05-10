package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.GoodsImgDao;
import edu.cslg.easyshopping.pojo.GoodsImg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsImgService {

    @Resource
    private GoodsImgDao goodsImgDao;

    /**
     * 商品的图片
     *
     * @param goodsImg 保存的商品图片
     */
    public void saveGoodsImg(GoodsImg goodsImg) {
        goodsImgDao.saveGoodsImg(goodsImg);
    }

    /**
     * 通过商品id获取相应的商品图片
     *
     * @param id 商品的id
     * @return 商品的图片
     */
    public GoodsImg getGoodsImgById(Integer id) {
        return goodsImgDao.getGoodsImgById(id);
    }

    /**
     * 更新商品的图片
     *
     * @param goodsImg 商品图片的参数
     */
    public void updateGoodsImg(GoodsImg goodsImg) {
        goodsImgDao.updateGoodsImg(goodsImg);
    }
}
