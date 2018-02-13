package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Standard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StandardDao {

    /**
     * 保存商品规格
     * @param standard 商品的规格
     */
    void saveStandard(Standard standard);

    /**
     * 通过商品的id获取商品规格
     * @param goodsId 商品的id
     * @return 商品的规格
     */
   /* List<Standard> listStandardByGoodsId(Integer goodsId);*/

    /**
     * 通过规格id获取商品规格
     * @param id 商品的规格id
     * @return 商品的规格
     */
    Standard getStandardById(Integer id);

    /**
     * 通过商品的id，颜色和尺寸来获取规格
     * @param goodsId 商品id
     * @param color 颜色
     * @param size 尺寸
     * @return 规格
     */
    Standard getStandardToCart(@Param(value = "goodsId") Integer goodsId, @Param(value = "color") String color,@Param(value = "size") String size);
}
