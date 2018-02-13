package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.GoodsType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品分类的接口
 */
@Mapper
public interface GoodsTypeDao {
    /**
     * 获取所有商品的分类
     * @return 商品分类的集合
     */
    List<GoodsType> listGoodsType();

    /**
     * 通过类别名称获取商品类型
     * @param type 类别名称
     * @return 商品类型
     */
    GoodsType getGoodsTypeByType(String type);

    /**
     * 通过id获取商品类型
     * @param id 商品id
     * @return 商品类型
     */
    GoodsType getGoodsTypeById(Integer id);
}
