package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.BuyerDetailDao;
import edu.cslg.easyshopping.pojo.BuyerDetail;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BuyerDetailService {
    @Resource
    private BuyerDetailDao buyerDetailDao;

    /**
     * 保存买家详情
     * @param buyerDetail 买家详情的参数
     */
    public void saveBuyerDetail(BuyerDetail buyerDetail){
        buyerDetailDao.saveBuyerDetail(buyerDetail);
    }
}
