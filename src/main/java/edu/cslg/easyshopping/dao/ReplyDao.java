package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 保存评价
     * @param reply 参数
     */
    void saveReply(Reply reply);

    /**
     * 通过规格id获取评论
     * @param goodsId 规格id
     * @param type 评价类型
     * @param replyIndex 每页开始的评论
     * @param replySize 每页评论数
     * @return 所有的规格
     */
    List<Reply> listAllReplyByStandardId(@Param(value = "goodsId") Integer goodsId,@Param(value = "type")String type, @Param(value = "replyIndex") Integer replyIndex, @Param(value = "replySize") Integer replySize);

    /**
     * 通过规格id获取评论总数
     * @param goodsId 规格id
     * @param type 评价类型
     * @return 评论
     */
    Integer countAllReplyByStandardId(@Param(value = "goodsId") Integer goodsId,@Param(value = "type") String type);

    /**
     * 获取有图的评论
     * @param goodsId 规格id
     * @param replyIndex 每页开始的评论
     * @param replySize 每页评论数
     * @return 评论
     */
    List<Reply> listImgReplyByStandardId(@Param(value = "goodsId") Integer goodsId,@Param(value = "replyIndex") Integer replyIndex,@Param(value = "replySize") Integer replySize);

    /**
     * 通过规格id获取有图的评论总数
     * @param standardId 规格id
     * @return 评论
     */
    Integer countImgReplyByStandardId(Integer standardId);

    /**
     * 卖家获取
     * @param replyType 评价类型
     * @param sellerId 卖家id
     * @param replyIndex 评价开始索引
     * @param replySize 一页的评价数
     * @return 评价
     */
    List<Reply> listReplyBySellerAndType(@Param(value = "replyType") String replyType,@Param(value = "sellerId") Integer sellerId,@Param(value = "replyIndex") Integer replyIndex,@Param(value = "replySize") Integer replySize, @Param(value = "hostReply")Boolean hostReply);

    /**
     * 根据时间获取评价数
     * @param replyType 评价类型
     * @param sellerId 卖家id
     * @param interval 时间间隔
     * @return 数量
     */
    Integer countReplyBySellerAndType(@Param(value = "replyType") String replyType,@Param(value = "sellerId") Integer sellerId,@Param(value = "interval") Long interval,@Param(value = "hostReply") Boolean hostReply);

    /**
     * 更新评价
     * @param reply 评价的参数
     */
    void updateReply(Reply reply);
}
