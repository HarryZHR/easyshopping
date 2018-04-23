package edu.cslg.easyshopping.pojo;

import java.util.Date;

/**
 * 退货信息
 */
public class BackGoodsInfo {
    private Integer id;
    private String content;
    private String img;
    private Date backTime;
    private String backStatus;
    public BackGoodsInfo(){}

    public BackGoodsInfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getBackStatus() {
        return backStatus;
    }

    public void setBackStatus(String backStatus) {
        this.backStatus = backStatus;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
