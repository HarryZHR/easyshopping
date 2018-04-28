package edu.cslg.easyshopping.controller;

import edu.cslg.easyshopping.pojo.*;
import edu.cslg.easyshopping.service.*;
import edu.cslg.easyshopping.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class SellerController {

    private static final Integer PAGE_SIZE = 5;
    @Resource
    private GoodsTypeService goodsTypeService;
    @Resource
    private SellerService sellerService;
    @Resource
    private StuffService stuffService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private GoodsImgService goodsImgService;
    @Resource
    private StandardService standardService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderStatusService orderStatusService;
    @Resource
    private OrderItemService orderItemService;
    @Resource
    private ReplyService replyService;
    @Resource
    private HostReplyService hostReplyService;
    @Resource
    private ComplainService complainService;
    @Resource
    private BackGoodsInfoService backGoodsInfoService;

    /**
     * 商家注册
     * @return 商家注册的页面
     */
    @GetMapping(value = "/seller_register")
    public String sellerRegister(){
        return "seller_register";
    }

    /**
     * 发送验证码
     * @return 手机号码是否被注册
     */
    @ResponseBody
    @GetMapping(value = "/validate_seller_tel")
    public String sendValidation(String tel,HttpSession session){
        Seller seller = sellerService.getSellerByTel(tel);
        if(seller != null){
            return "false";
        }else {
            String validationCode = ValidationCode.getRandom();
            System.out.println("卖家注册的验证码："+validationCode);
            session.setAttribute(tel,validationCode);
            /*try {
                SendSmsResponse response = ValidationCodeSend.sendSms(tel,validationCode ,"SMS_102315072");
                System.out.println("短信接口返回的数据----------------");
                System.out.println("Code=" + response.getCode());
                System.out.println("Message=" + response.getMessage());
                System.out.println("RequestId=" + response.getRequestId());
                System.out.println("BizId=" + response.getBizId());
            }catch (ClientException e){
                e.printStackTrace();
            }*/
            return "true";
        }
    }

    /**
     * 验证验证码
     * @return 返回验证结果
     */
    @ResponseBody
    @GetMapping(value = "/validate_code")
    public String validateCode(String tel, String validate,HttpSession session){
        if(validate.equals(session.getAttribute(tel))){
            session.setAttribute("newSellerTel",tel);
            return "true";
        }
        return "false";
    }

    /**
     * 店铺申请提示的跳转
     * @return 店铺申请
     */
    @GetMapping(value = "/seller_application")
    public String sellerApplication(){
        return "seller_application";
    }

    /**
     * 店铺申请下一步
     * @return 跳转店铺申请填写信息
     */
    @GetMapping(value = "/seller_input")
    public String sellerInput(){
        return "seller_input";
    }

    /**
     * 提交店铺个人信息
     * @return 返回提交申请材料照片的页面
     */
    @PostMapping(value = "/seller_info_sub")
    public String sellerInfoSub(Seller seller,HttpSession session){
        Object telObj = session.getAttribute("newSellerTel");
        seller.setTel((String) telObj);
        seller.setScore(100);
        seller.setSellerStatus(false);
        seller.setShopTime(new Date());
        sellerService.saveSeller(seller);
        return "seller_info_sub";
    }

    /**
     * 直接跳转上传申请材料的页面
     * @return 申请材料的页面
     */
    @GetMapping(value = "seller_up_stuff")
    public String sellerUpStuff(){
        return "seller_info_sub";
    }

    /**
     * 提交店铺的申请材料
     * @return 返回卖家登录界面
     */
    @PostMapping(value = "/seller_stuff")
    public String sellerStuff(Stuff stuff,HttpSession session){
        Object telObj = session.getAttribute("newSellerTel");
        Seller seller;
        if(telObj != null){
             seller = sellerService.getSellerByTel((String) telObj);
        }else {
            seller = (Seller) session.getAttribute("currSeller");
        }
        stuff.setId(seller.getId());
        stuff.setStuffStatus(false);
        stuff.setReason(null);
        if(seller.getStuff() == null){
            stuffService.saveStuff(stuff);
        }else {
            stuffService.updateStuff(stuff);
        }
        return "seller_login";
    }

    /**
     * 验证密码
     * @param seller 卖家
     * @return 验证正确且状态正确返回"true",否则"false"
     */
    @GetMapping(value = "/seller_check_pwd")
    @ResponseBody
    public String sellerCheck_pwd(Seller seller,HttpSession session){
        Seller newSeller = sellerService.getSellerByTelAndPwd(seller);
        if(newSeller != null){
            session.setAttribute("currSeller",newSeller);
            return "true";
        }
        return "false";
    }

    /**
     * 店铺主界面
     * @return 登录之后的首页
     */
    @GetMapping(value = "/seller_home")
    public String sellerHome(Model model,HttpSession session) {
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            Integer goodsCount = goodsService.countGoodsBySeller(currSeller.getId());
            session.setAttribute("goodsCountInSeller",goodsCount);
            currSeller.setComplainCount(complainService.countComplainBySellerId(currSeller.getId()));
            session.setAttribute("currSeller",currSeller);
            model.addAttribute("seller_status", "home_page");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 店铺的基础设置
     *
     * @return 店铺基础设置的页面
     */
    @GetMapping(value = "/seller_setting")
    public String setting(Model model,HttpSession session) {
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            // 获取当前数据库中最新的店铺信息
            Seller sellerNow = sellerService.getSellerAllById(currSeller.getId());
            session.setAttribute("sellerNow",sellerNow);
            model.addAttribute("seller_status", "setting");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 修改店铺信息
     * @param seller 店铺参数
     * @return 修改成功与否
     */
    @GetMapping(value = "/seller_update")
    @ResponseBody
    public String sellerUpdate(Seller seller,HttpSession session){
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            // 更新店铺中的信息
            currSeller.setStoreName(seller.getStoreName());
            currSeller.setIcon(seller.getIcon());
            currSeller.setIntroduce(seller.getIntroduce());
            currSeller.setSellerProvince(seller.getSellerProvince());
            currSeller.setSellerCity(seller.getSellerCity());
            currSeller.setSellerDistrict(seller.getSellerDistrict());
            currSeller.setSellerStreet(seller.getSellerStreet());
            currSeller.setSupply(seller.getSupply());
            sellerService.updateSeller(currSeller);
            return "true";
        }
        return "seller_login";
    }

    /**
     * 发布商品前选择分类
     *
     * @return 店铺发布商品前的页面
     */
    @GetMapping(value = "/goods_category")
    public String goodsCategory(Model model, HttpSession session) {
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            List<GoodsType> goodsTypeList = goodsTypeService.listGoodsType();
            session.setAttribute("goodsTypeList", goodsTypeList);
            model.addAttribute("seller_status", "goods_category");
            model.addAttribute("operate", "goods_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 衣服的尺寸
     * @return 衣服尺寸的集合
     */
    private List<String> setClothSizes(){
        List<String> clothSizes = new ArrayList<>();
        clothSizes.add("S");
        clothSizes.add("M");
        clothSizes.add("X");
        clothSizes.add("L");
        clothSizes.add("XL");
        clothSizes.add("2XL");
        clothSizes.add("3XL");
        return clothSizes;
    }

    /**
     * 设置鞋子尺码
     * @return 鞋子尺码的集合
     */
    private List<Integer> setShoesSizes(){
        List<Integer> shoesSizes = new ArrayList<>();
        for (int i = 35; i < 46; i++) {
            shoesSizes.add(i);
        }
        return shoesSizes;
    }

    /**
     * 设置童装的尺码
     * @return 童装的尺码
     */
    private List<Integer> setChildSizes(){
        List<Integer> childSizes = new ArrayList<>();
        for (int i = 80; i < 161; i += 10) {
            childSizes.add(i);
        }
        return childSizes;
    }

    /**
     * 发布商品
     * @param model 参数
     * @param goods_type 上一步选择的商品分类
     * @param session 保存作用域
     * @return 上架商品
     */
    @PostMapping(value = "/goods_publish")
    public String goodsPublish(Model model, String goods_type, HttpSession session) {
        Seller currSeller = (Seller) session.getAttribute("currSeller");
        if(currSeller != null){
            session.setAttribute("clothSizes", setClothSizes());
            session.setAttribute("shoesSizes", setShoesSizes());
            session.setAttribute("childSizes", setChildSizes());
            session.setAttribute("goods_type", goods_type);
            model.addAttribute("seller_status", "goods_publish");
            model.addAttribute("operate", "goods_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 保存商品
     * @return 主页面
     */
    @PostMapping(value = "/goods_save")
    public String goodsSave(Goods goods,GoodsImg goodsImg,HttpSession session,Model model,String[] input_img_detail,String[] color,String[] colorImg,String[] size,Integer[] count,Float[] price){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            String type = (String)session.getAttribute("goods_type");
            GoodsType goodsType = goodsTypeService.getGoodsTypeByType(type);
            goods.setGoodsTime(new Date());
            goods.setType(goodsType);
            goods.setSeller(currSeller);
            goodsService.saveGoods(goods);
            Integer goodsId = goods.getId();
            goodsImg.setId(goodsId);
            StringBuilder detailImg = new StringBuilder();
            for (String detailImgArr : input_img_detail){
                detailImg.append(detailImgArr).append("_");
            }
            goodsImg.setDetailImg(detailImg.toString());
            goodsImgService.saveGoodsImg(goodsImg);
            Standard standard = new Standard();
            Float minPrice = Float.MAX_VALUE;
            for (int i = 0; i < color.length; i++){
                if(price[i]< minPrice){
                    minPrice = price[i];
                }
                standard.setGoods(goods);
                standard.setColor(color[i]);
                standard.setColorImg(colorImg[i]);
                standard.setSize(size[i]);
                standard.setCount(count[i]);
                standard.setPrice(price[i]);
                standardService.saveStandard(standard);
            }
            goods.setMinPrice(minPrice);
            goods.setStatus(true);
            goods.setSaleCount(0);
            goods.setDiscount(0f);
            goodsService.updateGoods(goods);
            model.addAttribute("seller_status", "home_page");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 上传图片
     *
     * @return 图片保存的URL
     */
    @ResponseBody
    @PostMapping(value = "/up_img")
    public String upImg(@RequestParam("file") MultipartFile file) {
        if (file != null) {
            return UploadUtil.upImg(file);
        }
        return null;
    }

    /**
     * 多图上传
     * @param files 上传的文件
     * @return 返回上传的路径
     */
    @ResponseBody
    @PostMapping(value = "/up_img_multi")
    public String upImgMulti(@RequestParam("files") MultipartFile[] files){
        if(files.length > 0){
            StringBuilder urlStr = new StringBuilder();
            for (MultipartFile file : files) {
                String url = UploadUtil.upImg(file);
                urlStr.append(url).append("_");
            }
            return urlStr.toString();
        }
        return null;
    }

    /**
     * 编辑商品
     *
     * @return 所有上架商品的页面
     */
    @GetMapping(value = "/goods_list_edit")
    public String goodsListEdit(Model model,HttpSession session,Integer currPage) {
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            listGoodsInSeller(currPage,currSeller,session);
            model.addAttribute("seller_status", "goods_list_edit");
            model.addAttribute("operate", "goods_operate");
            return "seller_index";
        }
        return null;
    }

    /**
     * 分页获取店铺中的商品
     * @param currPage 当前页
     * @param currSeller 当前的卖家
     * @param session 保存作用域
     */
    private void listGoodsInSeller(Integer currPage,Seller currSeller,HttpSession session){
        Integer pageSize = 5;
        Integer goodsCount = goodsService.countGoodsBySeller(currSeller.getId());
        Integer pageCount = (goodsCount + pageSize - 1) / pageSize;
        if(pageCount == 0){
            pageCount = 1;
        }
        if(currPage == null || currPage <= 0){
            currPage = 1;
        }
        if(currPage > pageCount){
            currPage = pageCount;
        }
        List<Goods> goodsList = GoodsCountUtil.setGoodsListCount(goodsService.listGoodsBySeller(currSeller.getId(), pageSize * (currPage - 1), pageSize));
        session.setAttribute("goodsList",goodsList);
        session.setAttribute("pageCount",pageCount);
        session.setAttribute("currPage",currPage);
    }

    /**
     * 编辑某个商品
     * @param goodsId 商品的id
     * @return 跳转编辑的页面
     */
    @GetMapping(value = "to_goods_edit")
    public String toGoodsEdit(Integer goodsId,HttpSession session,Model model){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Goods goods = GoodsCountUtil.setGoodsCount(goodsService.getGoodsById(goodsId),"");
            session.setAttribute("goodsInEdit",goods);
            session.setAttribute("clothSizes", setClothSizes());
            session.setAttribute("shoesSizes", setShoesSizes());
            session.setAttribute("childSizes", setChildSizes());
            model.addAttribute("seller_status", "goods_edit");
            model.addAttribute("operate", "goods_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 保存编辑的商品
     * @return 保存商品
     */
    @GetMapping(value = "goods_edit")
    public String sellerGoodsEdit(HttpSession session,Model model){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null) {
            model.addAttribute("seller_status", "goods_list_edit");
            model.addAttribute("operate", "goods_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 商品店铺中的商品，修改商品的状态
     * @param goodsId 商品的id
     * @return 商品删除成功与否
     */
    @GetMapping(value = "seller_delete_goods")
    @ResponseBody
    public String sellerDeleteGoods(String goodsId){
        Goods goods = goodsService.getGoodsById(Integer.valueOf(goodsId));
        goods.setStatus(false);
        goodsService.updateGoods(goods);
        return "true";
    }
    /**
     * 设置商品优惠
     * @return 商品优惠设置的页面
     */
    @GetMapping(value = "/goods_discount")
    public String goodsDiscount(Model model,HttpSession session,Integer currPage) {
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            listGoodsInSeller(currPage,currSeller,session);
            model.addAttribute("seller_status", "goods_discount");
            model.addAttribute("operate", "goods_operate");
            return "seller_index";
        }
        return null;
    }

    /**
     * 商品参加滚动图
     * @param model model
     * @return 参加滚动图活动的页面
     */
    @GetMapping(value = "/goods_activity")
    public String goodsActivity(Model model,HttpSession session,Integer currPage) {
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            listGoodsInSeller(currPage,currSeller,session);
            model.addAttribute("seller_status", "goods_activity");
            model.addAttribute("operate", "goods_operate");
            return "seller_index";
        }
        return null;
    }

    /**
     * 参与活动
     * @param activityName 参与活动的名称
     * @param goodsId 商品的id
     * @return 参与是否成功
     */
    @GetMapping(value = "seller_attend_activity")
    @ResponseBody
    public String sellerAttendActivity(String activityName, Integer goodsId,HttpSession session){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Goods goods = goodsService.getGoodsById(goodsId);
            goods.setActivity(Integer.valueOf(activityName));
            goodsService.updateGoods(goods);
            return "true";
        }
        return null;
    }

    /**
     * 修改优惠
     * @param session 保存作用域
     * @param goodsId 商品的id
     * @param discount 折扣
     * @return 修改状态
     */
    @GetMapping(value = "seller_set_discount")
    @ResponseBody
    public String sellerSetDiscount(HttpSession session,Integer goodsId,Float discount){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Goods goods = goodsService.getGoodsById(goodsId);
            goods.setDiscount(discount);
            goodsService.updateGoods(goods);
            return "true";
        }
        return null;
    }

    /**
     * 卖家退出
     * @return 登录界面
     */
    @GetMapping(value = "seller_exit")
    public String sellerExit(HttpSession session){
        session.removeAttribute("currSeller");
        return "seller_login";
    }

    /**
     * 跳转登录界面
     * @return 登录界面
     */
    @GetMapping(value = "seller_login")
    public String sellerLogin(){
        return "seller_login";
    }

    /**
     * 查看卖家所有的订单
     * @param currPage 当前页
     * @return 卖家的订单
     */
    @GetMapping(value = "seller_order_list")
    public String sellerOrderList(Model model,HttpSession session, Integer currPage){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Integer pageSize = 5;
            Integer goodsCount = orderService.countOrderBySellerId(currSeller.getId());
            Integer pageCount = (goodsCount + pageSize - 1) / pageSize;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(currPage == null || currPage <= 0){
                currPage = 1;
            }
            if(currPage > pageCount){
                currPage = pageCount;
            }
            List<Order> orders = OrderMoneyUtil.setOrderListMoney(orderService.listOrderBySellerId(currSeller.getId(),pageSize * (currPage - 1), pageSize));
            session.setAttribute("orderInSeller",orders);
            session.setAttribute("currPage",currPage);
            session.setAttribute("pageCount",pageCount);
            model.addAttribute("seller_status", "order_list");
            model.addAttribute("operate", "order_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 查看评价
     * @return 店铺内的评价，第一页
     */
    @GetMapping(value = "seller_order_reply")
    public String sellerOrderReply(HttpSession session, Model model,String replyType,Boolean hostReply){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Long month = System.currentTimeMillis() - 2592000;
            Long week = System.currentTimeMillis() - 604800;
            Integer goodMonth = replyService.countReplyBySellerAndType("goodReply",currSeller.getId(), month,false);
            Integer multiMonth = replyService.countReplyBySellerAndType("multiReply",currSeller.getId(), month,false);
            Integer badMonth = replyService.countReplyBySellerAndType("badReply",currSeller.getId(), month,false);
            Integer allMonth = goodMonth + multiMonth + badMonth;

            Integer goodWeek = replyService.countReplyBySellerAndType("goodReply",currSeller.getId(),week,false);
            Integer multiWeek = replyService.countReplyBySellerAndType("multiReply",currSeller.getId(),week,false);
            Integer badWeek = replyService.countReplyBySellerAndType("badReply",currSeller.getId(),week,false);
            Integer allWeek = goodWeek + multiWeek + badWeek;

            Integer allGood = replyService.countReplyBySellerAndType("goodReply",currSeller.getId(),null,false);
            Integer allMulti = replyService.countReplyBySellerAndType("multiReply",currSeller.getId(),null,false);
            Integer allBad = replyService.countReplyBySellerAndType("badReply",currSeller.getId(),null,false);

            Integer goodOutMonth = allGood - goodMonth;
            Integer multiOutMonth = allMulti - multiMonth;
            Integer badOutMonth = allBad - badMonth;
            Integer allOutMonth = goodOutMonth + multiOutMonth + badOutMonth;

            List<Integer> replyCounts = new ArrayList<>();
            replyCounts.add(goodWeek);
            replyCounts.add(goodMonth);
            replyCounts.add(goodOutMonth);
            replyCounts.add(allGood);

            replyCounts.add(multiWeek);
            replyCounts.add(multiMonth);
            replyCounts.add(multiOutMonth);
            replyCounts.add(allMulti);

            replyCounts.add(badWeek);
            replyCounts.add(badMonth);
            replyCounts.add(badOutMonth);
            replyCounts.add(allBad);

            replyCounts.add(allWeek);
            replyCounts.add(allMonth);
            replyCounts.add(allOutMonth);

            if("".equals(replyType)){
                replyType = null;
            }
            Integer pageSize = 5;
            Integer replyCount = replyService.countReplyBySellerAndType(replyType,currSeller.getId(),null,hostReply);
            Integer pageCount = (replyCount + pageSize - 1) / pageSize;
            replyCounts.add(replyCount);
            if(pageCount == 0){
                pageCount = 1;
            }
            List<Reply> replies = replyService.listReplyBySellerAndType(replyType,currSeller.getId(),0, pageSize,hostReply);
            session.setAttribute("replies",replies);
            session.setAttribute("currReplyPage",1);
            session.setAttribute("pageCount",pageCount);
            session.setAttribute("replyType",replyType);
            session.setAttribute("hostReply",hostReply);
            session.setAttribute("replyCounts",replyCounts);
            model.addAttribute("seller_status", "order_reply");
            model.addAttribute("operate", "order_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 获得分页的评价
     * @param currReplyPage 当前页码
     * @param replyType 评价类型
     * @return 每页的评价
     */
    @GetMapping(value = "seller_page_reply")
    public String sellerPageReply(HttpSession session, Integer currReplyPage, Model model, String replyType,Boolean hostReply){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            if("".equals(replyType)){
                replyType = null;
            }
            Integer pageSize = 5;
            Integer replyCount = replyService.countReplyBySellerAndType(replyType,currSeller.getId(),null,hostReply);
            Integer pageCount = (replyCount + pageSize - 1) / pageSize;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(currReplyPage == null || currReplyPage <= 0){
                currReplyPage = 1;
            }
            if(currReplyPage > pageCount){
                currReplyPage = pageCount;
            }
            List<Reply> replies = replyService.listReplyBySellerAndType(replyType,currSeller.getId(),pageSize * (currReplyPage - 1), pageSize,hostReply);
            session.setAttribute("replies",replies);
            session.setAttribute("currReplyPage",currReplyPage);
            session.setAttribute("hostReply",hostReply);
            model.addAttribute("seller_status", "order_reply");
            model.addAttribute("operate", "order_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 添加店铺的回复
     * @param replyId 评价的id
     * @param hostReply 主人的评价
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "seller_host_reply")
    public String sellerHostReply(HttpSession session,Integer replyId, HostReply hostReply){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Reply reply = replyService.getReplyById(replyId);
            hostReply.setHostReplyTime(new Date());
            hostReplyService.saveHostReply(hostReply);
            reply.setHostReply(hostReply);
            replyService.updateReply(reply);
            return "success";
        }
        return "seller_login";
    }

    /**
     * 发货页面
     * @return 发货的页面
     */
    @GetMapping(value = "seller_send_goods")
    public String sellerSendGoods(HttpSession session,Model model, Integer currPage){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Integer pageSize = 5;
            Integer orderCount = orderService.countOrderByMoreStatusAndSeller(2,2,currSeller.getId());
            Integer pageCount = (orderCount + pageSize - 1) / pageSize;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(currPage == null || currPage <= 0){
                currPage = 1;
            }
            if(currPage > pageCount){
                currPage = pageCount;
            }
            List<Order> orders = OrderMoneyUtil.setOrderListMoney(orderService.listOrderByMoreStatusAndSeller(2,2,currSeller.getId(), pageSize * (currPage - 1), pageSize));
            session.setAttribute("orderWaitSend",orders);
            model.addAttribute("seller_status", "send_goods");
            model.addAttribute("operate", "send_operate");
            session.setAttribute("currPage",currPage);
            session.setAttribute("pageCount",pageCount);
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 发货
     * @return 成功与否
     */
    @ResponseBody
    @GetMapping(value = "seller_update_send_goods")
    public String sellerUpdateSendGoods(HttpSession session, Integer orderId, String sendNum){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Order order = orderService.getOrderById(orderId);
            for (OrderItem orderItem : order.getOrderItems()){
                orderItem.setSendNum(sendNum);
                orderItemService.updateOrderItem(orderItem);
            }
            order.setOrderStatus(orderStatusService.getOrderStatusById(3));
            orderService.updateOrderStatus(order);
            return "success";
        }
        return "seller_login";
    }

    /**
     * 单个商品的发货页面
     * @param orderId 订单的id
     * @return 发货页面
     */
    @GetMapping(value = "seller_single_send_goods")
    public String sellerSingleGoods(HttpSession session,Model model,Integer orderId){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Order order = orderService.getOrderById(orderId);
            List<Order> orders = new ArrayList<>();
            orders.add(order);
            OrderMoneyUtil.setOrderListMoney(orders);
            session.setAttribute("orderWaitSend",orders);
            model.addAttribute("seller_status", "send_goods");
            model.addAttribute("operate", "send_operate");
            model.addAttribute("single_send","true");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 查看店铺的所有投诉
     * @param complainCurrPage 当前页面
     * @return 跳转页面
     */
    @GetMapping(value = "seller_complain_list")
    public String sellerComplainList(HttpSession session,Model model,Integer complainCurrPage){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Integer pageSize = 5;
            Integer complainCount = complainService.countComplainBySellerId(currSeller.getId());
            Integer pageCount = (complainCount + pageSize - 1) / pageSize;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(complainCurrPage == null || complainCurrPage <= 0){
                complainCurrPage = 1;
            }
            if(complainCurrPage > pageCount){
                complainCurrPage = pageCount;
            }
            List<Complain> complains = complainService.listComplainBySellerId(currSeller.getId(),pageSize * (complainCurrPage - 1), pageSize);
            session.setAttribute("complains",complains);
            session.setAttribute("complainCurrPage",complainCurrPage);
            session.setAttribute("complainPageCount",pageCount);
            model.addAttribute("seller_status", "complain_list");
            model.addAttribute("operate", "error_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 查看具体的投诉
     * @param complainId 投诉的id
     * @return 跳转页面
     */
    @GetMapping(value = "seller_look_complain")
    public String sellerLookComplain(HttpSession session,Model model,Integer complainId){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Complain complain = ComplainUtil.setComplainImgs(complainService.getComplainById(complainId));
            session.setAttribute("complain",complain);
            model.addAttribute("seller_status", "complain_look");
            model.addAttribute("operate", "error_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 获取卖家的退货信息
     * @param orderItemCurrPage 当前页
     * @param status 不同的状态
     * @return 退货信息
     */
    @GetMapping(value = "seller_back_list")
    public String sellerBackList(HttpSession session,Model model,Integer orderItemCurrPage,Integer status){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            Integer orderItemCount = orderItemService.countOrderItemByBuyerOrSeller(currSeller.getId(),"seller",status);
            Integer pageCount = (orderItemCount + PAGE_SIZE - 1) / PAGE_SIZE;
            if(pageCount == 0){
                pageCount = 1;
            }
            if(orderItemCurrPage == null || orderItemCurrPage <= 0){
                orderItemCurrPage = 1;
            }
            if(orderItemCurrPage > pageCount){
                orderItemCurrPage = pageCount;
            }
            List<OrderItem> orderItems = orderItemService.listOrderItemByBuyerOrSeller(currSeller.getId(),PAGE_SIZE * (orderItemCurrPage - 1), PAGE_SIZE,"seller",status);
            session.setAttribute("backGoodsOrderItems",orderItems);
            session.setAttribute("orderItemCurrPage",orderItemCurrPage);
            session.setAttribute("orderItemCount",pageCount);
            session.setAttribute("status",status);
            model.addAttribute("seller_status", "back_goods_list");
            model.addAttribute("operate", "back_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 买家跳转到退货的页面
     * @param orderItemId 订单详情id
     * @param process 查看还是操作的标志
     * @return 跳转到页面
     */
    @GetMapping(value = "seller_back_goods")
    public String sellerBackGoods(HttpSession session,Integer orderItemId,String process,Model model){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
            session.setAttribute("orderItem",orderItem);
            session.setAttribute("process",process);
            model.addAttribute("seller_status","back_goods");
            model.addAttribute("operate", "back_operate");
            return "seller_index";
        }
        return "seller_login";
    }

    /**
     * 卖家操作退货
     * @param type 同意还是不同意
     * @param backGoodsId 退货的id
     * @param reason 不同意的理由
     * @return 跳转回页面
     */
    @ResponseBody
    @GetMapping(value = "seller_operate_back")
    public String sellerOperateBack(HttpSession session,String type,Integer backGoodsId,String reason){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            BackGoodsInfo backGoodsInfo = backGoodsInfoService.getBackGoodsInfoById(backGoodsId);
            if("agree".equals(type)){
                backGoodsInfo.setBackStatus("退款成功");
            }else if("disagree".equals(type)){
                backGoodsInfo.setBackStatus("退款失败");
                backGoodsInfo.setReason(reason);
            }
            backGoodsInfoService.updateBackGoodsInfo(backGoodsInfo);
            return "success";
        }
        return "seller_login";
    }

    /**
     * 卖家进入到订单详情
     * @param orderId 订单的id
     * @return 跳转页面
     */
    @GetMapping(value = "seller_order_detail")
    public String sellerOrderDetail(HttpSession session, Integer orderId, Model model){
        Seller currSeller = (Seller)session.getAttribute("currSeller");
        if(currSeller != null){
            session.setAttribute("currOrder",OrderMoneyUtil.setOrderMoney(orderService.getOrderById(orderId)));
            model.addAttribute("seller_status","order_detail");
            model.addAttribute("operate", "order_operate");
            return "seller_index";
        }
        return "seller_login";
    }
}
