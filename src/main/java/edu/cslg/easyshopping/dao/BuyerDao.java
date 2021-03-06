package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Buyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BuyerDao {
    /**
     * 保存买家
     * @param buyer 买家的参数
     */
    void saveBuyer(Buyer buyer);

    /**
     * 通过号码获得买家
     * @param tel 号码
     * @return 相应的买家
     */
    Buyer getBuyerByTel(String tel);

    /**
     * 通过用户名或者手机号码登录
     * @param buyer 买家的用户名和密码
     * @return 获得的买家
     */
    Buyer getBuyerByLoginIdAndPwd(Buyer buyer);

    /**
     * 通过id获取卖家
     * @param buyerId 卖家id
     * @return 卖家对象
     */
    Buyer getBuyerById(Integer buyerId);

    /**
     * 买家收藏卖家
     * @param buyerId 买家id
     * @param sellerId 卖家id
     */
    void buyerLikeSeller(@Param(value = "buyerId") Integer buyerId, @Param(value = "sellerId") Integer sellerId);

    /**
     * 买家取消收藏卖家
     * @param buyerId 买家id
     * @param sellerId 卖家id
     */
    void buyerDislikeSeller(@Param(value = "buyerId") Integer buyerId, @Param(value = "sellerId") Integer sellerId);
    /**
     * 判断店铺是否被收藏
     * @param buyerId 买家id
     * @param sellerId 卖家id
     * @return 匹配数量
     */
    Integer buyerLikeSellerOr(@Param(value = "buyerId") Integer buyerId, @Param(value = "sellerId") Integer sellerId);

    /**
     * 判断商品是否被收藏
     * @param buyerId 买家id
     * @param goodsId 商品id
     */
    void buyerLikeGoods(@Param(value = "buyerId") Integer buyerId, @Param(value = "goodsId") Integer goodsId);

    /**
     * 买家取消收藏商品
     * @param buyerId 买家id
     * @param goodsId 商品id
     */
    void buyerDislikeGoods(@Param(value = "buyerId") Integer buyerId, @Param(value = "goodsId") Integer goodsId);

    /**
     * 判断商品是否被收藏
     * @param buyerId 买家id
     * @param goodsId 商品id
     * @return 匹配数量
     */
    Integer buyerLikeGoodsOr(@Param(value = "buyerId") Integer buyerId, @Param(value = "goodsId") Integer goodsId);

    /**
     * 通过昵称获取用户
     * @param nickName 昵称
     * @return 买家
     */
    Buyer getBuyerByNickName(String nickName);

    /**
     * 更新买家信息
     * @param buyer 参数
     */
    void updateBuyer(Buyer buyer);

    /**
     * 分页查询买家信息
     * @param buyerIndex 买家开始索引
     * @param buyerSize 一页显示的买家
     * @return 当页的买家
     */
    List<Buyer> listBuyer(@Param(value = "buyerIndex") Integer buyerIndex, @Param(value = "buyerSize") Integer buyerSize);

    /**
     * 获取买家的总数
     * @return 买家总数
     */
    Integer countBuyer();
}
