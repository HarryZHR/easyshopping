package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.CartItemDao;
import edu.cslg.easyshopping.pojo.CartItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CartItemService {
    @Resource
    private CartItemDao cartItemDao;

    /**
     * 保存购物详情
     * @param cartItem 购物详情参数
     */
    public void saveCartItem(CartItem cartItem){
        cartItemDao.saveCartItem(cartItem);
    }

    /**
     * 通过买家和规格判断购物车是否已存在
     * @param buyerId 买家id
     * @param standardId 规格id
     * @return 购物车详情
     */
    public CartItem getCartItemByStandardIdAndBuyerId(Integer buyerId, Integer standardId){
        return cartItemDao.getCartItemByStandardIdAndBuyerId(buyerId, standardId);
    }

    /**
     * 更新购物详情
     * @param cartItem 购物详情
     */
    public void updateCartItem(CartItem cartItem){
       cartItemDao.updateCartItem(cartItem);
    }

    /**
     * 通过买家id获取购物详情
     * @param buyerId 买家id
     * @return 购物详情
     */
    public List<CartItem> listCartItemByBuyerId(Integer buyerId){
        return cartItemDao.listCartItemByBuyerId(buyerId);
    }

    /**
     * 通过id获取购物车详情
     * @param id 购物车id
     * @return 购物车详情对象
     */
    public CartItem getCartItemById(Integer id){
        return cartItemDao.getCartItemById(id);
    }

    /**
     * 删除购物车详情
     * @param id 购物车详情
     */
    public void deleteCartItem(Integer id){
        cartItemDao.deleteCartItem(id);
    }
}
