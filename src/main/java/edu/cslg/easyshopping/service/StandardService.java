package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.StandardDao;
import edu.cslg.easyshopping.pojo.Standard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StandardService {

    @Resource
    private StandardDao standardDao;

    /**
     * 保存规格
     * @param standard 规格参数
     */
    public void saveStandard(Standard standard){
        standardDao.saveStandard(standard);
    }

    /*
    public List<Standard> listStandardByGoodsId(Integer id){
        return standardDao.listStandardByGoodsId(id);
    }*/

    /**
     * 为购物车找规格
     * @param goodsId 商品id
     * @param color 颜色
     * @param size 尺寸
     * @return 规格
     */
    /*public Standard getStandardToCart(Integer goodsId,String color,String size){
        return standardDao.getStandardToCart(goodsId, color, size);
    }*/

    /**
     * 通过goodsId 尺码和颜色获取规格
     * @param goodsId 商品id
     * @param size 尺寸
     * @param color 颜色
     * @return 规格
     */
    public Standard getStandardByIdAndSizeAndColor(Integer goodsId, String size, String color){
        return standardDao.getStandardByIdAndSizeAndColor(goodsId, size, color);
    }

    /**
     * 更新规格
     * @param standard 规格的参数
     */
    public void updateStandard(Standard standard){
        standardDao.updateStandard(standard);
    }

    /**
     * 通过商品的id和颜色获得规格
     * @param goodsId 商品id
     * @param color 颜色
     * @return 规格
     */
    public List<Standard> listStandardByGoodsIdAndColor(Integer goodsId, String color){
        return standardDao.listStandardByGoodsIdAndColor(goodsId, color);
    }

    /**
     * 通过商品的id和尺寸获得规格
     * @param goodsId 商品的id
     * @param size 尺寸
     * @return 规格
     */
    public List<Standard> listStandardByGoodsIdAndSize(Integer goodsId, String size){
        return standardDao.listStandardByGoodsIdAndSize(goodsId, size);
    }

}
