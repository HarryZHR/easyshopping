package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemDao {
    /**
     * 产生订单详情
     * @param orderItem 订单详情的参数
     */
    void saveOrderItem(OrderItem orderItem);

    /**
     * 通过id获取订单详情
     * @param id 订单id
     * @return 订单详情
     */
    OrderItem getOrderItemById(Integer id);

    /**
     * 更新订单详情
     * @param orderItem 订单详情的参数
     */
    void updateOrderItem(OrderItem orderItem);
}
