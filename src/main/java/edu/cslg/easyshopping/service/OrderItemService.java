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

    /**
     * 分页获取退货的订单详情
     * @param id 买家id
     * @param orderItemIndex 开始的订单详情索引
     * @param orderItemSize 每页显示的条数
     * @return 订单详情集合
     */
    public List<OrderItem> listOrderItemByBuyerOrSeller(Integer id, Integer orderItemIndex, Integer orderItemSize,String type,Integer status){
        return orderItemDao.listOrderItemByBuyerOrSeller(id, orderItemIndex, orderItemSize,type,status);
    }

    /**
     * 获取退货订单详情的数量
     * @param id 买家的id
     * @return 数量
     */
    public Integer countOrderItemByBuyerOrSeller(Integer id,String type,Integer status){
        return orderItemDao.countOrderItemByBuyerOrSeller(id,type,status);
    }
}
