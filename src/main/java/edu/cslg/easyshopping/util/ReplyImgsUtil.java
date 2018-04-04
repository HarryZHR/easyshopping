package edu.cslg.easyshopping.util;

import edu.cslg.easyshopping.pojo.Reply;

import java.util.ArrayList;
import java.util.List;

public class ReplyImgsUtil {
    public static List<Reply> setReplyImgs(List<Reply> replies){
        for (Reply reply: replies) {
            List<String> imgs = new ArrayList<>();
            String img = reply.getImg();
            String[] imgArr = img.split(",");
            for (String imgStr : imgArr){
                imgs.add(imgStr);
            }
            reply.setImgs(imgs);
        }
        return replies;
    }
}
