package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.OrderItemDao;
import edu.cslg.easyshopping.pojo.OrderItem;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderItemService {

    @Resource
    private OrderItemDao orderItemDao;

    /**
     * 产生订单详情
     * @param orderItem 订单详情的参数
     */
    public void saveOrderItem(OrderItem orderItem){
        orderItemDao.saveOrderItem(orderItem);
    }

    /**
     * 通过id获取订单详情
     * @param id 订单id
     * @return 订单详情
     */
    public OrderItem getOrderItemById(Integer id){
        return orderItemDao.getOrderItemById(id);
    }

    /**
     * 更新订单详情
     * @param orderItem 订单详情参数
     */
    public void updateOrderItem(OrderItem orderItem){
            orderItemDao.updateOrderItem(orderItem);
    }
}
