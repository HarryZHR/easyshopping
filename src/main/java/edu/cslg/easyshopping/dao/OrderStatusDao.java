package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.OrderStatus;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderStatusDao {
    /**
     * 通过id获取订单状态
     * @param orderStatusId 订单状态的id
     * @return 订单状态
     */
    OrderStatus getOrderStatusById(Integer orderStatusId);
}
