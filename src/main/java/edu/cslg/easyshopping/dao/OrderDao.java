package edu.cslg.easyshopping.dao;

import com.sun.org.apache.xpath.internal.operations.Or;
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
     * 分页获取所有订单
     * @param buyerId 买家id
     * @param orderIndex 订单的索引
     * @param pageSize 一页订单的数量
     * @return 订单
     */
//    List<Order> listOrderAllByBuyer(@Param(value = "buyerId") Integer buyerId, @Param(value = "orderIndex")Integer orderIndex,@Param(value = "pageSize")Integer pageSize);

    /**
     * 获取买家所有的订单总数
     * @param buyerId 买家id
     * @return 总数
     */
//    Integer countOrderAllByBuyer(Integer buyerId);

    /**
     * 根据订单状态获取订单
     * @param buyerId 买家id
     * @param orderIndex 订单的索引
     * @param pageSize 一页的订单数量
     * @return 订单
     */
    List<Order> listOrderByStatusAndBuyer(@Param(value = "buyerId") Integer buyerId, @Param(value = "orderIndex")Integer orderIndex, @Param(value = "pageSize")Integer pageSize,@Param(value = "orderStatusId") Integer orderStatusId);

    /**
     * 根据订单状态获取不同的订单数量
     * @param buyerId 买家id
     * @return 数量
     */
    Integer countOrderByStatusAndBuyer(@Param(value = "buyerId") Integer buyerId,@Param(value = "orderStatusId") Integer orderStatusId);
}
