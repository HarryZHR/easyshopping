package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.ReplyDao;
import edu.cslg.easyshopping.pojo.Reply;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplyService {
    @Resource
    private ReplyDao replyDao;

    /**
     * 通过规格id获取回复
     * @param standardId 规格id
     * @return 所有的规格
     */
    public List<Reply> listReplyByStandardId(Integer standardId){
        return replyDao.listReplyByStandardId(standardId);
    }
}
