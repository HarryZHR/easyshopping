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

    /**
     * 通过买家id获取店铺订单
     * @param sellerId id
     */
    public List<Order> listOrderBySellerId(Integer sellerId, Integer orderIndex, Integer orderSize){
        return orderDao.listOrderBySellerId(sellerId, orderIndex, orderSize);
    }

    /**
     * 获取店铺的订单数量
     * @param sellerId id
     * @return 订单
     */
    public Integer countOrderBySellerId(Integer sellerId){
        return orderDao.countOrderBySellerId(sellerId);
    }

    /**
     * 在店铺获取不同状态的订单
     * @param small 状态
     * @param big 后状态
     * @param sellerId 卖家id
     * @param orderIndex 订单开始的索引
     * @param orderSize 订单的数量
     * @return 订单
     */
    public List<Order> listOrderByMoreStatusAndSeller(Integer small, Integer big, Integer sellerId, Integer orderIndex, Integer orderSize){
        return orderDao.listOrderByMoreStatusAndSeller(small, big, sellerId, orderIndex, orderSize);
    }

    /**
     * 在店铺的不同状态的数量
     * @param small 状态
     * @param big 状态
     * @param sellerId 订单id
     * @return 数量
     */
    public Integer countOrderByMoreStatusAndSeller(Integer small, Integer big,Integer sellerId){
        return orderDao.countOrderByMoreStatusAndSeller(small, big, sellerId);
    }
}
