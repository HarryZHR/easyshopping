package edu.cslg.easyshopping.service;

import com.sun.org.apache.xpath.internal.operations.Or;
import edu.cslg.easyshopping.dao.OrderDao;
import edu.cslg.easyshopping.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    private OrderDao orderDao;
    /**
     * 生成订单
     * @param order 订单的参数
     */
    public void saveOrder(Order order){
        orderDao.saveOrder(order);
    }

    /**
     * 多个订单状态获取订单
     * @param small 小的状态
     * @param big 大的状态
     * @param buyerId 买家id
     * @param orderIndex 订单开头索引
     * @param pageSize 一页的个数
     * @return 订单
     */
    public List<Order> listOrderByMoreStatusAndBuyer(Integer small, Integer big, Integer buyerId, Integer orderIndex, Integer pageSize){
        return orderDao.listOrderByMoreStatusAndBuyer(small, big, buyerId, orderIndex, pageSize);
    }

    /**
     * 多个订单状态获取订单数量
     * @param small 小的状态
     * @param big 大的状态
     * @param buyerId 买家id
     * @return 订单个数
     */
    public Integer countOrderByMoreStatusAndBuyer(Integer small, Integer big, Integer buyerId){
        return orderDao.countOrderByMoreStatusAndBuyer(small, big, buyerId);
    }

    /**
     * 删除order
     * @param orderId 订单的id
     */
    public void deleteOrder(Integer orderId){
        orderDao.deleteOrder(orderId);
    }

    /**
     * 更新订单状态
     * @param order 订单参数
     */
    public void updateOrderStatus(Order order){
        orderDao.updateOrderStatus(order);
    }

    /**
     * 通过id获取订单
     * @param id 订单的id
     * @return 订单
     */
    public Order getOrderById(Integer id){
        return orderDao.getOrderById(id);
    }
}
