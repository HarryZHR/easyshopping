package edu.cslg.easyshopping.pojo;

import java.util.Date;

public class HostReply {
    private Integer id;
    private String content;
    private Date hostReplyTime;

    public HostReply() {
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

    public Date getHostReplyTime() {
        return hostReplyTime;
    }

    public void setHostReplyTime(Date hostReplyTime) {
        this.hostReplyTime = hostReplyTime;
    }

}
