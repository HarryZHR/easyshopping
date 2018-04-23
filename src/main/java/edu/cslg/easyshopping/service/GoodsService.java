package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.GoodsDao;
import edu.cslg.easyshopping.pojo.Goods;
import edu.cslg.easyshopping.pojo.GoodsType;
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

    public List<Goods> listGoodsByCategoryInSeller(Integer sellerId, Integer typeId){
        return goodsDao.listGoodsByCategoryInSeller(sellerId,typeId);
    }

    public List<Goods> listGoodsBySellerAll(Integer sellerId){
        return goodsDao.listGoodsBySellerAll(sellerId);
    }

    public Integer countBuyerLikeGoods(Integer goodsId){
        return goodsDao.countBuyerLikeGoods(goodsId);
    }

    /**
     * 通过类型或者关键字获取商品
     * @param type 类型
     * @param key 关键字
     * @param low 低价
     * @param high 高价
     * @param goodsIndex 第一个商品的索引
     * @param pageSize 每页的商品数量
     * @return 商品的集合
     */
    public List<Goods> listGoodsByCategoryAndKey(GoodsType type, String key, Float low, Float high,String operate, Integer goodsIndex, Integer pageSize,Integer carouselId,Integer bigType,Integer sellerId){
        return goodsDao.listGoodsByCategoryAndKey(type, key, low, high,operate, goodsIndex, pageSize,carouselId,bigType,sellerId);
    }

    /**
     * 通过类型或者关键字获取商品的数量
     * @param type 类型
     * @param key 关键字
     * @param low 低价
     * @param high 高价
     * @return 数量
     */
    public Integer countGoodsByCategoryAndKey(GoodsType type, String key, Float low, Float high,Integer carouselId,Integer bigType,Integer sellerId ){
        return goodsDao.countGoodsByCategoryAndKey(type, key, low, high,carouselId,bigType,sellerId);
    }

    /**
     * 获取买家收藏的商品
     * @param buyerId 买家的id
     * @return 收藏的商品
     */
    public List<Goods> listGoodsBuyerLike(Integer buyerId){
        return goodsDao.listGoodsBuyerLike(buyerId);
    }

}
