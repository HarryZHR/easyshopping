package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.StandardDao;
import edu.cslg.easyshopping.pojo.Standard;
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

/*    public List<Standard> listStandardByGoodsId(Integer id){
        return standardDao.listStandardByGoodsId(id);
    }*/

    /**
     * 为购物车找规格
     * @param goodsId 商品id
     * @param color 颜色
     * @param size 尺寸
     * @return 规格
     */
    public Standard getStandardToCart(Integer goodsId,String color,String size){
        return standardDao.getStandardToCart(goodsId, color, size);
    }
}
