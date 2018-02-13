package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.GoodsDao;
import edu.cslg.easyshopping.pojo.Goods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsService {
    @Resource
    private GoodsDao goodsDao;

    public void saveGoods(Goods goods){
        goodsDao.saveGoods(goods);
    }

    public void updateGoods(Goods goods){
        goodsDao.updateGoods(goods);
    }

    public List<Goods> listGoodsBySeller(Integer id,Integer goodsIndex,Integer pageSize){
        return goodsDao.listGoodsBySeller(id, goodsIndex, pageSize);
    }

    public Integer countGoodsBySeller(Integer id){
        return goodsDao.countGoodsBySeller(id);
    }

    public Goods getGoodsById(Integer id){
        return goodsDao.getGoodsById(id);
    }

    public List<Goods> listGoodsAll(Integer goodsIndex, Integer pageSize){
        return goodsDao.listGoodsAll(goodsIndex,pageSize);
    }

    public Integer countGoodsAll(){
        return goodsDao.countGoodsAll();
    }

    public List<Goods> listGoodsByCategory(Integer sellerId, Integer typeId){
        return goodsDao.listGoodsByCategory(sellerId,typeId);
    }

    public List<Goods> listGoodsBySellerAll(Integer sellerId){
        return goodsDao.listGoodsBySellerAll(sellerId);
    }

    public Integer countBuyerLikeGoods(Integer goodsId){
        return goodsDao.countBuyerLikeGoods(goodsId);
    }
}
