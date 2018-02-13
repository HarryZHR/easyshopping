package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

@Mapper
public interface GoodsDao {
    /**
     * 保存商品
     * @param goods 商品的参数
     */
    void saveGoods(Goods goods);

    /**
     * 更新商品
     * @param goods 商品的参数
     */
    void updateGoods(Goods goods);

    /**
     * 获取店铺所有上架的商品
     * @param id 店铺的id
     * @return 店铺内上架的商品
     */
    List<Goods> listGoodsBySeller(@Param("id") Integer id,@Param("goodsIndex") Integer goodsIndex,@Param("pageSize") Integer pageSize);

    /**
     * 获取店铺所有上架商品的数量
     * @param id 店铺的id
     * @return 店铺所有上架商品的数量
     */
    Integer countGoodsBySeller(Integer id);

    /**
     * 获取所有上架的商品
     * @param goodsIndex 商品的索引
     * @param pageSize 一页的商品数量
     * @return 所有上架的商品
     */
    List<Goods> listGoodsAll(@Param("goodsIndex") Integer goodsIndex,@Param("pageSize") Integer pageSize);

    /**
     * 获取所有上架商品的数量
     * @return 上架商品的数量
     */
    Integer countGoodsAll();

    /**
     * 通过id获取商品
     * @param id 商品的id
     * @return 商品
     */
    Goods getGoodsById(Integer id);

    /**
     * 得到店铺相同分类的商品
     * @param sellerId 店铺id
     * @param typeId 分类id
     * @return 所有符合要求商品
     */
    List<Goods> listGoodsByCategory(@Param(value = "sellerId") Integer sellerId, @Param(value = "typeId") Integer typeId);

    /**
     * 获取店铺中所有的商品
     * @param sellerId 店铺id
     * @return 所有商品
     */
    List<Goods> listGoodsBySellerAll(Integer sellerId);

    /**
     * 通过商品id获取收藏商品的买家数量
     * @param goodsId 商品的id
     * @return 买家数量
     */
    Integer countBuyerLikeGoods(Integer goodsId);
}
