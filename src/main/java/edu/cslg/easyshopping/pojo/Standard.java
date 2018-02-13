package edu.cslg.easyshopping.pojo;

import org.springframework.stereotype.Component;

@Component
public class Standard {
    private Integer id;
    private Goods goods;
    private String color;
    private String colorImg;
    private String size;
    private Float price;
    private Integer count;

    public Standard() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorImg() {
        return colorImg;
    }

    public void setColorImg(String colorImg) {
        this.colorImg = colorImg;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
