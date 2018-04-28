package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
     * @param orderItem 订单详情参数
     */
    void updateOrderItem(OrderItem orderItem);

    /**
     * 分页获取退货的订单详情
     * @param id 买家或者卖家id
     * @param orderItemIndex 开始的订单详情索引
     * @param orderItemSize 每页显示的条数
     * @return 订单详情集合
     */
    List<OrderItem> listOrderItemByBuyerOrSeller(@Param(value = "id") Integer id, @Param(value = "orderItemIndex") Integer orderItemIndex, @Param(value = "orderItemSize")Integer orderItemSize,@Param(value = "type")String type, @Param(value = "status") Integer status);

    /**
     * 获取退货订单详情的数量
     * @param id 买家或者卖家的id
     * @return 数量
     */
    Integer countOrderItemByBuyerOrSeller(@Param(value = "id") Integer id, @Param(value = "type")String type, @Param(value = "status") Integer status);

}
