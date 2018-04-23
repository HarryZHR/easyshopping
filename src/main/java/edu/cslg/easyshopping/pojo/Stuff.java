package edu.cslg.easyshopping.pojo;

import org.springframework.stereotype.Component;

@Component
public class Stuff {
    private Integer id;
    private String faceImg;
    private String oppositeImg;
    private String handImg;
    private boolean stuffStatus;
    private String reason;
    private Seller seller;

    public Stuff() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaceImg() {
        return faceImg;
    }

    public void setFaceImg(String faceImg) {
        this.faceImg = faceImg;
    }

    public String getOppositeImg() {
        return oppositeImg;
    }

    public void setOppositeImg(String oppositeImg) {
        this.oppositeImg = oppositeImg;
    }

    public String getHandImg() {
        return handImg;
    }

    public void setHandImg(String handImg) {
        this.handImg = handImg;
    }

    public boolean isStuffStatus() {
        return stuffStatus;
    }

    public void setStuffStatus(boolean stuffStatus) {
        this.stuffStatus = stuffStatus;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
