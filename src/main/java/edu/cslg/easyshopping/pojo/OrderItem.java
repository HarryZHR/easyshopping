package edu.cslg.easyshopping.pojo;

public class OrderItem {
    private Integer id;
    private Integer buyCount;
    private String sendNum;
    private Order order;
    private Standard standard;
    private BackGoodsInfo backGoodsInfo;
    private Complain complain;

    public OrderItem(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getSendNum() {
        return sendNum;
    }

    public void setSendNum(String sendNum) {
        this.sendNum = sendNum;
    }

    public BackGoodsInfo getBackGoodsInfo() {
        return backGoodsInfo;
    }

    public void setBackGoodsInfo(BackGoodsInfo backGoodsInfo) {
        this.backGoodsInfo = backGoodsInfo;
    }

    public Complain getComplain() {
        return complain;
    }

    public void setComplain(Complain complain) {
        this.complain = complain;
    }
}
