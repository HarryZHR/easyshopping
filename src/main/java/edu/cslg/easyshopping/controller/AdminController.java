package edu.cslg.easyshopping.controller;

import edu.cslg.easyshopping.pojo.Admin;
import edu.cslg.easyshopping.pojo.Stuff;
import edu.cslg.easyshopping.service.AdminService;
import edu.cslg.easyshopping.service.StuffService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {
    @Resource
    private AdminService adminService;
    @Resource
    private StuffService stuffService;
    /**
     * 管理员登录页面
     * @return 管理员登录页面
     */
    @GetMapping(value = "admin_login")
    public String adminLogin(){
        return "admin_login";
    }

    /**
     * 验证登录名和密码
     * @param session 保存作用域
     * @param loginId 登录名
     * @param pwd 密码
     * @return 是否登录成功
     */
    @ResponseBody
    @GetMapping(value = "admin_login_check")
    public String adminLoginCheck(HttpSession session,String loginId, String pwd){
        Admin admin = adminService.getAdminByLogin(loginId,pwd);
        if(admin != null){
            session.setAttribute("currAdmin",admin);
            return "success";
        }
        return "fail";
    }

    /**
     * 跳转管理员的首页
     * @return 管理员的首页
     */
    @GetMapping(value = "admin_index")
    public String adminIndex(HttpSession session){
        List<Stuff> stuffsNoCheck = stuffService.listStuffByStatus();
        session.setAttribute("non_check_stuffs",stuffsNoCheck);
        return "admin_index";
    }


}
