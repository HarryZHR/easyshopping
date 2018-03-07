package edu.cslg.easyshopping.pojo;

import java.util.Date;

/**
 * 退货信息
 */
public class BackGoodsInfo {
    private Integer id;
    private OrderItem orderItem;
    private String content;
    private Date backTime;

    public BackGoodsInfo(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }
}
