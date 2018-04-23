package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.BuyerDao;
import edu.cslg.easyshopping.pojo.Buyer;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BuyerService {

    @Resource
    private BuyerDao buyerDao;

    /**
     * 保存买家
     * @param buyer 买家的参数
     */
    public void saveBuyer(Buyer buyer){
        buyerDao.saveBuyer(buyer);
    }

    /**
     * 通过手机号码获取买家
     * @param tel 手机号码
     * @return 买家对象
     */
    public Buyer getBuyerByTel(String tel){
        return buyerDao.getBuyerByTel(tel);
    }

    /**
     * 通过用户名手机号码验证登录
     * @param buyer 买家的输入
     * @return 买家对象
     */
    public Buyer getBuyerByLoginIdAndPwd(Buyer buyer){
        return buyerDao.getBuyerByLoginIdAndPwd(buyer);
    }

    /**
     * 通过id获取卖家
     * @param buyerId 卖家id
     * @return 卖家
     */
    public Buyer getBuyerById(Integer buyerId){
        return buyerDao.getBuyerById(buyerId);
    }

    /**
     * 买家收藏卖家
     * @param buyerId 买家id
     * @param sellerId 卖家id
     */
    public void buyerLikeSeller(Integer buyerId, Integer sellerId){
        buyerDao.buyerLikeSeller(buyerId,sellerId);
    }

    /**
     * 买家取消收藏卖家
     * @param buyerId 买家id
     * @param sellerId 卖家id
     */
    public void buyerDislikeSeller(Integer buyerId, Integer sellerId){
        buyerDao.buyerDislikeSeller(buyerId,sellerId);
    }

    /**
     * 买家是否收藏卖家
     * @param buyerId 买家id
     * @param sellerId 卖家id
     * @return 收藏与否
     */
    public Integer buyerLikeSellerOr(Integer buyerId,Integer sellerId){
        return buyerDao.buyerLikeSellerOr(buyerId,sellerId);
    }

    /**
     * 判断商品是否被收藏
     * @param buyerId 买家id
     * @param goodsId 商品id
     */
    public void buyerLikeGoods(Integer buyerId, Integer goodsId){
        buyerDao.buyerLikeGoods(buyerId,goodsId);
    }

    /**
     * 买家取消收藏商品
     * @param buyerId 买家id
     * @param goodsId 商品id
     */
    public void buyerDislikeGoods(Integer buyerId, Integer goodsId){
        buyerDao.buyerDislikeGoods(buyerId,goodsId);
    }

    /**
     * 买家是否收藏商品
     * @param buyerId 买家id
     * @param goodsId 商品id
     * @return 收藏与否
     */
    public Integer buyerLikeGoodsOr(Integer buyerId,Integer goodsId){
        return buyerDao.buyerLikeGoodsOr(buyerId,goodsId);
    }

    /**
     * 通过昵称获取用户
     * @param nickName 昵称
     * @return 买家
     */
    public Buyer getBuyerByNickName(String nickName){
        return buyerDao.getBuyerByNickName(nickName);
    }

    /**
     * 更新买家信息
     * @param buyer 参数
     */
    public void updateBuyer(Buyer buyer){
        buyerDao.updateBuyer(buyer);
    }

    /**
     * 分页查询买家信息
     * @param buyerIndex 买家开始索引
     * @param buyerSize 一页显示的买家
     * @return 当页的买家
     */
    public List<Buyer> listBuyer(Integer buyerIndex, Integer buyerSize){
        return buyerDao.listBuyer(buyerIndex, buyerSize);
    }

    /**
     * 获取买家的总数
     * @return 买家总数
     */
    public Integer countBuyer(){
        return buyerDao.countBuyer();
    }
}
