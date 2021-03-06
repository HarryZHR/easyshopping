package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Seller;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author HarryZhang
 * 商品处理的接口
 */
@Mapper
public interface SellerDao {

    /**
     * 通过手机号码得到卖家
     * @param tel 手机号码
     * @return 卖家
     */
    Seller getSellerByTel(String tel);

    /**
     * 卖家注册
     */
    void saveSeller(Seller seller);

    /**
     * 验证卖家登录
     * @param seller 登录的卖家
     * @return 返回验证结果
     */
    Seller getSellerByTelAndPwd(Seller seller);

    /**
     * 通过id获取卖家
     * @param id 商家id
     * @return 商家
     */
    Seller getSellerById(Integer id);

    /**
     * 通过id查找店铺，不考虑店铺状态
     * @param id 店铺id
     * @return 店铺对象
     */
    Seller getSellerAllById(Integer id);
    /**
     * 更新卖家信息
     * @param seller 卖家信息
     */
    void updateSeller(Seller seller);

    /**
     * 通过买家id获取收藏的店铺
     * @param buyerId 买家id
     * @return 所有收藏的店铺
     */
    List<Seller> listSellerLikeByBuyerId(Integer buyerId);

    /**
     * 分页获取卖家
     * @param sellerIndex 开始的索引
     * @param sellerSize 一页的数量
     * @return 卖家对象
     */
    List<Seller> listSeller(@Param(value = "sellerIndex") Integer sellerIndex,@Param(value = "sellerSize") Integer sellerSize);

    /**
     * 获取卖家的数量
     * @return 数量
     */
    Integer countSeller();
}
