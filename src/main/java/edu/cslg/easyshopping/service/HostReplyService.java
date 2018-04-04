package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.HostReplyDao;
import edu.cslg.easyshopping.pojo.HostReply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HostReplyService {
    @Resource
    private HostReplyDao hostReplyDao;

    /**
     * 保存主人回复
     * @param hostReply 主人回复参数
     */
    public void saveHostReply(HostReply hostReply){
        hostReplyDao.saveHostReply(hostReply);
    }
}
