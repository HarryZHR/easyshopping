package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.BuyerDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BuyerDetailDao {
    /**
     * 保存买家详情
     * @param buyerDetail 买家详情的参数
     */
    void saveBuyerDetail(BuyerDetail buyerDetail);
}
