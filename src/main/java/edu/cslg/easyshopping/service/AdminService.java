package edu.cslg.easyshopping.service;

import edu.cslg.easyshopping.dao.AdminDao;
import edu.cslg.easyshopping.pojo.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {

    @Resource
    private AdminDao adminDao;
    /**
     * 通过登录验证管理员身份
     * @param loginId 登录id
     * @param pwd 密码
     * @return 符合的管理员
     */
    public Admin getAdminByLogin(String loginId, String pwd){
        return adminDao.getAdminByLogin(loginId,pwd);
    }
}
