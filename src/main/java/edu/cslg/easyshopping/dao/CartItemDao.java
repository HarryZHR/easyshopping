package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartItemDao {
    /**
     * 保存购物详情
     * @param cartItem 购物详情参数
     */
    void saveCartItem(CartItem cartItem);

    /**
     * 通过买家和规格判断购物车是否已存在
     * @param buyerId 买家id
     * @param standardId 规格id
     * @return 购物车详情
     */
    CartItem getCartItemByStandardIdAndBuyerId(@Param(value = "buyerId") Integer buyerId,@Param(value = "standardId") Integer standardId);

    /**
     * 更新购物详情
     * @param cartItem 购物详情参数
     */
    void updateCartItem(CartItem cartItem);

    /**
     * 通过买家id获取购物详情
     * @param buyerId 买家id
     * @return 购物详情
     */
    List<CartItem> listCartItemByBuyerId(Integer buyerId);

    /**
     * 通过id获取购物车详情
     * @param id 购物车id
     * @return 购物车详情对象
     */
    CartItem getCartItemById(Integer id);

    /**
     * 删除购物车详情
     * @param id 购物车详情
     */
    void deleteCartItem(Integer id);
}
