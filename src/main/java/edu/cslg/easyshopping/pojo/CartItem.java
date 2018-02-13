package edu.cslg.easyshopping.pojo;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public class CartItem {
    private Integer id;
    private Integer buyCount;
    private Standard standard;
    private Buyer buyer;

    public CartItem (){}

    public CartItem(Integer buyCount, Standard standard, Buyer buyer) {
        this.buyCount = buyCount;
        this.standard = standard;
        this.buyer = buyer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

}
