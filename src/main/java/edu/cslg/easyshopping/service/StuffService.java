package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.StuffDao;
import edu.cslg.easyshopping.pojo.Stuff;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StuffService{

    @Resource
    private StuffDao stuffDao;

    /**
     * 保存申请材料
     * @param stuff 材料的参数
     */
    public void saveStuff(Stuff stuff) {
        stuffDao.saveStuff(stuff);
    }
    /**
     *  未审核的申请材料
     * @return 所有未通过的申请
     */
    public List<Stuff> listStuffByStatus(){
        return stuffDao.listStuffByStatus();
    }
}
