package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.ComplainDao;
import edu.cslg.easyshopping.pojo.Complain;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ComplainService {
    @Resource
    private ComplainDao complainDao;
    /**
     * 保存投诉
     * @param complain 投诉参数
     */
    public void saveComplain(Complain complain){
        complainDao.saveComplain(complain);
    }

    /**
     * 分页获取待处理投诉
     * @param complainIndex 投诉的开始索引
     * @param complainSize 一页多少的投诉
     * @return 投诉的对象
     */
    public List<Complain> listComplainWaitCheck(Integer complainIndex, Integer complainSize,String type){
        return complainDao.listComplainWaitCheck(complainIndex,complainSize,type);
    }

    /**
     * 待处理的投诉的数量
     * @return 数量
     */
    public Integer countComplainWaitCheck(String type){
        return complainDao.countComplainWaitCheck(type);
    }

    /**
     * 通过id获取投诉对象
     * @param id complainId
     * @return 投诉的对象
     */
    public Complain getComplainById(Integer id){
        return complainDao.getComplainById(id);
    }

    /**
     * 更新投诉状态
     * @param complain 参数
     */
    public void updateComplain(Complain complain){
        complainDao.updateComplain(complain);
    }
}

