package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Stuff;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    /**
     * 管理员需要的
     * 未审核的申请材料
     * @return 未审核的申请
     */
    List<Stuff> listStuffByStatus();
}
