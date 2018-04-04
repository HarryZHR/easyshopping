package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.HostReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HostReplyDao {
    /**
     * 保存主人回复
     * @param hostReply 主人回复参数
     */
    void saveHostReply(HostReply hostReply);
}
