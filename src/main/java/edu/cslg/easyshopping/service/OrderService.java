package edu.cslg.easyshopping.service;

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
     * 分页获取所有订单
     * @param buyerId 买家id
     * @param orderIndex 订单的索引
     * @param pageSize 一页订单的数量
     * @return 订单
     */
    /*public List<Order> listOrderAllByBuyer(Integer buyerId,Integer orderIndex,Integer pageSize){
        return orderDao.listOrderAllByBuyer(buyerId, orderIndex, pageSize);
    }*/

    /**
     * 获取买家所有的订单总数
     * @param buyerId 买家id
     * @return 总数
     */
    /*public Integer countOrderAllByBuyer(Integer buyerId){
        return orderDao.countOrderAllByBuyer(buyerId);
    }*/

    /**
     * 根据订单状态获取订单
     * @param buyerId 买家id
     * @param orderIndex 订单的索引
     * @param pageSize 一页的订单数量
     * @return 订单
     */
    public List<Order> listOrderByStatusAndBuyer(Integer buyerId,Integer orderIndex,Integer pageSize,Integer orderStatusId){
        return orderDao.listOrderByStatusAndBuyer(buyerId, orderIndex, pageSize, orderStatusId);
    }

    /**
     * 根据订单状态获取不同的订单数量
     * @param buyerId 买家id
     * @return 数量
     */
    public Integer countOrderByStatusAndBuyer(Integer buyerId,Integer orderStatusId){
        return orderDao.countOrderByStatusAndBuyer(buyerId,orderStatusId);
    }
}
