package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Complain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ComplainDao {
    /**
     * 保存投诉
     * @param complain 投诉参数
     */
    void saveComplain(Complain complain);

    /**
     * 分页获取待处理投诉
     * @param complainIndex 投诉的开始索引
     * @param complainSize 一页多少的投诉
     * @return 投诉的对象
     */
    List<Complain> listComplainWaitCheck(@Param(value = "complainIndex") Integer complainIndex, @Param(value = "complainSize") Integer complainSize,@Param(value = "type") String type);

    /**
     * 待处理的投诉的数量
     * @return 数量
     */
    Integer countComplainWaitCheck(@Param(value = "type") String type);

    /**
     * 通过id获取complain
     * @param id complainId
     * @return 投诉
     */
    Complain getComplainById(Integer id);

    /**
     * 更新投诉状态
     * @param complain 参数
     */
    void updateComplain(Complain complain);
}
