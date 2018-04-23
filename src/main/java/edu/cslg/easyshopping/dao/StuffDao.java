package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Stuff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.INACTIVE;

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
    List<Stuff> listStuffByStatus(@Param(value = "stuffIndex") Integer stuffIndex,@Param(value = "stuffSize") Integer stuffSize);

    /**
     * 未审核的申请材料数量
     * @return 数量
     */
    Integer countStuffByStatus();

    /**
     * 通过id获取申请材料
     * @param id stuffId
     * @return 申请材料
     */
    Stuff getStuffById(Integer id);

    /**
     * 更新申请材料
     * @param stuff 申请材料对象
     */
    void updateStuff(Stuff stuff);
}
