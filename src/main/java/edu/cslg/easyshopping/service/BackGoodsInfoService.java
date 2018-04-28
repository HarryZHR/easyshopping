package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.BackGoodsInfoDao;
import edu.cslg.easyshopping.pojo.BackGoodsInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BackGoodsInfoService {

    @Resource
    private BackGoodsInfoDao backGoodsInfoDao;

    /**
     * 保存退货的信息
     * @param backGoodsInfo 退货的参数
     */
    public void saveBackGoodsInfo(BackGoodsInfo backGoodsInfo){
        backGoodsInfoDao.saveBackGoodsInfo(backGoodsInfo);
    }

    /**
     * 更新退货信息
     * @param backGoodsInfo 退货信息
     */
    public void updateBackGoodsInfo(BackGoodsInfo backGoodsInfo){
        backGoodsInfoDao.updateBackGoodsInfo(backGoodsInfo);
    }

    /**
     * 通过id获取退货信息
     * @param id id
     * @return 退货信息对象
     */
    public BackGoodsInfo getBackGoodsInfoById(Integer id){
        return backGoodsInfoDao.getBackGoodsInfoById(id);
    }

}
