package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderDao {
    /**
     * 生成订单
     * @param order 订单的参数
     */
    void saveOrder(Order order);

    /**
     * 多个订单状态获取订单
     * @param small 小的状态
     * @param big 大的状态
     * @param buyerId 买家id
     * @param orderIndex 订单开头索引
     * @param pageSize 一页的个数
     * @return 订单
     */
    List<Order> listOrderByMoreStatusAndBuyer(@Param(value = "small") Integer small,@Param(value = "big") Integer big,@Param(value = "buyerId") Integer buyerId, @Param(value = "orderIndex")Integer orderIndex, @Param(value = "pageSize")Integer pageSize);

    /**
     * 多个订单状态获取订单数量
     * @param small 小的状态
     * @param big 大的状态
     * @param buyerId 买家id
     * @return 订单个数
     */
    Integer countOrderByMoreStatusAndBuyer(@Param(value = "small") Integer small,@Param(value = "big") Integer big,@Param(value = "buyerId") Integer buyerId);

    /**
     * 删除order
     * @param orderId 订单的id
     */
    void deleteOrder(Integer orderId);

    /**
     * 更新订单状态
     * @param order 订单参数
     */
    void updateOrderStatus(Order order);

    /**
     * 通过id获取订单
     * @param id id
     * @return 订单
     */
    Order getOrderById(Integer id);

    /**
     * 通过买家id获取店铺订单
     * @param sellerId id
     */
    List<Order> listOrderBySellerId(@Param(value = "sellerId") Integer sellerId,@Param(value = "orderIndex") Integer orderIndex,@Param(value = "orderSize") Integer orderSize);

    /**
     * 获取店铺的订单数量
     * @param sellerId id
     * @return 订单
     */
    Integer countOrderBySellerId(Integer sellerId);

    /**
     * 在店铺获取不同状态的订单
     * @param small 状态
     * @param big 后状态
     * @param sellerId 卖家id
     * @param orderIndex 订单开始的索引
     * @param orderSize 订单的数量
     * @return 订单
     */
    List<Order> listOrderByMoreStatusAndSeller(@Param(value = "small") Integer small,@Param(value = "big") Integer big,@Param(value = "sellerId") Integer sellerId,@Param(value = "orderIndex") Integer orderIndex,@Param(value = "orderSize") Integer orderSize);

    /**
     * 在店铺的不同状态的数量
     * @param small 状态
     * @param big 状态
     * @param sellerId 订单id
     * @return 数量
     */
    Integer countOrderByMoreStatusAndSeller(@Param(value = "small")Integer small, @Param(value = "big") Integer big,@Param(value = "sellerId") Integer sellerId);
}
