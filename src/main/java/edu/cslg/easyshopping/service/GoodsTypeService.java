package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.GoodsTypeDao;
import edu.cslg.easyshopping.pojo.GoodsType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class GoodsTypeService {

    @Resource
    private GoodsTypeDao goodsTypeDao;

    public List<GoodsType> listGoodsType() {
        return goodsTypeDao.listGoodsType();
    }

    public GoodsType getGoodsTypeByType(String type){
        return goodsTypeDao.getGoodsTypeByType(type);
    }
}
