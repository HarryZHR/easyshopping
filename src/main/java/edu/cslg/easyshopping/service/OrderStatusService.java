package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.OrderStatusDao;
import edu.cslg.easyshopping.pojo.OrderStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderStatusService {

    @Resource
    private OrderStatusDao orderStatusDao;
    /**
     * 通过id获取订单状态
     * @param orderStatusId 订单状态的id
     * @return 订单状态
     */
    public OrderStatus getOrderStatusById(Integer orderStatusId){
        return orderStatusDao.getOrderStatusById(orderStatusId);
    }
}
