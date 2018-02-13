package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.SellerDao;
import edu.cslg.easyshopping.pojo.Seller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 卖家服务层处理
 * @author HarryZhang
 */
@Service
public class SellerService{
    @Resource
    private SellerDao sellerDao;

    public Seller getSellerByTel(String tel) {
        return sellerDao.getSellerByTel(tel);
    }

    public void saveSeller(Seller seller) {
        sellerDao.saveSeller(seller);
    }

    public Seller getSellerByTelAndPwd(Seller seller) {
        return sellerDao.getSellerByTelAndPwd(seller);
    }

    public void updateSeller(Seller seller){
        sellerDao.updateSeller(seller);
    }

    public Seller getSellerById(Integer id){
        return sellerDao.getSellerById(id);
    }
}
