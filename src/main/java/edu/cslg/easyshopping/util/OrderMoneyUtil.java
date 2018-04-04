package edu.cslg.easyshopping.util;

import edu.cslg.easyshopping.pojo.Order;
import edu.cslg.easyshopping.pojo.OrderItem;

import java.util.List;

/**
 * 订单的总金额工具类
 */
public class OrderMoneyUtil {
    /**
     * 求出订单的总金额
     * @param orders 订单
     * @return 订单
     */
    public static List<Order> setOrderListMoney(List<Order> orders){
        for (Order order : orders){
            Float orderMoney = 0.0f;
            for (OrderItem orderItem : order.getOrderItems()){
                orderMoney += (orderItem.getStandard().getPrice() - orderItem.getStandard().getGoods().getDiscount()) * orderItem.getBuyCount();
            }
            order.setOrderMoney(orderMoney);
        }
        return orders;
    }
}
