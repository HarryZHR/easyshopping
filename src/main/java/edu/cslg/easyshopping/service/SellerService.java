package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.SellerDao;
import edu.cslg.easyshopping.pojo.Seller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 卖家服务层处理
 * @author HarryZhang
 */
@Service
public class SellerService{
    @Resource
    private SellerDao sellerDao;

    /**
     * 通过电话获取卖家
     * @param tel 电话
     * @return 卖家
     */
    public Seller getSellerByTel(String tel) {
        return sellerDao.getSellerByTel(tel);
    }

    /**
     * 保存卖家
     * @param seller 卖家参数
     */
    public void saveSeller(Seller seller) {
        sellerDao.saveSeller(seller);
    }

    /**
     * 通过电话和密码获取卖家
     * @param seller 卖家登录的参数
     * @return 卖家对象
     */
    public Seller getSellerByTelAndPwd(Seller seller) {
        return sellerDao.getSellerByTelAndPwd(seller);
    }

    /**
     * 更新卖家对象
     * @param seller 卖家参数
     */
    public void updateSeller(Seller seller){
        sellerDao.updateSeller(seller);
    }

    /**
     * 通过id获取卖家
     * @param id 卖家id
     * @return 卖家
     */
    public Seller getSellerById(Integer id){
        return sellerDao.getSellerById(id);
    }

    /**
     * 通过id查找店铺，不考虑店铺状态
     * @param id 店铺id
     * @return 店铺对象
     */
    public Seller getSellerAllById(Integer id){
        return sellerDao.getSellerAllById(id);
    }

    /**
     * 通过买家id获取收藏的店铺
     * @param buyerId 买家id
     * @return 所有收藏的店铺
     */
    public List<Seller> listSellerLikeByBuyerId(Integer buyerId){
        return sellerDao.listSellerLikeByBuyerId(buyerId);
    }

    public List<Seller> listSeller(Integer sellerIndex, Integer sellerSize){
        return sellerDao.listSeller(sellerIndex, sellerSize);
    }

    public Integer countSeller(){
        return sellerDao.countSeller();
    }
}
