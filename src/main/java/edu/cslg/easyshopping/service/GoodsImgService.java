package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.GoodsImgDao;
import edu.cslg.easyshopping.pojo.GoodsImg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GoodsImgService {

    @Resource
    private GoodsImgDao goodsImgDao;

    public void saveGoodsImg(GoodsImg goodsImg){
        goodsImgDao.saveGoodsImg(goodsImg);
    }

    public GoodsImg getGoodsImg(Integer id){
        return goodsImgDao.getGoodsImgById(id);
    }
}
