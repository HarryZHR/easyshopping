package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Stuff;
import org.apache.ibatis.annotations.Mapper;

/**
 * 店铺申请材料
 */
@Mapper
public interface StuffDao {
    /**
     * 插入申请材料
     * @param stuff 申请材料对象
     */
    void saveStuff(Stuff stuff);
}
