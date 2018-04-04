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
     * 保存评价
     * @param reply 参数
     */
    public void saveReply(Reply reply){
        replyDao.saveReply(reply);
    }

    /**
     * 通过规格id获取评论
     * @param goodsId 规格id
     * @param type 评价类型
     * @param replyIndex 每页开始的评论
     * @param replySize 每页评论数
     * @return 所有的规格
     */
    public List<Reply> listAllReplyByStandardId(Integer goodsId, String type, Integer replyIndex, Integer replySize){
        return replyDao.listAllReplyByStandardId(goodsId, type, replyIndex, replySize);
    }

    /**
     * 通过规格id获取评论总数
     * @param goodsId 规格id
     * @param type 评价类型
     * @return 评论
     */
    public Integer countAllReplyByStandardId(Integer goodsId, String type){
        return replyDao.countAllReplyByStandardId(goodsId, type);
    }

    /**
     * 获取有图的评论
     * @param goodsId 规格id
     * @param replyIndex 每页开始的评论
     * @param replySize 每页评论数
     * @return 评论
     */
    public List<Reply> listImgReplyByStandardId(Integer goodsId, Integer replyIndex, Integer replySize){
        return replyDao.listImgReplyByStandardId(goodsId, replyIndex, replySize);
    }

    /**
     * 通过规格id获取有图的评论总数
     * @param goodsId 规格id
     * @return 评论
     */
    public Integer countImgReplyByStandardId(Integer goodsId){
        return replyDao.countImgReplyByStandardId(goodsId);
    }

    /**
     * 卖家获取
     * @param replyType 评价类型
     * @param sellerId 卖家id
     * @param replyIndex 评价开始索引
     * @param replySize 一页的评价数
     * @return 评价
     */
    public List<Reply> listReplyBySellerAndType(String replyType, Integer sellerId,Integer replyIndex, Integer replySize,Boolean hostReply){
        return replyDao.listReplyBySellerAndType(replyType, sellerId, replyIndex, replySize,hostReply);
    }

    /**
     * 根据时间获取评价数
     * @param replyType 评价类型
     * @param sellerId 卖家id
     * @param interval 时间间隔
     * @return 数量
     */
    public Integer countReplyBySellerAndType(String replyType, Integer sellerId, Long interval,Boolean hostReply){
        return replyDao.countReplyBySellerAndType(replyType, sellerId, interval,hostReply);
    }

    /**
     * 通过id获取评价
     * @param id 评价id
     * @return 评价
     */
    public Reply getReplyById(Integer id){
        return replyDao.getReplyById(id);
    }

    /**
     * 更新评价
     * @param reply 评价的参数
     */
    public void updateReply(Reply reply){
        replyDao.updateReply(reply);
    }
}
