package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyDao {
    /**
     * 通过id获取评论
     * @param id 评论的id
     * @return 评论
     */
    Reply getReplyById(Integer id);

    /**
     * 通过规格id获取回复
     * @param standardId 规格id
     * @return 所有的规格
     */
    List<Reply> listReplyByStandardId(Integer standardId);
}
