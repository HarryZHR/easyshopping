package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.BackGoodsInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BackGoodsInfoDao {
    /**
     * 保存退货的信息
     * @param backGoodsInfo 退货的参数
     */
    void saveBackGoodsInfo(BackGoodsInfo backGoodsInfo);

    /**
     * 更新退货信息
     * @param backGoodsInfo 退货信息
     */
    void updateBackGoodsInfo(BackGoodsInfo backGoodsInfo);

    /**
     * 通过id获取退货信息
     * @param id id
     * @return 退货信息对象
     */
    BackGoodsInfo getBackGoodsInfoById(Integer id);

}
