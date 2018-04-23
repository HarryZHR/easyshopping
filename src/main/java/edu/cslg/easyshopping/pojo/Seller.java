package edu.cslg.easyshopping.pojo;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class Seller {
    private Integer id;
    private String tel;
    private String storeName;
    private String hostName;
    private String pwd;
    private String sellerProvince;
    private String sellerCity;
    private String sellerDistrict;
    private String sellerStreet;
    private String supply;
    private Integer score;
    private String icon;
    private String introduce;
    private Stuff stuff;
    private Date shopTime;
    private Integer sale;
    private Integer complainCount;
    //是否允许店铺营业 0 代表 不允许，1 代表可以
    private boolean sellerStatus;
    private List<Buyer> buyerEnjoy;
    private List<Goods> goodses;
    private List<Order> orders;

    public Seller(){}

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSupply() {
        return supply;
    }

    public void setSupply(String supply) {
        this.supply = supply;
    }

    public String getSellerProvince() {
        return sellerProvince;
    }

    public void setSellerProvince(String sellerProvince) {
        this.sellerProvince = sellerProvince;
    }

    public String getSellerCity() {
        return sellerCity;
    }

    public void setSellerCity(String sellerCity) {
        this.sellerCity = sellerCity;
    }

    public String getSellerDistrict() {
        return sellerDistrict;
    }

    public void setSellerDistrict(String sellerDistrict) {
        this.sellerDistrict = sellerDistrict;
    }

    public String getSellerStreet() {
        return sellerStreet;
    }

    public void setSellerStreet(String sellerStreet) {
        this.sellerStreet = sellerStreet;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Stuff getStuff() {
        return stuff;
    }

    public void setStuff(Stuff stuff) {
        this.stuff = stuff;
    }

    public Date getShopTime() {
        return shopTime;
    }

    public void setShopTime(Date shopTime) {
        this.shopTime = shopTime;
    }

    public List<Buyer> getBuyerEnjoy() {
        return buyerEnjoy;
    }

    public void setBuyerEnjoy(List<Buyer> buyerEnjoy) {
        this.buyerEnjoy = buyerEnjoy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public Integer getComplainCount() {
        return complainCount;
    }

    public void setComplainCount(Integer complainCount) {
        this.complainCount = complainCount;
    }

    public boolean isSellerStatus() {
        return sellerStatus;
    }

    public void setSellerStatus(boolean sellerStatus) {
        this.sellerStatus = sellerStatus;
    }

    public List<Goods> getGoodses() {
        return goodses;
    }

    public void setGoodses(List<Goods> goodses) {
        this.goodses = goodses;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return Objects.equals(id, seller.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
