package edu.cslg.easyshopping.controller;

import edu.cslg.easyshopping.pojo.*;
import edu.cslg.easyshopping.service.*;
import edu.cslg.easyshopping.util.ComplainUtil;
import edu.cslg.easyshopping.util.OrderMoneyUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {
    private static final Integer PAGE_SIZE = 5;
    @Resource
    private AdminService adminService;
    @Resource
    private StuffService stuffService;
    @Resource
    private SellerService sellerService;
    @Resource
    private ComplainService complainService;
    @Resource
    private BuyerService buyerService;
    @Resource
    private OrderService orderService;
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
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Integer countStuffsNoCheck = stuffService.countStuffByStatus();
            session.setAttribute("countStuffsNoCheck",countStuffsNoCheck);
            session.setAttribute("adminOperate","admin_home");
            return "admin_index";
        }
        return "admin_login";
    }

    /**
     * 分页获取为审核的申请材料
     * @param stuffCurrPage 当前页码
     * @return 申请材料列表页面
     */
    @GetMapping(value = "admin_check_seller_list")
    public String adminCheckSellerList(HttpSession session,Integer stuffCurrPage){
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Integer goodsCount = stuffService.countStuffByStatus();
            Integer pageCount = (goodsCount + PAGE_SIZE - 1) / PAGE_SIZE;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(stuffCurrPage == null || stuffCurrPage <= 0){
                stuffCurrPage = 1;
            }
            if(stuffCurrPage > pageCount){
                stuffCurrPage = pageCount;
            }
            List<Stuff> stuffs = stuffService.listStuffByStatus(PAGE_SIZE * (stuffCurrPage - 1), PAGE_SIZE);
            session.setAttribute("noCheckStuffs",stuffs);
            session.setAttribute("stuffPageCount",pageCount);
            session.setAttribute("stuffCurrPage",stuffCurrPage);
            session.setAttribute("adminOperate","admin_check_seller_list");
            return "admin_index";
        }
        return "admin_login";
    }

    /**
     * 管理员审核卖家
     * @param stuffId 申请id
     * @return 跳转
     */
    @GetMapping(value = "admin_check_seller")
    public String adminCheckSeller(HttpSession session,Integer stuffId){
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Stuff stuff = stuffService.getStuffById(stuffId);
            session.setAttribute("stuff",stuff);
            session.setAttribute("adminOperate","admin_check_seller");
            return "admin_index";
        }
        return "admin_login";
    }

    /**
     * 同意审核
     * @return 审核列表
     */
    @ResponseBody
    @GetMapping(value = "admin_agree_seller_check")
    public String adminAgreeSellerCheck(HttpSession session, Integer stuffId){
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Stuff stuff = stuffService.getStuffById(stuffId);
            stuff.setStuffStatus(true);
            stuffService.updateStuff(stuff);
            Seller seller = stuff.getSeller();
            seller.setSellerStatus(true);
            sellerService.updateSeller(seller);
            return "success";
        }
        return "admin_login";
    }

    /**
     * 不同意审核
     * @return 审核列表
     */
    @ResponseBody
    @GetMapping(value = "admin_disagree_seller_check")
    public String adminDisagreeSellerCheck(HttpSession session,Integer stuffId,String reason){
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Stuff stuff = stuffService.getStuffById(stuffId);
            stuff.setStuffStatus(true);
            stuff.setReason(reason);
            stuffService.updateStuff(stuff);
            return "success";
        }
        return "admin_login";
    }

    /**
     * 审核投诉列表
     * @return 返回首页
     */
    @GetMapping(value = "admin_complain_check_list")
    public String adminComplainCheckList(HttpSession session,Integer complainCurrPage,String type){
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Integer complainCount = complainService.countComplainWaitCheck(type);
            Integer pageCount = (complainCount + PAGE_SIZE - 1) / PAGE_SIZE;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(complainCurrPage == null || complainCurrPage <= 0){
                complainCurrPage = 1;
            }
            if(complainCurrPage > pageCount){
                complainCurrPage = pageCount;
            }
            List<Complain> complains = complainService.listComplainWaitCheck(PAGE_SIZE * (complainCurrPage - 1), PAGE_SIZE,type);
            session.setAttribute("complains",complains);
            session.setAttribute("complainCount",pageCount);
            session.setAttribute("complainCurrPage",complainCurrPage);
            session.setAttribute("type",type);
            session.setAttribute("adminOperate","admin_check_complain_list");
            return "admin_index";
        }
        return "admin_login";
    }

    /**
     * 处理单个投诉
     * @param complainId 投诉的id
     * @return 处理投诉的页面
     */
    @GetMapping(value = "admin_check_complain")
    public String adminCheckComplain(HttpSession session, Integer complainId,String type){
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Complain complain = ComplainUtil.setComplainImgs(complainService.getComplainById(complainId));
            session.setAttribute("complain",complain);
            session.setAttribute("adminOperate","admin_check_complain");
            session.setAttribute("type",type);
            return "admin_index";
        }
        return "admin_login";
    }

    /**
     * 处理投诉
     * @param complainId 投诉id
     * @param cutScore 扣分分值
     * @return 处理成功与否
     */
    @ResponseBody
    @GetMapping(value = "admin_check_complain_operate")
    public String adminCheckComplainOperate(HttpSession session,Integer complainId,Integer cutScore){
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if(currAdmin != null){
            Complain complain = complainService.getComplainById(complainId);
            if(null == cutScore){
                complain.setCutScore(0);
            }else {
                complain.setCutScore(cutScore);
                Seller seller = complain.getOrderItem().getOrder().getSeller();
                if(seller.getScore() - cutScore > 0){
                    seller.setScore(seller.getScore() - cutScore);
                }else {
                    seller.setScore(0);
                    seller.setSellerStatus(false);
                }
                sellerService.updateSeller(seller);
            }
            complain.setCheckFlag(true);
            complainService.updateComplain(complain);
            return "success";
        }
        return "admin_login";
    }

    @GetMapping(value = "admin_look_user_list")
    public String adminLookSellerList(HttpSession session,String select,Integer sellerCurrPage, Integer buyerCurrPage) {
        Admin currAdmin = (Admin) session.getAttribute("currAdmin");
        if (currAdmin != null) {
            if("seller".equals(select)){
                Integer sellerCount = sellerService.countSeller();
                Integer pageCount = (sellerCount + PAGE_SIZE - 1) / PAGE_SIZE;
                if(pageCount == 0){
                    pageCount = 1;
                }
                if(sellerCurrPage == null || sellerCurrPage <= 0){
                    sellerCurrPage = 1;
                }
                if(sellerCurrPage > pageCount){
                    sellerCurrPage = pageCount;
                }
                List<Seller> sellers = sellerService.listSeller(PAGE_SIZE * (sellerCurrPage - 1), PAGE_SIZE);
                session.setAttribute("sellers",sellers);
                session.setAttribute("lookCount",pageCount);
                session.setAttribute("lookCurrPage",sellerCurrPage);
            }else {
                Integer buyerCount = buyerService.countBuyer();
                Integer pageCount = (buyerCount + PAGE_SIZE - 1) / PAGE_SIZE;
                if(pageCount == 0){
                    pageCount = 1;
                }
                if(buyerCurrPage == null || buyerCurrPage <= 0){
                    buyerCurrPage = 1;
                }
                if(buyerCurrPage > pageCount){
                    buyerCurrPage = pageCount;
                }
                List<Buyer> buyers = buyerService.listBuyer(PAGE_SIZE * (buyerCurrPage - 1), PAGE_SIZE);
                for (Buyer buyer : buyers){
                    Integer orderNum = 0;
                    Float payMoney = 0.0f;
                    List<Order> orders = OrderMoneyUtil.setOrderListMoney(orderService.listOrderByBuyer(buyer.getId()));
                    for(Order order : orders){
                        orderNum++;
                        payMoney += order.getOrderMoney();
                    }
                    buyer.setPayMoney(payMoney);
                    buyer.setOrderNum(orderNum);
                }
                session.setAttribute("buyers",buyers);
                session.setAttribute("lookCount",pageCount);
                session.setAttribute("lookCurrPage",buyerCurrPage);
            }
            session.setAttribute("select",select);
            session.setAttribute("adminOperate", "admin_look_user_list");
            return "admin_index";
        }
        return "admin_login";
    }

    /**
     * 退出
     * @return 登录
     */
    @GetMapping(value = "admin_logout")
    public String adminLogout(HttpSession session){
        session.removeAttribute("currAdmin");
        return "admin_login";
    }
}
