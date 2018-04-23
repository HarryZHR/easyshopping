package edu.cslg.easyshopping.pojo;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Buyer {
    private Integer id;
    private String nickName;
    private String pwd;
    private String tel;
    private String headImg;
    private Date registerDate;
    private boolean buyerStatus;
    private BuyerDetail buyerDetail;
    private List<Seller> enjoySellers;
    private List<Goods> enjoyGoods;
    private List<CartItem> cartItems;
    private List<Order> orders;
    private List<Address> addresses;
    private List<Reply> replies;
    private Integer orderNum;
    private Float payMoney;

    public Buyer() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public BuyerDetail getBuyerDetail() {
        return buyerDetail;
    }

    public void setBuyerDetail(BuyerDetail buyerDetail) {
        this.buyerDetail = buyerDetail;
    }

    public List<Seller> getEnjoySellers() {
        return enjoySellers;
    }

    public void setEnjoySellers(List<Seller> enjoySellers) {
        this.enjoySellers = enjoySellers;
    }

    public List<Goods> getEnjoyGoods() {
        return enjoyGoods;
    }

    public void setEnjoyGoods(List<Goods> enjoyGoods) {
        this.enjoyGoods = enjoyGoods;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public boolean isBuyerStatus() {
        return buyerStatus;
    }

    public void setBuyerStatus(boolean buyerStatus) {
        this.buyerStatus = buyerStatus;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Float getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Float payMoney) {
        this.payMoney = payMoney;
    }
}
