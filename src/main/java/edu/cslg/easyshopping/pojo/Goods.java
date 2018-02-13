package edu.cslg.easyshopping.pojo;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品实体类
 */
public class Goods {
    private Integer id;
    private String title;
    private String introduce;
    private Float minPrice;
    private Float discount;
    private Integer saleCount;
    private Date goodsTime;
    private GoodsType type;
    private	Seller seller;
    private Boolean status;
    private Integer activity;
    private Integer allCount;
    private List<Standard> standards;
    private GoodsImg goodsImg;
    private List<Buyer> buyerEnjoy;
    private Integer goodRelyCount;
    private List<String> sizes;
    private Map<String,String> colorMap;
    private List<String> goodsImgDetail;

    public Goods(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Float getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Date getGoodsTime() {
        return goodsTime;
    }

    public void setGoodsTime(Date goodsTime) {
        this.goodsTime = goodsTime;
    }

    public GoodsType getType() {
        return type;
    }

    public void setType(GoodsType type) {
        this.type = type;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public List<Buyer> getBuyerEnjoy() {
        return buyerEnjoy;
    }

    public void setBuyerEnjoy(List<Buyer> buyerEnjoy) {
        this.buyerEnjoy = buyerEnjoy;
    }

    public List<Standard> getStandards() {
        return standards;
    }

    public void setStandards(List<Standard> standards) {
        this.standards = standards;
    }

    public Integer getGoodRelyCount() {
        return goodRelyCount;
    }

    public void setGoodRelyCount(Integer goodRelyCount) {
        this.goodRelyCount = goodRelyCount;
    }

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public GoodsImg getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(GoodsImg goodsImg) {
        this.goodsImg = goodsImg;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public Map<String, String> getColorMap() {
        return colorMap;
    }

    public void setColorMap(Map<String, String> colorMap) {
        this.colorMap = colorMap;
    }

    public List<String> getGoodsImgDetail() {
        return goodsImgDetail;
    }

    public void setGoodsImgDetail(List<String> goodsImgDetail) {
        this.goodsImgDetail = goodsImgDetail;
    }
}
