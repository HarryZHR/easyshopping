package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.StuffDao;
import edu.cslg.easyshopping.pojo.Stuff;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StuffService{

    @Resource
    private StuffDao stuffDao;

    public void saveStuff(Stuff stuff) {
        stuffDao.saveStuff(stuff);
    }
}
